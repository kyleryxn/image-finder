package com.github.kyleryxn.imagefinder.model;

public class Statistic {
    private final int numImagesExtracted;
    private final int numVisitedLinks;
    private final long totalSeconds;
    private final String pagesPerSecond;

    public Statistic(int numImagesExtracted, int numVisitedLinks, long totalSeconds, String pagesPerSecond) {
        this.numImagesExtracted = numImagesExtracted;
        this.numVisitedLinks = numVisitedLinks;
        this.totalSeconds = totalSeconds;
        this.pagesPerSecond = pagesPerSecond;
    }

    public int getNumImagesExtracted() {
        return numImagesExtracted;
    }

    public int getNumVisitedLinks() {
        return numVisitedLinks;
    }

    public long getTotalTime() {
        return totalSeconds;
    }

    public String getAvgSpeed() {
        return pagesPerSecond;
    }

    @Override
    public String toString() {
        return "Statistic{" +
                "numImagesExtracted=" + numImagesExtracted +
                ", numVisitedLinks=" + numVisitedLinks +
                ", totalSeconds=" + totalSeconds +
                ", pagesPerSecond=" + pagesPerSecond +
                '}';
    }
}
