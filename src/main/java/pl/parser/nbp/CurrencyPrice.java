package pl.parser.nbp;

public class CurrencyPrice {
    private float purchasePrice;
    private float askPrice;

    public void setPurchasePrice(float purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public void setAskPrice(float askPrice) {
        this.askPrice = askPrice;
    }

    @Override
    public String toString() {
        return "[ PURCHASE: " + purchasePrice + ", ASK: " + askPrice +" ]";
    }

    public float getPrice(PriceType type){
        if(type == PriceType.PURCHASE){
            return purchasePrice;
        } else {
            return askPrice;
        }
    }


}

enum PriceType { PURCHASE, ASK }
