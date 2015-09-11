package pl.parser.nbp;

public class Result {

    private double averagePurchasePrices;
    private double askPriceStandardDeviation;

    public Result(double averagePurchasePrices, double askPriceStandardDeviation) {
        this.averagePurchasePrices = averagePurchasePrices;
        this.askPriceStandardDeviation = askPriceStandardDeviation;
    }

    public double getAveragePurchasePrices() {
        return averagePurchasePrices;
    }

    public double getAskPriceStandardDeviation() {
        return askPriceStandardDeviation;
    }

    @Override
    public String toString() {
        return averagePurchasePrices + "\n"
                + askPriceStandardDeviation;
    }
}
