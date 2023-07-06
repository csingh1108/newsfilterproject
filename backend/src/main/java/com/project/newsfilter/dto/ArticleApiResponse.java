package com.project.newsfilter.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ArticleApiResponse {
    private Object source; // Object type to capture the nested source JSON object
    private String author;
    private String title;
    private String urlToImage;
    private String content;
    private String description;
    private String url;
    private String publishedAt;
    // Other fields as needed

    // Custom getter for source name to handle null values
    public String getSourceName() {
        if (source instanceof Map<?,?>) {
            Map<String, Object> sourceMap = (Map<String, Object>) source;
            return (String) sourceMap.get("name");
        }
        return null;
    }
}

