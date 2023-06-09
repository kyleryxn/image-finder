package com.github.kyleryxn.imagefinder.parser;

import com.github.kyleryxn.imagefinder.model.Image;
import com.github.kyleryxn.imagefinder.util.image.ImageFactory;
import com.github.kyleryxn.imagefinder.util.image.ImageUtility;
import com.github.kyleryxn.imagefinder.util.url.HTTPClient;
import com.github.kyleryxn.imagefinder.util.url.URLUtility;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * HTMLParser is an implementation of the {@link Parser} interface that is specifically designed
 * to parse HTML documents and extract links and image information.
 */
public class HTMLParser implements Parser {
    private static final Logger LOGGER = LoggerFactory.getLogger(HTMLParser.class);

    private final String domain;

    /**
     * Constructs an HTMLParser object with the specified domain.
     *
     * @param domain the domain to be used for parsing.
     */
    public HTMLParser(String domain) {
        this.domain = domain;
    }

    /**
     * Retrieves the domain associated with this object.
     *
     * @return the domain associated with this object.
     */
    @Override
    public String getDomain() {
        return this.domain;
    }

    /**
     * Retrieves the children URLs of the given URL.
     *
     * @param url the URL to retrieve children URLs from.
     * @return a {@link Set} of children URLs.
     */
    @Override
    public Set<String> getChildren(String url) {
        Document document = getDoc(url);

        if (document != null) {
            return document.select("a")
                    .stream()
                    .map(e -> e.attr("abs:href"))
                    .filter(URLUtility::isValidLinkToCrawl)
                    .filter(l -> URLUtility.isInDomain(l, domain))
                    .map(URLUtility::trimURL)
                    .collect(Collectors.toSet());
        }

        return new HashSet<>();
    }

    /**
     * Retrieves the image links from the specified URL.
     *
     * @param url the URL to retrieve image links from.
     * @return a {@link Set} of image links retrieved from the specified URL.
     */
    @Override
    public Set<String> getImageLinks(String url) {
        Document document = getDoc(url);

        if (document != null) {
            return document.select("img[src]").stream()
                    .map(link -> link.attr("abs:src"))
                    .filter(img -> !img.startsWith("data:"))
                    .filter(ImageUtility::isValidImageExtension)
                    .collect(Collectors.toSet());

        }

        return new HashSet<>();
    }

    /**
     * Retrieves a {@link Set} of {@link Image} objects from the specified URL.
     *
     * @param url the URL to retrieve Image objects from.
     * @return a Set of Image objects retrieved from the specified URL.
     */
    @Override
    public Set<Image> getImageObjects(String url) {
        Set<Image> images = new HashSet<>();
        Document document = getDoc(url);

        if (document != null) {
            Elements imgTags = document.select("img[src]");
            Element favicon = document.head().select("link[rel=icon], link[rel^=shortcut], link[rel^=apple-touch-icon]").first();

            images = imgTags.parallelStream()
                    .filter(img -> !img.absUrl("src").startsWith("data:"))
                    .filter(img -> ImageUtility.isValidImageExtension(img.absUrl("src")))
                    .map(ImageFactory::createImage)
                    .collect(Collectors.toSet());

            Optional.ofNullable(favicon)
                    .map(ImageFactory::createImage)
                    .ifPresent(images::add);

            return images;
        }

        return images;
    }

    /**
     * Retrieves the HTML document from the given URL using {@link Jsoup} library.
     * @param url the URL from which to retrieve the HTML document.
     * @return the {@link Document} object representing the HTML document, or null if retrieval fails.
     */
    private Document getDoc(String url) {
        try {
            Connection connection = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.4044.113 Safari/5370.36 Brave/5035")
                    .timeout(5000);
            return connection.get();
        } catch (IOException e) {
            LOGGER.error("Failed to retrieve HTML document {}", e.getMessage());
            return null;
        }
    }
}
