import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {

    public static void main(String[] args) {
        // Set up WebDriver (ChromeDriver)
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        // Navigate to a webpage
        driver.get("https://accounts.lambdatest.com/login");

        // Get the page source
        String pageSource = driver.getPageSource();

        // Parse the HTML content using JSoup
        Document document = Jsoup.parse(pageSource);

        // Select the relevant elements you want to extract
        Elements elements = document.select("input, button");  // Add more tags as needed

        // Create a CSV file and write data to it
        try (PrintWriter writer = new PrintWriter(new FileWriter("elements.csv"))) {
            // Write the CSV header
            writer.println("element,tag,id,type,class,name,aria-autocomplete,title,href,text,value,aria-label");

            // Loop through each element and extract the required attributes
            for (Element element : elements) {
                // Get each attribute (use "None" if the attribute doesn't exist)
                String tag = element.tagName();
                String id = element.attr("id").isEmpty() ? "None" : element.attr("id");
                String type = element.attr("type").isEmpty() ? "None" : element.attr("type");
                String className = element.attr("class").isEmpty() ? "None" : element.attr("class");
                String name = element.attr("name").isEmpty() ? "None" : element.attr("name");
                String ariaAutocomplete = element.attr("aria-autocomplete").isEmpty() ? "None" : element.attr("aria-autocomplete");
                String title = element.attr("title").isEmpty() ? "None" : element.attr("title");
                String href = element.attr("href").isEmpty() ? "None" : element.attr("href");
                String text = element.text().isEmpty() ? "None" : element.text().replace("\"", "\"\"");
                String value = element.attr("value").isEmpty() ? "None" : element.attr("value");
                String ariaLabel = element.attr("aria-label").isEmpty() ? "None" : element.attr("aria-label");

                // Write the attributes to the CSV row
                writer.println(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,\"%s\",%s,%s",
                        element.tagName(),
                        tag,
                        id,
                        type,
                        className,
                        name,
                        ariaAutocomplete,
                        title,
                        href,
                        text,
                        value,
                        ariaLabel
                ));
            }

            System.out.println("CSV file created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}
