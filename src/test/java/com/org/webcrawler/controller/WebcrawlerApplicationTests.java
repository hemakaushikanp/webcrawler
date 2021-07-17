package com.org.webcrawler.controller;

import com.org.webcrawler.request.CrawlerSearchRequest;
import com.org.webcrawler.service.WebCrawlerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class WebcrawlerApplicationTests {

    @Mock
    WebCrawlerService webCrawlerService;

    @InjectMocks
    WebcrawlerController webcrawlerController;

    @Test
    void testPerformCrawlerSearchInParallel() throws IOException, ExecutionException, InterruptedException {
        HashMap<String, List<String>> resultMap = new HashMap() {{
            put("https://www.google.com/", new ArrayList() {{
                add("web crawler searched for text - Test ");
            }});
        }};
        List<HashMap<String, List<String>>> searchResults = new ArrayList() {{
            add(resultMap);
        }};
        CrawlerSearchRequest crawlerSearchRequest = new CrawlerSearchRequest();
        crawlerSearchRequest.setSearchText("Search");
        crawlerSearchRequest.setIngestURLs(new ArrayList() {{
            add("https://www.google.com/");
        }});
        Mockito.when(webCrawlerService.performCrawlingForURL(crawlerSearchRequest)).thenReturn(searchResults);
        assertEquals(1, webcrawlerController.performCrawlerSearchInParallel(crawlerSearchRequest).getSearchResults().size());
    }

}
