package com.org.webcrawler.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.webcrawler.jsonmapper.CrawlerMapper;
import com.org.webcrawler.request.CrawlerSearchRequest;
import com.org.webcrawler.response.CrawlerSearchResponse;
import com.org.webcrawler.service.WebCrawlerService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("/crawler-search")
public class WebcrawlerController {
    @Autowired
    WebCrawlerService webCrawlerService;

    @Autowired
    private ObjectMapper objectMapper;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(CrawlerSearchRequest.class, new CrawlerMapper(objectMapper));
    }

    @PostMapping(path = "/parallel", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public CrawlerSearchResponse performCrawlerSearchInParallel(@NonNull @RequestBody CrawlerSearchRequest crawlerSearchRequest) throws IOException, ExecutionException, InterruptedException {
        log.info("START : performCrawlerSearch() for text: " + crawlerSearchRequest.getSearchText());
        return new CrawlerSearchResponse(webCrawlerService.performCrawlingForURL(crawlerSearchRequest));
    }

    @GetMapping(path = "/parallel", produces = "application/json")
    @ResponseBody
    public CrawlerSearchResponse performCrawlerSearch(@NonNull @RequestBody CrawlerSearchRequest crawlerSearchRequest) throws IOException, ExecutionException, InterruptedException {
        log.info("START : performCrawlerSearch() for text: " + crawlerSearchRequest.getSearchText());
        return new CrawlerSearchResponse(webCrawlerService.performCrawlingForURL(crawlerSearchRequest));
    }

    @GetMapping("/")
    @ResponseBody
    public CrawlerSearchResponse performCrawlerSearch(@NonNull @RequestParam String crawlerSearch) throws IOException, InterruptedException, ExecutionException {
        return new CrawlerSearchResponse(webCrawlerService.performCrawlingForURL(objectMapper.readValue(crawlerSearch, CrawlerSearchRequest.class)));
    }
}
