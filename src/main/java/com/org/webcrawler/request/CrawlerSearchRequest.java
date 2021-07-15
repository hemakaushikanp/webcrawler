package com.org.webcrawler.request;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CrawlerSearchRequest {
    private String searchText;
    private ArrayList<String> ingestURLs;
    private int level;
}
