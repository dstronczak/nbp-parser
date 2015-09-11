package pl.parser.nbp;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CurrencyData {

    private Currency currency;
    private Date dateFrom;
    private Date dateTo;

    List<Float> currencyPriceList = new ArrayList<Float>();

    public CurrencyData(Currency currency, Date dateFrom, Date dateTo) {
        this.currency = currency;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public  List<CurrencyPrice> fetchData() throws CurrencyDataException {
        List<Date> dateList = Utils.getDaysBetweenDates(dateFrom, dateTo);
        try {
            CurrencyDataService currencyDataService = new CurrencyDataService(currency, dateList);
            return currencyDataService.getData();
        } catch (Exception e) {
            throw new CurrencyDataException();
        }


    }


    //TODO: rm
    public static void main(String[] args) {
        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        Date ago = new Date(System.currentTimeMillis() - (7 * DAY_IN_MS));
        System.out.println(Utils.getDaysBetweenDates(ago, new Date()));
        System.out.println(Utils.getDaysBetweenDates(ago, new Date()).size() );

    }


}
