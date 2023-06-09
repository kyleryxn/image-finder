package com.github.kyleryxn.imagefinder.util.image;

import com.github.kyleryxn.imagefinder.model.Image;
import com.github.kyleryxn.imagefinder.util.url.URLUtility;
import org.jsoup.nodes.Element;

/**
 * The ImageFactory class is responsible for creating {@link Image} objects from HTML img tags or link tags.
 */
public class ImageFactory {

    /**
     * Creates an {@link Image} object from an HTML img tag or link tag.
     *
     * @param imgTag the img or link tag element to create the Image object from.
     * @return an Image object representing the image extracted from the img or link tag, or null if the image could not be created.
     */
    public static Image createImage(Element imgTag) {
        String url = null;
        String domain = null;
        String name = "None";
        boolean isLogo = false;
        boolean isFavicon = false;

        if (imgTag.hasAttr("src")) {
            url = imgTag.absUrl("src");
            domain = URLUtility.getDomain(url);
            isLogo = url.contains("logo");

            if (imgTag.hasAttr("alt")) {
                name = !imgTag.attr("alt").isEmpty() ? imgTag.attr("alt") : "None";
            }

        } else if (imgTag.hasAttr("href")) {
            url = imgTag.absUrl("href");
            domain = URLUtility.getDomain(url);
            isFavicon = true;
        }

        if (url != null && domain != null) {
            return new Image(name, domain, isLogo, isFavicon, url);
        } else {
            return null;
        }
    }
}
