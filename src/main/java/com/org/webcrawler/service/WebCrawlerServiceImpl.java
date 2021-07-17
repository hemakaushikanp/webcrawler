package com.org.webcrawler.service;

import com.org.webcrawler.request.CrawlerSearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Slf4j
@Service
public class WebCrawlerServiceImpl implements WebCrawlerService {
    @Override
    public HashMap<String, List<String>> performCrawling(CrawlerSearchRequest crawlerSearchRequest) throws IOException {
        log.info("SERVICE:crawlerSearchRequest:" + crawlerSearchRequest.getIngestURLs().size());
        HashMap<String, List<String>> resultMap = new HashMap<String, List<String>>();
        crawlerSearchRequest.getIngestURLs().forEach(url -> {
            try {
                Document document = Jsoup.connect(url).get();
                System.out.println("element size : " + document.getAllElements().size());
                List<String> textMatches = document.getAllElements().stream().
                        filter(element -> element.text().toLowerCase().contains(crawlerSearchRequest.getSearchText().toLowerCase())).distinct()
                        .map(Element::text).collect(Collectors.toList());
                resultMap.put(url, textMatches);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return resultMap;
    }

    @Override
    public List<HashMap<String, List<String>>> performCrawlingForURL(CrawlerSearchRequest crawlerSearchRequest) throws IOException, ExecutionException, InterruptedException {
        log.info("SERVICE:crawlerSearchRequest:" + crawlerSearchRequest.getIngestURLs().size());
        List<String> urlsWithoutDuplicates = new ArrayList<String>(
                new HashSet<>(crawlerSearchRequest.getIngestURLs()));
        List<CompletableFuture<HashMap<String, List<String>>>> completableFutures = urlsWithoutDuplicates.stream()
                .map(url ->
                        CompletableFuture.supplyAsync(() ->
                                searchTextInURL(url, crawlerSearchRequest.getSearchText())))
                       /* {
                                HashMap<String, List<String>> resultMapForURL = searchTextInURL(url, crawlerSearchRequest.getSearchText());
                                return resultMapForURL;
                        }
                        ))*/
                .collect(Collectors.toList());

        return completableFutures.stream().map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    private HashMap<String, List<String>> searchTextInURL(String url, String searchText) {
        try {
            HashMap<String, List<String>> resultMap = new HashMap<String, List<String>>();
            Document document = Jsoup.connect(url).get();
           // log.info("element size : " + document.getAllElements().size());
            List<String> textMatches = document.getAllElements().stream().
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
