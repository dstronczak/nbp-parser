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

        double averagePurchasePrice = countAveragePurchasePrice(currencyPrices);
        double askPriceStandardDeviation = countAskPriceStandardDeviation(currencyPrices);

        return new Result(averagePurchasePrice, askPriceStandardDeviation);

    }

    private double countAveragePurchasePrice(List<CurrencyPrice> currencyPrices) {
        List<Double> purchasePrices = getPriceList(currencyPrices, PriceType.PURCHASE);
        return Utils.getMean(purchasePrices);
    }


    private double countAskPriceStandardDeviation(List<CurrencyPrice> currencyPrices) {
        List<Double> askPrices = getPriceList(currencyPrices, PriceType.ASK);
        return Utils.getStdDev(askPrices);
    }

    private List<Double> getPriceList(List<CurrencyPrice> currencyPrices, PriceType priceType) {
        List<Double> prices = new ArrayList<Double>();

        for(CurrencyPrice price : currencyPrices){
            prices.add(price.getPrice(priceType));
        }
        return prices;
    }
}
