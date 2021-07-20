package com.org.webcrawler.response;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CrawlerSearchResponse {
    private List<Map<String, List<String>>> searchResults;
}
