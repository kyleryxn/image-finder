package com.github.kyleryxn.imagefinder.parser;



import com.github.kyleryxn.imagefinder.model.Image;

import java.util.Set;

/**
 * Interface for parsing HTML content and extracting relevant information such as links and images.
 */
public interface Parser {

    /**
     * Get the domain of the parsed content.
     *
     * @return the domain of the parsed content.
     */
    String getDomain();

    /**
     * Get the {@link Set} of child URLs (links) found in the parsed content.
     *
     * @param url the URL of the content to parse.
     * @return the set of child URLs (links) found in the parsed content.
     */
    Set<String> getChildren(String url);

    /**
     * Get the {@link Set} of image links (URLs) found in the parsed content.
     *
     * @param url the URL of the content to parse.
     * @return the set of image links (URLs) found in the parsed content.
     */
    Set<String> getImageLinks(String url);

    /**
     * Get the {@link Set} of {@link Image} links (URLs) found in the parsed content.
     *
     * @param url the URL of the content to parse.
     * @return the set of image objects extracted from the parsed content.
     */
    Set<Image> getImageObjects(String url);
}
