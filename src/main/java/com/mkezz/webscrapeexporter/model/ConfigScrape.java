package com.mkezz.webscrapeexporter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ConfigScrape {

    private String url;
    private String instance;
    private Integer timeout;
    @JsonProperty("user_agent")
    private String userAgent;
    private List<ConfigExtract> extracts = new ArrayList<>();



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ConfigExtract> getExtracts() {
        return extracts;
    }

    public void setExtracts(List<ConfigExtract> extracts) {
        this.extracts = extracts;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
