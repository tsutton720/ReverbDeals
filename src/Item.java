import java.util.ArrayList;

public class Item {
    private double AVGprice;
    private String url;
    private ArrayList<Listing> listings;

    public Item() {
        this.AVGprice = 0;
        this.url = null;
        this.listings = null;
    }

    public Item(double avgPrice, String link, ArrayList<Listing> listing) {
        this.AVGprice = avgPrice;
        this.url = link;
        this.listings = listing;
    }

    public double getAvgPrice() {
        return this.AVGprice;

    }

    public String getUrl() {
        return this.url;
    }

    public ArrayList<Listing> getListings() {
        return this.listings;
    }

    public void setAvgPrice(double price) {
        this.AVGprice = price;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}