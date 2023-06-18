package utiltests;

import com.github.kyleryxn.imagefinder.crawler.WebCrawler;
import com.github.kyleryxn.imagefinder.model.Image;
import com.github.kyleryxn.imagefinder.util.ResourceUtility;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Resource Utility Tests")
public class ResourceUtilityTest {

    @Test
    @DisplayName("Test: Write to JSON")
    void testWriteToJSON() {
        String url = "https://www.avant-gardner.com";
        Map<String, Set<Image>> images = new WebCrawler(url).crawl();
        ResourceUtility.writeMapToJson("images.json", images);
        File file = new File("src/main/webapp/WEB-INF/images.json");

        assertTrue(file.exists());
    }
}
