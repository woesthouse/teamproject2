package NovelNetwork.NovelNetwork.scrayper;

import NovelNetwork.NovelNetwork.Domain.Book;
import NovelNetwork.NovelNetwork.Repository.BookRepository;
import jakarta.annotation.PostConstruct;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class Kyobobook {

    @Autowired
    private BookRepository bookRepository;

    @PostConstruct
    public void init() {
        scrapeBooksFromKyobobook();
    }

    public void scrapeBooksFromKyobobook() {
        System.setProperty("webdriver.chrome.driver", "chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless");

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("https://product.kyobobook.co.kr/bestseller/online?period=001");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("prod_item")));

            String html = driver.getPageSource();

            Document doc = Jsoup.parse(html);
            Elements bookItems = doc.select("li.prod_item");

            for (Element item : bookItems) {
                String bookTitle = item.select("span.prod_name").text();
                String authorAndDate = item.select("span.prod_author").text();
                String introduction = item.select("p.prod_introduction").text();
                String rating = item.select("span.review_klover_text.font_size_xxs").text();
                String imgUrl = item.select("img[data-kbbfn='s3-image'][loading='lazy']").first().attr("data-src");

                // Extract only the author from authorAndDate string
                String author = authorAndDate.split("\\|")[0].trim();

                // Extract only the first author if multiple authors are listed
                if (author.contains(" ")) {
                    author = author.split(" ")[0];
                }

                // If the image URL is relative, prepend the website's root URL
                if (!imgUrl.startsWith("http")) {
                    imgUrl = "http://www.kyobobook.co.kr/" + imgUrl;
                }

                // Empty strings if null
                bookTitle = bookTitle != null ? bookTitle : "";
                author = author != null ? author : "";
                introduction = introduction != null ? introduction : "";
                rating = rating != null ? rating : "0.0";

                insertIntoDB(bookTitle, author, introduction, Float.parseFloat(rating), imgUrl);
            }

        } catch (Exception e) {
            System.out.println("An error occurred during scraping: " + e.getMessage());
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    public void insertIntoDB(String title, String authorAndDate, String contents, Float rating, String coverImage) {
        try {
            Book book = new Book();
            book.setTitle(title);
            book.setAuthor(authorAndDate);
            book.setContents(contents);
            book.setRating(rating);
            book.setCoverImage(coverImage);

            bookRepository.save(book);
        } catch (Exception e) {
            System.out.println("An error occurred during database insertion: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
