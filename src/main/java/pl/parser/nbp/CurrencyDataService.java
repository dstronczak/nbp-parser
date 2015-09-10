package pl.parser.nbp;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;

public class CurrencyDataService {
    private final static String BASE_URI = "http://www.nbp.pl/kursy/xml/";

    private Currency currency;
    List<Date> dateList = new ArrayList<Date>();
    HashMap<Integer, HashSet<String>> tableDir = new HashMap<Integer, HashSet<String>>();

    private static final SimpleDateFormat DIR_DATE_FORMAT = new SimpleDateFormat("yyMMdd");

    private boolean gotData = false;

    List<Float> puchasePrices = new ArrayList<Float>();
    List<Float> askPrices = new ArrayList<Float>();

    public CurrencyDataService(Currency currency, List<Date> dateList) throws IOException {
        this.currency = currency;
        this.dateList = dateList;


        getTableDir();
    }

    private void getTableDir() throws IOException {
        int firstYear = Utils.getYear(dateList.get(0));
        int lastYear = Utils.getYear(dateList.get(dateList.size() - 1));

        for (int i = firstYear; i <= lastYear; i++) {
            tableDir.put(i, getDirForYear(i));
        }

    }

    private HashSet<String> getDirForYear(int year) throws IOException {
        String fileName = dirFilenameForYear(year);
        String path = pathForFile(fileName);

        HashSet<String> dir = new HashSet<String>();


        System.out.println(path);

        URLConnection connection = new URL(path).openConnection();
        InputStream response = connection.getInputStream();

        BufferedReader in = new BufferedReader(new InputStreamReader(response));
        String line;

        while ((line = in.readLine()) != null) {
            if (line.startsWith("c"))
                dir.add(line.trim());
        }


        System.out.println(dir);

        return dir;

    }

    private String pathForFile(String fileName) {
        return BASE_URI + fileName;
    }

    private String dirFilenameForYear(int year) {
        System.out.printf("Getting it for: " + year + "\n");
        String dirFileTemplate = "dir%s.txt";
        String yearString = String.valueOf(year);

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if (year == currentYear) {
            yearString = "";
        }

        return String.format(dirFileTemplate, yearString);


    }

    public List<CurrencyPrice> getData() throws IOException, ParserConfigurationException, SAXException {
        List<CurrencyPrice> currencyPrices = new ArrayList<CurrencyPrice>();
        //get Data
        for (Date date : dateList) {
            String uri = pathForFile(findXmlNameForDate(date));

            URLConnection connection = new URL(uri).openConnection();
            InputStream response = connection.getInputStream();

            currencyPrices.add(XmlParser.parse(response, currency));


        }
        return currencyPrices;


    }


    private String findXmlNameForDate(Date date) {
        int year = Utils.getYear(date);
        String dateInDir = dateInDirFormat(date);

        for (String xmlName : tableDir.get(year)) {
            if (xmlName.substring(xmlName.length() - 6).equals(dateInDir))
                return xmlName + ".xml";
        }

        return "";

    }

    private String dateInDirFormat(Date date) {
        return DIR_DATE_FORMAT.format(date);

    }

    public static void main(String[] args) {


        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        Date ago = new Date(System.currentTimeMillis() - (700 * DAY_IN_MS));


        try {
            CurrencyDataService cds = new CurrencyDataService(Currency.USD, Utils.getDaysBetweenDates(ago, new Date()));
            System.out.println("\n");
            System.out.println(cds.dateInDirFormat(new Date()));
            System.out.println(cds.findXmlNameForDate(new Date()));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
