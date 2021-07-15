package com.org.webcrawler.service;

import com.org.webcrawler.request.CrawlerSearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Slf4j
@Service
public class WebCrawlerServiceImpl implements  WebCrawlerService{

    @Override
    public HashMap<String, List<String>> performCrawling(CrawlerSearchRequest crawlerSearchRequest)  throws IOException{
        log.info("SERVICE:crawlerSearchRequest:" + crawlerSearchRequest.getIngestURLs().size());
        HashMap<String, List<String>> resultMap = new HashMap<String, List<String>>();
       //Document doc = Jsoup.connect("https://en.wikipedia.org/").get();
//        String title = doc.title();
//        System.out.println("title is: " + title);
//
//        Elements links = doc.select("a[href]");
//
//        links.forEach(link -> {
//            System.out.println("\nlink : " + link.attr("href"));
//            System.out.println("text : " + link.text());
//        });

       // Document doc = Jsoup.parse(new File("https://en.wikipedia.org/"),"utf-8");
       // Element loginform = doc.getElementById("registerform");


        crawlerSearchRequest.getIngestURLs().forEach( url -> {
            try {
                List<String> textMatches = new ArrayList<String>();
                Document document = Jsoup.connect(url).get();
                //use streams
                System.out.println("element size : " +  document.getAllElements().size());
                document.getAllElements().forEach(element -> {
                    System.out.println("text : " +  element.text());
                    if(element.text().toLowerCase().contains(crawlerSearchRequest.getSearchText().toLowerCase())){
                        System.out.println("id : " + element.id() + "Text :" + element.text());
                        textMatches.add(element.nodeName() +"-" + element.text());
                    }
                });
                resultMap.put(url,textMatches);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });




        return resultMap;
    }
}
