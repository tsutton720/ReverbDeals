import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {

        App app = new App();

        ArrayList<String> urls = new ArrayList<>();
        String url = "https://reverb.com/p/mxr-m238-iso-brick";

        urls.add(url);

        // Print All Data
        // for (Item x : items) {
        // System.out.println(x.getAvgPrice() + " " + x.getUrl() + "\n\n\n");

        // for (Listing y : x.getListings()) {
        // System.out.println(y.getPrice() + " " + y.getShippingPrice() + " " +
        // y.getUrl() + "\n");
        // }
        // }
        app.sendEmail();

        // while (false) {
        // ArrayList<Item> items = app.generateItems(urls);

        // for (Item item : items) {
        // double avg = item.getAvgPrice();

        // for (Listing list : item.getListings()) {
        // if (list.getTotalPrice() < avg) {
        // // email
        // app.sendEmail("tsutton720@gmail.com", "tsutton720@gmail.com", list.getUrl(),
        // list.getTotalPrice(), item.getAvgPrice());

        // }

        // }

        // }

        // System.out.println("Loop ran once");
        // TimeUnit.MINUTES.sleep(30);

        // }

    }

    private ArrayList<Item> generateItems(ArrayList<String> urls) {

        ArrayList<Item> trackers = new ArrayList<>();
        for (String x : urls) {
            Document doc = getDoc(x);
            ArrayList<Listing> listings = populateListing(doc);
            double avgPrice = getAveragePrice(doc);

            trackers.add(new Item(avgPrice, x, listings));

        }
        return trackers;
    }

    private ArrayList<Listing> populateListing(Document doc) {
        ArrayList<Listing> listings = new ArrayList<>();
        Elements links = getElements(doc);

        for (Element link : links) {
            Elements s = link.select("span.price-display");
            double itemPrice = 0;
            double shipPrice = 0;
            String itemLink = link.attr("href");

            int counter = 0;
            for (Element prices : s) {

                if (counter == 0) {
                    itemPrice = Double.parseDouble(prices.ownText().replace("$", ""));
                    counter++;
                }

                else if (counter == 1) {
                    shipPrice = Double.parseDouble(prices.ownText().replace("$", ""));
                    counter = 0;
                }

            }

            Listing x = new Listing(itemPrice, shipPrice, itemLink);

            listings.add(x);

        }
        return listings;

    }

    private Elements getElements(Document doc) {

        Element list = doc.select("ul.tiles.tiles--one-wide").first();
        Elements li = list.select("li.tiles__tile");
        Elements links = li.select("a[href].listing-row-card__inner");
        return links;

    }

    private Document getDoc(String url) {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        WebDriver driver = new ChromeDriver(options);
        driver.get(url);
        Document doc = Jsoup.parse(driver.getPageSource());

        return doc;
    }

    private double getAveragePrice(Document doc) {

        Element table = doc.select("table.price-guide-table__element").first();
        Elements soldPrices = table.select("td.align-right");

        double total = 0;
        double numElements = 0;

        for (Element x : soldPrices) {
            total += Double.parseDouble(x.ownText().replace("$", ""));
            numElements++;
        }

        return total / numElements;

    }

    private void sendEmail() {
        // Recipient's email ID needs to be mentioned.
        String to = "tsutton720@gmail.com";

        // Sender's email ID needs to be mentioned
        String from = "web@gmail.com";

        // Assuming you are sending email from localhost
        String host = "localhost";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("This is the Subject Line!");

            // Now set the actual message
            message.setText("This is actual message");

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

}
