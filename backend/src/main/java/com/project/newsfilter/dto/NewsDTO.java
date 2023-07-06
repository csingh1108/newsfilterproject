package com.project.newsfilter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsDTO {

    // News source name (e.g. CNN, Fox News)
    private String name;
    // Title of article
    private String title;
    // Description of article
    private String description;
    // URL of article
    private String url;
    // URL of image
    private String urlToImage;
    //  Shortened article content
    private String content;
    // date published
    private String publishedAt;
}
