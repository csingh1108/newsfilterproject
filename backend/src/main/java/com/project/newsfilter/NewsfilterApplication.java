package com.project.newsfilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.project.newsfilter")
public class NewsfilterApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsfilterApplication.class, args);
	}

}
