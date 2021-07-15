package com.org.webcrawler.service;

import com.org.webcrawler.request.CrawlerSearchRequest;

import java.io.IOException;

public interface WebCrawlerService {
 String performCrawling(CrawlerSearchRequest crawlerSearchRequest) throws IOException;
}
