package com.org.webcrawler.jsonmapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.webcrawler.request.CrawlerSearchRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyEditorSupport;

@Slf4j
@AllArgsConstructor
@Getter
@Setter
public class CrawlerMapper extends PropertyEditorSupport {
    private ObjectMapper objectMapper;

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.isEmpty(text)) {
            setValue(null);
        } else {
            CrawlerSearchRequest crawlerSearchRequest;
            try {
                crawlerSearchRequest = objectMapper.readValue(text, CrawlerSearchRequest.class);
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException(e);
            }
            setValue(crawlerSearchRequest);
        }
    }

}
