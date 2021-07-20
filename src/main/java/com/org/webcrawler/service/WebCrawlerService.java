package com.org.webcrawler.service;

import com.org.webcrawler.request.CrawlerSearchRequest;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface WebCrawlerService {
 List<Map<String, List<String>>> performCrawlingForURL(CrawlerSearchRequest crawlerSearchRequest) throws IOException, ExecutionException, InterruptedException;
}
