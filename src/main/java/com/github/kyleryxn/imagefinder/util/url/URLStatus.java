package com.github.kyleryxn.imagefinder.util.url;

import java.util.Arrays;

/**
 * URLStatus is an enumeration that represents different HTTP status codes along with their corresponding HTTP messages and results.
 */
public enum URLStatus {
    HTTP_OK(200, "OK", "SUCCESS"),
    NO_CONTENT(204, "No Content", "SUCCESS"),
    MOVED_PERMANENTLY(301, "Moved Permanently", "SUCCESS"),
    NOT_MODIFIED(304, "Not Modified", "SUCCESS"),
    USE_PROXY(305, "Use Proxy", "SUCCESS"),
    FORBIDDEN(403, "Forbidden", "ERROR"),
    NOT_FOUND(404, "Not Found", "ERROR"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error", "ERROR");

    private final int statusCode;
    private final String httpMessage;
    private final String result;

    /**
     * Get the HTTP status code.
     *
     * @return the HTTP status code.
     */
    public int getStatusCode() {
        return this.statusCode;
    }

    /**
     * Get the HTTP message.
     *
     * @return the HTTP message.
     */
    public String getHttpMessage() {
        return httpMessage;
    }

    /**
     * Get the HTTP result
     *
     * @return the HTTP result
     */
    public String getResult() {
        return result;
    }

    /**
     * Constructor for URLStatus enumeration.
     *
     * @param statusCode the HTTP status code.
     * @param httpMessage the HTTP status message.
     * @param result the result of the status code.
     */
    URLStatus(int statusCode, String httpMessage, String result) {
        this.statusCode = statusCode;
        this.httpMessage = httpMessage;
        this.result = result;
    }

    /**
     * Get the HTTP status message for a given HTTP status code.
     *
     * @param httpCode the HTTP status code.
     * @return the HTTP status message, or "Status Not Defined" if not found.
     */
    public static String getStatusMessageForStatusCode(int httpCode) {
        return Arrays.stream(URLStatus.values())
                .filter(status -> status.statusCode == httpCode)
                .map(URLStatus::getHttpMessage)
                .findFirst()
                .orElse("Status Not Defined");

    }

    /**
     * Get the result for a given HTTP status code.
     *
     * @param httpCode the HTTP status code.
     * @return the result, or "Result Not Defined" if not found
     */
    public static String getResultForStatusCode(int httpCode) {
        return Arrays.stream(URLStatus.values())
                .filter(status -> status.statusCode == httpCode)
                .map(URLStatus::getResult)
                .findFirst()
                .orElse("Result Not Defined");
    }

}
