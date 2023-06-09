package com.github.kyleryxn.imagefinder.model;


import com.github.kyleryxn.imagefinder.crawler.WebCrawler;

/**
 * Represents an image object with properties such as name, domain, URL, and flags indicating if it is a logo or a favicon.
 * This class implements the {@link Comparable} interface to allow for sorting images based on their names. This class
 * is used as a model in the {@link WebCrawler} application for representing images found during web crawling.
 *
 * @author Kyle Schoenhardt
 * @version 1.0.2
 */
public class Image implements Comparable<Image> {
    private final String name;
    private final String domain;
    private final boolean isLogo;
    private final boolean isFavicon;
    private final String url;

    /**
     * Constructor for Image.
     *
     * @param name the name of the image.
     * @param domain the domain of the image.
     * @param isLogo flag indicating if the image is a logo.
     * @param isFavicon flag indicating if the image is a favicon.
     * @param url the URL of the image.
     */
    public Image(String name, String domain, boolean isLogo, boolean isFavicon, String url) {
        this.name = name;
        this.domain = domain;
        this.isLogo = isLogo;
        this.isFavicon = isFavicon;
        this.url = url;
    }

    /**
     * Returns the name of the image.
     *
     * @return the name of the image.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns The domain of the image.
     *
     * @return the domain of the image.
     */
    public String getDomain() {
        return domain;
    }

    /**
     * Returns a flag indicating if the image is a logo.
     *
     * @return flag indicating if the image is a logo.
     */
    public boolean isLogo() {
        return isLogo;
    }

    /**
     * Returns a flag indicating if the image is a favicon.
     *
     * @return flag indicating if the image is a favicon.
     */
    public boolean isFavicon() {
        return isFavicon;
    }

    /**
     * Returns the URL of the image.
     *
     * @return the URL of the image.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Overrides the toString() method of {@link Object}.
     * Returns a string representation of the Image object.
     *
     * @return a string representation of the Image object.
     */
    @Override
    public String toString() {
        return "Image{" +
                "name='" + name + '\'' +
                ", domain='" + domain + '\'' +
                ", isLogo=" + isLogo +
                ", isFavicon=" + isFavicon +
                ", url='" + url + '\'' +
                '}';
    }

    /**
     * Compares this Image object with another Image object based on their names.
     * Implements the compareTo() method of the {@link Comparable} interface.
     *
     * @param o the object to be compared.
     * @return a negative integer if this Image's name is less than the given Image's name,
     *         a positive integer if this Image's name is greater than the given Image's name,
     *         and 0 if both names are equal.
     */
    @Override
    public int compareTo(Image o) {
        return o.name.compareTo(this.name);
    }
}
