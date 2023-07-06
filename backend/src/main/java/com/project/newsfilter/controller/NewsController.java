package com.project.newsfilter.controller;

import com.project.newsfilter.dto.NewsDTO;
import com.project.newsfilter.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping("/headlines")
    @ResponseStatus(HttpStatus.OK)
    public List<NewsDTO> getAllNews(@RequestParam(required = false) String sourceName){
        return newsService.getAllNews(sourceName);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<NewsDTO> getNewsResults(@RequestParam String searchName, @RequestParam(required = false) String oldestDate,
                                        @RequestParam(required = false) String newestDate, @RequestParam(required = false) String sortBy){
        return newsService.getNewsResults(searchName, oldestDate, newestDate, sortBy);
    }
}
