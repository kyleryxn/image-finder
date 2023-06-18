package com.github.kyleryxn.imagefinder.crawler;

import com.github.kyleryxn.imagefinder.model.Image;
import com.github.kyleryxn.imagefinder.parser.Parser;
import com.github.kyleryxn.imagefinder.util.url.URLUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/**
 * Represents each crawl of a web crawler.
 */
public class Crawl implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Crawl.class);

    private final String domain;
    private final ConcurrentMap<String, Set<String>> sitemap;
    private final ConcurrentMap<String, Boolean> visitedLinks;
    private final ConcurrentMap<String, Set<Image>> images;
    private final ThreadPoolManager executorService;
    private final Parser parser;

    /**
     * Constructor for creating a Crawl object.
     *
     * @param domain the URL to be crawled.
     * @param sitemap the map for storing list of pages of the domain that meet certain criteria. See {@link URLUtility} for more information regarding the criteria.
     * @param visitedLinks the map for tracking visited links.
     * @param images the map for storing {@link Image} objects.
     * @param executorService the executor service for submitting crawl tasks.
     * @param parser the parser for extracting information from web pages.
     */
    public Crawl(String domain, ConcurrentMap<String, Set<String>> sitemap, ConcurrentMap<String, Boolean> visitedLinks,
                 ConcurrentMap<String, Set<Image>> images, ThreadPoolManager executorService, Parser parser) {
        this.domain = domain;
        this.sitemap = sitemap;
        this.visitedLinks = visitedLinks;
        this.images = images;
        this.executorService = executorService;
        this.parser = parser;
    }

    /**
     * {@link Runnable} implementation for crawling the web page associated with the URL.
     * Extracts links, updates sitemap, and processes child links.
     */
    @Override
    public void run() {
        // Log that the crawler is visiting the current url
        LOGGER.info("visiting {} ", domain);

        // Get the set of links from the current URL using the parser
        Set<String> links = parser.getChildren(domain);

        // Add the current URL and its associated hrefs to the sitemap
        sitemap.put(domain, links);

        // Process each link
        links.forEach(this::processLink);
    }

    /**
     * Process a link by checking if it has been visited, updating maps, and submitting new crawl tasks.
     *
     * @param url the link to be processed.
     */
    private void processLink(String url) {

        // Put if absent: if the link is not present in visitedLinks, add it with the value false
        visitedLinks.putIfAbsent(url, false);

        // If the link has not been visited yet
        if (!visitedLinks.get(url)) {

            // Mark the link as visited by updating its value to true
            visitedLinks.put(url, true);

            // Get the set of images on the page for the link
            Set<Image> imagesOnPage = parser.getImageObjects(url);

            // Add the link and its associated images to the images map
            images.put(url, imagesOnPage);

            // Create a new crawl task for the link and submit it to the executor service
            // **** This effectively keeps the crawler going ****
            Crawl crawl = new Crawl(url, sitemap, visitedLinks, images, executorService, parser);
            executorService.submit(crawl);
        }
    }
}
