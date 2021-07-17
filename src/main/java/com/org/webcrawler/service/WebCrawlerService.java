package com.org.webcrawler.service;

import com.org.webcrawler.request.CrawlerSearchRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface WebCrawlerService {
 HashMap<String, List<String>> performCrawling(CrawlerSearchRequest crawlerSearchRequest) throws IOException;
 List<HashMap<String, List<String>>> performCrawlingForURL(CrawlerSearchRequest crawlerSearchRequest) throws IOException, ExecutionException, InterruptedException;
}
