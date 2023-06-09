package com.github.kyleryxn.imagefinder.util.url;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Utility class for working with URLs.
 * @see URL
 */
public class URLUtility {
    private static final Logger LOGGER = LoggerFactory.getLogger(URLUtility.class);

    /**
     * Checks if the given URL is a valid link to crawl based on common invalid link extensions.
     *
     * @param url the URL to check for validity
     * @return the URL to check for validity
     */
    public static boolean isValidLinkToCrawl(String url) {
        if (url == null || url.isEmpty()) {
            return false;
        }

        List<String> invalidExtensions = Arrays.asList(
                "#", "javascript:", "mailto:", "tel:",              // Invalid link starters
                ".pdf",                                             // Skip PDFs
                ".jpg", ".jpeg", ".png", ".gif", ".ico", ".svg",    // Skip images
                ".doc", ".docx", ".csv", ".xlsx", ".ppt", ".pptx",  // Skip office files
                ".zip", ".rar",                                     // Skip compressed files
                ".mp3", ".wav", ".ogg",                             // Skip music files
                ".mp4", ".avi", ".mov",                             // Skip video files
                ".xml", ".json", ".js", ".css", ".txt");            // Skip other irrelevant files

        for (String invalidExtension : invalidExtensions) {
            if (url.startsWith(invalidExtension) || url.endsWith(invalidExtension)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Trims the given URL by removing query parameters, URL fragments, and trailing slashes.
     *
     * @param url the URL to trim.
     * @return the url to trim.
     */
    public static String trimURL(String url) {
        if (url == null) return null;

        // Remove query parameters if present
        int queryIndex = url.indexOf('?');
        if (queryIndex != -1) {
            url = url.substring(0, queryIndex);
        }

        // Remove URL fragment if present
        int fragmentIndex = url.indexOf('#');
        if (fragmentIndex != -1) {
            url = url.substring(0, fragmentIndex);
        }

        // Remove trailing slash if present
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }

        return url;
    }

    /**
     * Gets the domain of the given URL by parsing the host from the {@link URI} and removing "www." if present.
     *
     * @param url the URL to get the domain from.
     * @return the domain of the URL, or {@code null} if the URL is invalid.
     */
    public static String getDomain(String url) {
        try {
            String domain = new URI(url).getHost();
            return domain.replace("www.", "");
        } catch (URISyntaxException e) {
            LOGGER.error("Failed to parse URL '{}'", e.getMessage());
            return null;
        }
    }

    /**
     * Checks if the given URL is within the specified domain.
     *
     * @param url the URL to check
     * @param domain the domain to check against
     * @return {@code true} if the URL is within the domain, {@code false} otherwise
     */
    public static boolean isInDomain(String url, String domain) {
        if (url == null || url.isEmpty()) return false;
        return url.startsWith(domain);
    }
}
