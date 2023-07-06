package com.project.newsfilter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Settings {

    private String settingId;
    private String userId ;
    private boolean topNews = true;
    private boolean usNews = true;
    private boolean worldNews = true;
    private boolean businessNews = true;
    private boolean techNews = true;
    private boolean scienceNews = true;
    private boolean artsNews = true;
    private boolean sportsNews = true;
    private boolean healthNews = true;
}
