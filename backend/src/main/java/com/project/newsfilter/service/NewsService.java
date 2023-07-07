package com.project.newsfilter.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.newsfilter.dto.ArticleApiResponse;
import com.project.newsfilter.dto.NewsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {
    public static final String apiKey = "b807e33bd5e44a9183a139ac5d049190";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // Method calls newsapi.org API to get top headlines, data is returned as a JSON object and mapped to NewsDTO
    public List<NewsDTO> getAllNews(String sourceName) {
        String url = "https://newsapi.org/v2/top-headlines?country=us&category=" + sourceName + "&pageSize=35&apiKey=" + apiKey;

        String jsonResponse = restTemplate.getForObject(url, String.class);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            JsonNode root = objectMapper.readTree(jsonResponse);
            JsonNode articlesNode = root.get("articles");

            List<ArticleApiResponse> articleList = objectMapper.readValue(articlesNode.toString(), new TypeReference<>() {
            });

            List<NewsDTO> newsList = new ArrayList<>();
            for (ArticleApiResponse article : articleList) {
                NewsDTO newsDTO = createNewsDTO(article);
                if ((newsDTO.getContent() != null && (newsDTO.getUrlToImage() != null))) {
                    newsList.add(newsDTO);
                }
            }

            return newsList;

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return Collections.emptyList();
    }

    //Method takes in date and reformats it to mm-dd-yyyy
    private String formatDate(String date) {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(date);
        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
        String formattedPublishedDate = localDateTime.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        return formattedPublishedDate;
    }

    //Method strips out the name of the org from the title
    private String formatTitle(String title) {
        int index = title.lastIndexOf("-");
        return (index != -1) ? title.substring(0, index) : title;
    }

    //Method strips out the last part of the content
    private String formatContent(String content) {
        int index = content.lastIndexOf("[");
        return (index != -1) ? content.substring(0, index) : content;
    }

    //Maps ArticleApiResponse to NewsDTO
    private NewsDTO createNewsDTO(ArticleApiResponse article) {
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setName(article.getSourceName());
        newsDTO.setTitle(formatTitle(article.getTitle()));
        newsDTO.setDescription(article.getDescription());
        newsDTO.setUrl(article.getUrl());
        newsDTO.setUrlToImage(article.getUrlToImage());
        if (article.getContent() != null) {
            newsDTO.setContent(formatContent(article.getContent()));
        }
        newsDTO.setPublishedAt(formatDate(article.getPublishedAt()));
        return newsDTO;
    }

    //Service method to get news results based on search parameters
    public List<NewsDTO> getNewsResults(String searchName, String oldestDate, String newestDate, String sortBy) {
        String url = "https://newsapi.org/v2/everything?" +
                "q=" + searchName +
                "&language=en" +
                "&from=" + oldestDate +
                "&to=" + newestDate +
                "&sortBy=" + sortBy +
                "&pageSize=100&apiKey=" + apiKey;

        RestTemplate restTemplate = new RestTemplate();
        String jsonResponse = restTemplate.getForObject(url, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            JsonNode root = objectMapper.readTree(jsonResponse);
            JsonNode articlesNode = root.get("articles");
            List<ArticleApiResponse> articleList = objectMapper.readValue(articlesNode.toString(), new TypeReference<>() {
            });
            List<NewsDTO> newsList = new ArrayList<>();
            String previousTitle = "";
            String currentTitle;
            for (ArticleApiResponse article : articleList) {
                currentTitle = article.getTitle();
                if(!currentTitle.equals(previousTitle) && article.getContent() !=  null && article.getUrl() != null){
                    NewsDTO newsDTO = createNewsDTO(article);
                    newsList.add(newsDTO);
                    previousTitle = currentTitle;
                }
            }

            return newsList;

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return Collections.emptyList();
    }
}