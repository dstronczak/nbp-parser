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

    }

    public float countAveragePuchasePrice(List<CurrencyPrice> currencyPrices ) {

        List<Float> prices = getPriceList(currencyPrices, PriceType.PURCHASE);
        return Utils.getMean(prices);
    }



    public float countAskPriceStandardDeviation(List<CurrencyPrice> currencyPrices ){
        List<Float> prices = getPriceList(currencyPrices, PriceType.ASK);
        return Utils.getStdDev(prices);
    }

    private List<Float> getPriceList(List<CurrencyPrice> currencyPrices, PriceType priceType) {
        List<Float> prices = new ArrayList<Float>();

        for(CurrencyPrice price : currencyPrices){
            prices.add(price.getPrice(priceType));
        }
        return prices;
    }
}
