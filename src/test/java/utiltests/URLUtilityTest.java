package utiltests;

import com.github.kyleryxn.imagefinder.util.url.URLUtility;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("URL Utility Tests")
public class URLUtilityTest {

    @Test
    @DisplayName("Test: Valid Link to Crawl")
    void testIsValidLinkToCrawl() {
        String validURL = "https://wecodeucate.org/";
        String invalidURL = "javascript:void()";
        String invalidExtension = "something.pdf";

        boolean validResult = URLUtility.isValidLinkToCrawl(validURL);
        boolean invalidResult = URLUtility.isValidLinkToCrawl(invalidURL);
        boolean invalidExtensionResult = URLUtility.isValidLinkToCrawl(invalidExtension);

        assertTrue(validResult);
        assertFalse(invalidResult);
        assertFalse(invalidExtensionResult);
    }

    @Test
    @DisplayName("Test: Get Domain")
    void testGetDomain() {
        String url = "https://wecodeucate.org/";
        String expected = "wecodeucate.org";
        String actual = URLUtility.getDomain(url);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test: Is Same Domain")
    void testIsInDomain() {
        String domain = "https://paper.co/";
        String urlOne = "https://paper.co/blog/online-tutoring-cant-happen-without-this";
        String urlTwo = "https://www.instagram.com/paperlearning/";

        boolean resultOne = URLUtility.isInDomain(urlOne, domain);
        boolean resultTwo = URLUtility.isInDomain(urlTwo, domain);

        assertTrue(resultOne);
        assertFalse(resultTwo);
    }
}
