package pl.parser.nbp;

public class CurrencyPrice {
    private float purchasePrice;
    private float askPrice;

    public float getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(float purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public float getAskPrice() {
        return askPrice;
    }

    public void setAskPrice(float askPrice) {
        this.askPrice = askPrice;
    }

    @Override
    public String toString() {
        return "[ PURCHASE: " + purchasePrice + ", ASK: " + askPrice +" ]";
    }
}
