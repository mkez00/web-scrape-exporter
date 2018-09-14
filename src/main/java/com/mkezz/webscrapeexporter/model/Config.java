package com.mkezz.webscrapeexporter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Config {
    @JsonProperty("job")
    private String jobName;
    @JsonProperty("scrapes")
    private List<ConfigScrape> configScrapes = new ArrayList<>();

    public List<ConfigScrape> getConfigScrapes() {
        return configScrapes;
    }

    public void setConfigScrapes(List<ConfigScrape> configScrapes) {
        this.configScrapes = configScrapes;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
}
