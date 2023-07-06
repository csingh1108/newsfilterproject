package com.project.newsfilter.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.newsfilter.dto.ArticleApiResponse;
import com.project.newsfilter.dto.NewsDTO;
import lombok.RequiredArgsConstructor;
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

    private String sourceName = "";
    private String searchName = "";
    private String oldestDate= "";
    private String newestDate = "";
    private String sortBy = "";

    public List<NewsDTO> getAllNews(String sourceName) {
        String url = "https://newsapi.org/v2/top-headlines?country=us&category=" + sourceName + "&pageSize=35&apiKey=" + apiKey;

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
            for (ArticleApiResponse article : articleList) {
                NewsDTO newsDTO = new NewsDTO();
                newsDTO.setName(article.getSourceName());
                //Remove name of org from title
                newsDTO.setTitle(formatTitle(article.getTitle()));
                newsDTO.setDescription(article.getDescription());
                newsDTO.setUrl(article.getUrl());
                newsDTO.setUrlToImage(article.getUrlToImage());
                //Remove trailing number with chars from content
                if (article.getContent() != null) {
                    newsDTO.setContent(formatContent(article.getContent()));
                }
                //reformat published date to mm-dd-yyyy
                newsDTO.setPublishedAt(formatDate(article.getPublishedAt()));

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
        if (index != -1) {
            title = title.substring(0, index);
        }
        return title;
    }

    private String formatContent(String content) {
        int index = content.lastIndexOf("[");
        if (index != -1) {
            return content.substring(0, index);
        } else {
            return content;
        }
    }

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
                    NewsDTO newsDTO = new NewsDTO();
                    newsDTO.setName(article.getSourceName());
                    //Remove name of org from title
                    newsDTO.setTitle(formatTitle(article.getTitle()));
                    newsDTO.setDescription(article.getDescription());
                    newsDTO.setUrl(article.getUrl());
                    newsDTO.setUrlToImage(article.getUrlToImage());
                    //Remove trailing number with chars from content
                    if (article.getContent() != null) {
                        newsDTO.setContent(formatContent(article.getContent()));
                    }
                    //reformat published date to mm-dd-yyyy
                    newsDTO.setPublishedAt(formatDate(article.getPublishedAt()));

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