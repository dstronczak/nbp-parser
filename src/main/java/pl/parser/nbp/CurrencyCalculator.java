package pl.parser.nbp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CurrencyCalculator {

    private Currency currency;
    private Date dateFrom;
    private Date dateTo;

    public CurrencyCalculator(Currency currency, Date dateFrom, Date dateTo) {
        this.currency = currency;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public Result calculate() throws CurrencyDataException {
        CurrencyData currencyData = new CurrencyData(currency, dateFrom, dateTo);
        List<CurrencyPrice> currencyPrices = currencyData.fetchData();
        float averagePuchasePrice = countAveragePuchasePrice(currencyPrices);
        float askPriceStandardDeviation = countAskPriceStandardDeviation(currencyPrices);
        return new Result(averagePuchasePrice, askPriceStandardDeviation);

//        System.out.println(currencyPrices);
//        System.out.println("Avg purchase price: " + countAveragePuchasePrice(currencyPrices));
//        System.out.println("std dev price: " + countAskPriceStandardDeviation(currencyPrices));

    }

    public float countAveragePuchasePrice(List<CurrencyPrice> currencyPrices ) {

        List<Float> prices = getPurchasePriceList(currencyPrices);
        return Utils.getMean(prices);
    }

    private List<Float> getPurchasePriceList(List<CurrencyPrice> currencyPrices) {
        List<Float> prices = new ArrayList<Float>();

        for(CurrencyPrice price : currencyPrices){
            prices.add(price.getPurchasePrice());
        }
        return prices;
    }

    private List<Float> getAskPriceList(List<CurrencyPrice> currencyPrices) {
        List<Float> prices = new ArrayList<Float>();

        for(CurrencyPrice price : currencyPrices){
            prices.add(price.getAskPrice());
        }
        return prices;
    }



    public float countAskPriceStandardDeviation(List<CurrencyPrice> currencyPrices ){
        List<Float> prices = getAskPriceList(currencyPrices);
        return Utils.getStdDev(prices);

    }
}
