package com.org.webcrawler.service;

import com.org.webcrawler.request.CrawlerSearchRequest;
import com.org.webcrawler.response.CrawlerSearchResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class WebcrawlerServiceTests {

    @Mock
    WebCrawlerService webCrawlerService= new WebCrawlerServiceImpl();

    @Test
    void testPerformCrawlerSearchInParallel() throws IOException, ExecutionException, InterruptedException {
        HashMap<String, List<String>> resultMap = new HashMap() {{
            put("https://www.google.com/", new ArrayList() {{
                add("web crawler searched for text - Test ");
            }});
        }};
        List<Map<String, List<String>>> searchResults = new ArrayList() {{
            add(resultMap);
        }};
        CrawlerSearchRequest crawlerSearchRequest = new CrawlerSearchRequest();
        crawlerSearchRequest.setSearchText("Search");
        crawlerSearchRequest.setIngestURLs(new ArrayList() {{
            add("https://www.google.com/");
        }});
        CrawlerSearchResponse crawlerSearchResponse = new CrawlerSearchResponse();
        crawlerSearchResponse.setSearchResults(searchResults);
        Mockito.when(webCrawlerService.performCrawlingForURL(crawlerSearchRequest)).thenReturn(searchResults);
        assertNotNull(webCrawlerService.performCrawlingForURL(crawlerSearchRequest).get(0));
    }
}
