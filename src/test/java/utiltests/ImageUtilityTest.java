package utiltests;

import com.github.kyleryxn.imagefinder.util.image.ImageUtility;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


@DisplayName("Image Utility Tests")
public class ImageUtilityTest {

    @Test
    @DisplayName("Test: Valid Image Extension")
    void testIsValidExtension() {
        String url = "test-image.svg";
        boolean result = ImageUtility.isValidImageExtension(url);

        assertTrue(result);
    }
}
