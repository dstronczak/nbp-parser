package pl.parser.nbp;

enum PriceType {PURCHASE, ASK}

public class CurrencyPrice {
    private double purchasePrice;
    private double askPrice;

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public void setAskPrice(double askPrice) {
        this.askPrice = askPrice;
    }

    @Override
    public String toString() {
        return "[ PURCHASE: " + purchasePrice + ", ASK: " + askPrice +" ]";
    }

    public double getPrice(PriceType type) {
        if(type == PriceType.PURCHASE){
            return purchasePrice;
        } else {
            return askPrice;
        }
    }


}
