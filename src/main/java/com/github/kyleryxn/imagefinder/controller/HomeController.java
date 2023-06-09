package com.github.kyleryxn.imagefinder.controller;

import com.github.kyleryxn.imagefinder.crawler.WebCrawler;
import com.github.kyleryxn.imagefinder.model.Image;
import com.github.kyleryxn.imagefinder.util.url.HTTPClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    private boolean showMessage = false;

    @GetMapping("/")
    public String welcome(Model model) {
        model.addAttribute("showMessage", showMessage);
        model.addAttribute("message", "");
        return "index";
    }

    @PostMapping("/crawl")
    public String getImages(@RequestParam("url") String url, Model model) {
        HTTPClient client = new HTTPClient();
        model.addAttribute("url", url);

        showMessage = !showMessage;

        // Check incoming url
        boolean flag = client.checkURL(url).isValid();
        String message = client.getMessage();
        System.out.println(message);

        if (flag) {
            // Start the crawler
            Set<Image> images = new WebCrawler(url).crawl().values()
                    .stream()
                    .flatMap(Set::stream)
                    .collect(Collectors.toSet());

            model.addAttribute("images", images);
            model.addAttribute("showMessage", showMessage);
            model.addAttribute("message", message);
        } else {
            model.addAttribute("images", new HashSet<Image>());
            model.addAttribute("showMessage", showMessage);
            model.addAttribute("message", "");
        }

        return "index";
    }


}
