package pl.parser.nbp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CurrencyData {

    private Currency currency;
    private Date dateFrom;
    private Date dateTo;


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


}
