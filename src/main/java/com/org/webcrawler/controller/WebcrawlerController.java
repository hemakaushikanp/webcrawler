package com.org.webcrawler.controller;

import com.org.webcrawler.request.CrawlerSearchRequest;
import com.org.webcrawler.service.WebCrawlerService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/crawler-search")
public class WebcrawlerController {

    @Autowired
    WebCrawlerService webCrawlerService;

    @PostMapping
    public void performCrawlerSearch(@NonNull @RequestBody CrawlerSearchRequest crawlerSearchRequest) throws IOException {
        log.info("START : performCrawlerSearch() for text: " + crawlerSearchRequest.getSearchText());
//        webCrawlerService=(searchRequest)->{
//            //System.out.println("Drawing "+width);
//            return null;
//        };
        webCrawlerService.performCrawling(crawlerSearchRequest);

    }
}
