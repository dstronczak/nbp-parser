package pl.parser.nbp;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;

public class CurrencyDataService {
    private final static String BASE_URI = "http://www.nbp.pl/kursy/xml/";
    private static final SimpleDateFormat DIR_DATE_FORMAT = new SimpleDateFormat("yyMMdd");
    List<Date> dateList = new ArrayList<Date>();
    HashMap<Integer, HashSet<String>> tableDirectory = new HashMap<Integer, HashSet<String>>();
    private Currency currency;


    public CurrencyDataService(Currency currency, List<Date> dateList) throws IOException {
        this.currency = currency;
        this.dateList = dateList;

        getTableDirectory();
    }


    public List<CurrencyPrice> getData() throws IOException, ParserConfigurationException, SAXException {
        List<CurrencyPrice> currencyPrices = new ArrayList<CurrencyPrice>();
        //get Data
        for (Date date : dateList) {
            String xmlName;
            try {
                xmlName = findXmlNameForDate(date);
            } catch (XmlNotFoundException e) {
                //Currency data not published - skip
                continue;
            }

            String uri = pathForFile(xmlName);
            getDataFromXml(currencyPrices, uri);

        }

        return currencyPrices;
    }

    private void getDataFromXml(List<CurrencyPrice> currencyPrices, String uri) throws IOException, ParserConfigurationException, SAXException {
        URLConnection connection = new URL(uri).openConnection();
        InputStream response = connection.getInputStream();

        currencyPrices.add(XmlParser.parse(response, currency));
    }

    private void getTableDirectory() throws IOException {
        int firstYear = Utils.getYear(dateList.get(0));
        int lastYear = Utils.getYear(dateList.get(dateList.size() - 1));

        for (int i = firstYear; i <= lastYear; i++) {
            tableDirectory.put(i, getDirForYear(i));
        }

    }

    private HashSet<String> getDirForYear(int year) throws IOException {
        String fileName = dirFilenameForYear(year);
        String path = pathForFile(fileName);

        HashSet<String> dir = new HashSet<String>();

        URLConnection connection = new URL(path).openConnection();
        InputStream response = connection.getInputStream();

        BufferedReader in = new BufferedReader(new InputStreamReader(response));
        String line;

        while ((line = in.readLine()) != null) {
            //We are looking for "c" type tables
            if (line.startsWith("c"))
                dir.add(line.trim());
        }

        return dir;

    }

    private String pathForFile(String fileName) {
        return BASE_URI + fileName;
    }

    private String dirFilenameForYear(int year) {
        String dirFileTemplate = "dir%s.txt";
        String yearString = String.valueOf(year);

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if (year == currentYear) {
            yearString = "";
        }

        return String.format(dirFileTemplate, yearString);


    }

    private String findXmlNameForDate(Date date) throws XmlNotFoundException {
        int year = Utils.getYear(date);
        String dateInDir = dateInDirFormat(date);

        for (String xmlName : tableDirectory.get(year)) {
            if (xmlName.substring(xmlName.length() - 6).equals(dateInDir))
                return xmlName + ".xml";
        }

        throw new XmlNotFoundException();

    }

    private String dateInDirFormat(Date date) {
        return DIR_DATE_FORMAT.format(date);

    }

    private class XmlNotFoundException extends Throwable {

    }

}
