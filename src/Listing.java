public class Listing {
    private double price;
    private double shippingPrice;
    private String url;

    public Listing(double price, String url) {
        this.price = price;
        this.url = url;
    }

    public Listing() {
        this.price = 0;
        this.shippingPrice = 0;
        this.url = null;
    }

    public Listing(double price, double shippingPrice, String url) {
        this.price = price;
        this.url = url;
        this.shippingPrice = shippingPrice;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getShippingPrice() {
        return this.shippingPrice;
    }

    public void setShippingPrice(double shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public String getUrl() {
        return this.url;
    }

    public void setString(String url) {
        this.url = url;

    }

    public double getTotalPrice() {
        return this.price + this.shippingPrice;
    }
}