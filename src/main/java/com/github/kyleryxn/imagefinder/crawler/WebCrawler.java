package com.github.kyleryxn.imagefinder.crawler;

import com.github.kyleryxn.imagefinder.model.Image;
import com.github.kyleryxn.imagefinder.model.Statistic;
import com.github.kyleryxn.imagefinder.parser.HTMLParser;
import com.github.kyleryxn.imagefinder.parser.Parser;
import com.github.kyleryxn.imagefinder.util.ResourceUtility;
import com.github.kyleryxn.imagefinder.util.url.HTTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents a web crawler that visits URLs and extracts information like links and images
 * from the web pages using a provided parser.
 */
public class WebCrawler {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebCrawler.class);
    private static final int MAX_THREADS = 10;
    private static final long MAX_IDLE_TIME = 10_000_000_000L; // 10s

    // Manager to handle all the threads
    private final ThreadPoolManager executorService = new ThreadPoolManager(MAX_THREADS, MAX_THREADS, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

    // The seed url
    private final String domain;

    // The sitemap
    private final ConcurrentMap<String, Set<String>> sitemap = new ConcurrentHashMap<>();

    // Map containing already visited urls
    private final ConcurrentMap<String, Boolean> visitedLinks = new ConcurrentHashMap<>();

    // All the images gathered from the site, no duplicates
    // The url the image is gathered from serves as the key
    private final ConcurrentMap<String, Set<Image>> images = new ConcurrentHashMap<>();

    // Parser instance
    private final Parser parser;

    private long startTime;

    /**
     * Constructor for creating a WebCrawler object with the domain and default {@link HTMLParser}.
     *
     * @param domain the seed URL to start crawling.
     */
    public WebCrawler(String domain) {
        this(domain, new HTMLParser(domain));
    }

    /**
     * Constructor for creating a WebCrawler object with the domain and a custom parser.
     *
     * @param domain the seed URL to start crawling.
     * @param parser the parser for extracting information from web pages.
     */
    public WebCrawler(String domain, Parser parser) {
        HTTPClient client = new HTTPClient();
        String message = client.checkURL(domain).getMessage();
        System.out.println(message);

        if (client.isValid())
            this.domain = domain;
        else
            this.domain = null;

        this.parser = parser;
    }

    /**
     * Starts the web crawling process from the seed URL and waits until all crawling tasks are completed.
     *
     * @return the map of URLs and their associated images gathered from the crawled web pages.
     */
    public ConcurrentMap<String, Set<Image>> crawl() {

        // Add seed url to 'visitedLinks' map
        visitedLinks.put(domain, true);

        // Crawl seed page first
        executorService.submit(new Crawl(domain, sitemap, visitedLinks, images, executorService, parser));

        startTime = System.currentTimeMillis();

        // Loop continues as long as there are threads in the queue
        // Or if the difference between the current time and the last execution time
        // of 'poolManager' is less than 'MAX_IDLE_TIME' in nanoseconds
        while (true) {
            try {
                Thread.sleep(1000);
                long idleNano = System.nanoTime() - executorService.getLastExecutionTime();
                if (executorService.getQueue().size() == 0 && idleNano >= MAX_IDLE_TIME) {
                    executorService.shutdown();
                    boolean terminated = executorService.awaitTermination(10, TimeUnit.SECONDS);
                    if (terminated) {
                        LOGGER.info("terminating executor service");
                        break;
                    }
                }
            } catch (InterruptedException e) {
                LOGGER.error(e.toString());
                break;
            }
        }

        // Create JSON records
        ResourceUtility.writeMapToJson("images.json", images);
        ResourceUtility.writeMapToJson("sitemap.json", sitemap);
        printStats();

        return images;
    }

    private void printStats() {
        long totalTime = (System.currentTimeMillis() - startTime) / 1000; // Total time in seconds
        double crawlingSpeed = (double) visitedLinks.size() / totalTime;

        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.UP);

        LOGGER.info("Visited links: {}", visitedLinks.size());
        LOGGER.info("Images extracted: {}", images.size());
        LOGGER.info("Total Time (seconds): {}", /*TimeUnit.SECONDS.toMinutes(totalTime)*/ totalTime);
        LOGGER.info("Average Crawling Speed (pages/second): {}", crawlingSpeed);

        ResourceUtility.writeStatsToJson(new Statistic(images.size(), visitedLinks.size(), totalTime, df.format(crawlingSpeed)));
    }
}
