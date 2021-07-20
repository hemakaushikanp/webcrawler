package com.org.webcrawler.service;

import com.org.webcrawler.request.CrawlerSearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Service
public class WebCrawlerServiceImpl implements WebCrawlerService {
    @Override
    public List<Map<String, List<String>>> performCrawlingForURL(CrawlerSearchRequest crawlerSearchRequest) {
        log.info("SERVICE:crawlerSearchRequest:" + crawlerSearchRequest.getIngestURLs().size());
        List<String> urlsWithoutDuplicates = new ArrayList<>(
                new HashSet<>(crawlerSearchRequest.getIngestURLs()));
        List<CompletableFuture<Map<String, List<String>>>> completableFutures = urlsWithoutDuplicates.stream()
                .map(url ->
                        CompletableFuture.supplyAsync(() ->
                                searchTextInURL(url, crawlerSearchRequest.getSearchText())))
                                      .collect(Collectors.toList());

        return completableFutures.stream().map(CompletableFuture::join)
                .collect(Collectors.toList());
    }


    public Map<String, List<String>> searchTextInURL(String url, String searchText) {
        try {
            Map<String, List<String>> resultMap = new HashMap<>();
            Document document = Jsoup.connect(url).get();
            List<String> textMatches = document.getAllElements().parallelStream().
                    filter(element -> element.text().toLowerCase().contains(searchText.toLowerCase())).distinct()
                    .map(Element::text).collect(Collectors.toList());
            resultMap.put(url, textMatches);
            return resultMap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
