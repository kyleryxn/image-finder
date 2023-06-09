package com.github.kyleryxn.imagefinder.util.url;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;

/**
 * A simple HTTP client utility for checking the validity of a URL by making a connection to the URL
 * and checking the response code.
 * @see URL
 */
public class HTTPClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(HTTPClient.class);
    private String successMessage;
    private String failureMessage;
    private boolean isValid;

    /**
     * Check if a given URL is valid by making a connection to the URL and checking the response code.
     * If the response code is 200 ({@link URLStatus#HTTP_OK}), it sets the success message, sets isValid to true,
     * and returns this object, otherwise, it sets the failure message, sets isValid to false, and returns this object.
     * If an exception occurs while making the URL connection, it logs an message message, sets isValid to false, and returns this object.
     *
     * @param url the URL to be checked
     * @return this object
     */
    public HTTPClient checkURL(String url) {
        isValid = false;

        if (url == null || url.isEmpty()) {
            return this;
        }

        try {
            URL aURL = new URL(url);
            HttpsURLConnection urlConnection = (HttpsURLConnection) aURL.openConnection();

            if (urlConnection.getResponseCode() == URLStatus.HTTP_OK.getStatusCode()) {
                successMessage = "URL at '" + url + "' returned " + URLStatus.getStatusMessageForStatusCode(urlConnection.getResponseCode());
                isValid = true;
            } else {
                failureMessage = "URL at '" + url + "' returned " + URLStatus.getStatusMessageForStatusCode(urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            LOGGER.error("Failed to connect to URL, {}", e.getMessage());
        }

        return this;
    }

    /**
     * Get the success message for a valid URL connection.
     *
     * @return the success message
     */
    public String getSuccessMessage() {
        return successMessage;
    }

    /**
     * Get the failure message for a valid URL connection.
     *
     * @return the failure message
     */
    public String getFailureMessage() {
        return failureMessage;
    }

    /**
     * Get the message for a valid URL connection.
     *
     * @return the message (success or failure)
     */
    public String getMessage() {
        if (successMessage != null) {
            return successMessage;
        } else {
            return failureMessage;
        }
    }

    /**
     * Check if the URL is valid.
     *
     * @return true if the URL is valid, false otherwise
     */
    public boolean isValid() {
        return isValid;
    }
}
