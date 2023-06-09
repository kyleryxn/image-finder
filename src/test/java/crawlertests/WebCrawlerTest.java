package crawlertests;

import com.github.kyleryxn.imagefinder.crawler.WebCrawler;
import com.github.kyleryxn.imagefinder.model.Image;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

@DisplayName("Web Crawler Tests")
public class WebCrawlerTest {

    @Test
    @DisplayName("Test: Crawl")
    void testCrawl() {
        String url = "https://www.brex.com/";
        Map<String, Set<Image>> images = new WebCrawler(url).crawl();

        images.forEach((k, v) -> {
            System.out.println("\nURL '" + k + "':\n\tImages:");
            v.forEach(img -> System.out.println("\t\t" + img));
        });
    }
}
