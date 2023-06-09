package com.github.kyleryxn.imagefinder.util.image;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * The ImageUtility class provides utility methods for working with image files and URLs.
 * @see URL
 */
public class ImageUtility {

    /**
     * Checks if a given URL has a valid image file extension.
     *
     * @param url the URL to check for valid image file extension
     * @return true if the URL has a valid image file extension, false otherwise
     * @see URL
     */
    public static boolean isValidImageExtension(String url) {
        List<String> validImageExtensions = Arrays.asList(".gif", ".ico", ".jpg", ".jpeg", ".png", ".svg");

        for (String extension : validImageExtensions) {
            if (url.endsWith(extension)) {
                return true;
            }
        }

        return false;
    }
}
