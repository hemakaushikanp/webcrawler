package com.org.webcrawler.service;

import com.org.webcrawler.request.CrawlerSearchRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface WebCrawlerService {
 HashMap<String, List<String>> performCrawling(CrawlerSearchRequest crawlerSearchRequest) throws IOException;
}
