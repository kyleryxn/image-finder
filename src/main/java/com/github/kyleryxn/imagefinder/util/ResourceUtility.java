package com.github.kyleryxn.imagefinder.util;

import com.github.kyleryxn.imagefinder.model.Image;
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
    private static final String FILE_PATH = "src/main/webapp/WEB-INF/images.json";

    /**
     * Writes a {@link Map} of Strings to Sets of Images to a JSON file.
     *
     * @param imageMap the Map of Strings to Sets of Images to be written to JSON
     * @see Set
     * @see Image
     */
    public static void writeImageMapToJson(Map<String, Set<Image>> imageMap) {
        Gson gson = new GsonBuilder()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .create();
        String json = gson.toJson(imageMap);

        try (Writer writer = new FileWriter(FILE_PATH);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {

            bufferedWriter.write(json);
            System.out.println("Image map data has been written to '" + FILE_PATH + "'");
        } catch (IOException e) {
            LOGGER.error("Cannot write file : {}", e.getMessage());
        }
    }
}
