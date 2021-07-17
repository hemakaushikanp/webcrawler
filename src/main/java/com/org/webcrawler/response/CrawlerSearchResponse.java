package com.org.webcrawler.response;

import lombok.*;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CrawlerSearchResponse {
    private List<HashMap<String, List<String>>> searchResults;
}
