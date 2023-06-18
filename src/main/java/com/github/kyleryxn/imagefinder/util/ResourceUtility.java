package com.github.kyleryxn.imagefinder.util;

import com.github.kyleryxn.imagefinder.model.Image;
import com.github.kyleryxn.imagefinder.model.Statistic;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Set;

/**
 * Utility class for writing a {@link Map} of {@link String} paired with a {@link Set} of type {@link Image} to a JSON file using {@link Gson} library.
 */
public class ResourceUtility {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceUtility.class);
    private static final String FILE_PATH = "src/main/webapp/WEB-INF/";
    private static final Gson gson = new GsonBuilder()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .create();

    /**
     * Writes a {@link Map} of Strings to Sets of Images to a JSON file.
     *
     * @param map the Map of Strings to Sets of T to be written to JSON
     * @see Set
     * @see Image
     */
    public static <T> void writeMapToJson(String filename, Map<String, Set<T>> map) {
        String json = gson.toJson(map);

        try (Writer writer = new FileWriter(FILE_PATH + filename);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {

            bufferedWriter.write(json);
            System.out.println("Image map data has been written to '" + FILE_PATH + filename + "'");
        } catch (IOException e) {
            LOGGER.error("Cannot write file : {}", e.getMessage());
        }
    }

    public static void writeStatsToJson(Statistic statistics) {
        String json = gson.toJson(statistics);

        try (Writer writer = new FileWriter(FILE_PATH + "stats.json");
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {

            bufferedWriter.write(json);
            System.out.println("Statistics data has been written to '" + FILE_PATH + "stats.json" + "'");
        } catch (IOException e) {
            LOGGER.error("Cannot write file : {}", e.getMessage());
        }
    }
}
