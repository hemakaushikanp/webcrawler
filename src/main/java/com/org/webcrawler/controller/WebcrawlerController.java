package com.org.webcrawler.controller;

import com.org.webcrawler.request.CrawlerSearchRequest;
import com.org.webcrawler.service.WebCrawlerService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@RestController
@RequestMapping("/crawler-search")
public class WebcrawlerController {

    @Autowired
    WebCrawlerService webCrawlerService;

    @PostMapping
    public HashMap performCrawlerSearch(@NonNull @RequestBody CrawlerSearchRequest crawlerSearchRequest) throws IOException {
        log.info("START : performCrawlerSearch() for text: " + crawlerSearchRequest.getSearchText());
        HashMap<String, List<String>> resultMap = webCrawlerService.performCrawling(crawlerSearchRequest);
        return resultMap;
    }

    @PostMapping(path = "/parallel", consumes = "application/json", produces = "application/json")
    public List performCrawlerSearchInParallel(@NonNull @RequestBody CrawlerSearchRequest crawlerSearchRequest) throws IOException, ExecutionException, InterruptedException {
        log.info("START : performCrawlerSearch() for text: " + crawlerSearchRequest.getSearchText());
        List<HashMap<String, List<String>>> resultMap = webCrawlerService.performCrawlingForURL(crawlerSearchRequest);
        return resultMap;
    }
}
