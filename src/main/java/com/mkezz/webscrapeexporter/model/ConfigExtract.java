package com.mkezz.webscrapeexporter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConfigExtract {
    private String selector;
    @JsonProperty("integer")
    private boolean isInteger = false;
    @JsonProperty("metric_name")
    private String metricName;
    @JsonProperty("metric_help")
    private String metricHelp;
    @JsonProperty("metric_type")
    private String metricType;
    @JsonProperty("inner_html")
    private boolean innerHtml = false;
    private String metricValue; //will be set after scrape

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public boolean isInteger() {
        return isInteger;
    }

    public void setInteger(boolean integer) {
        isInteger = integer;
    }

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public String getMetricHelp() {
        return metricHelp;
    }

    public void setMetricHelp(String metricHelp) {
        this.metricHelp = metricHelp;
    }

    public String getMetricType() {
        return metricType;
    }

    public void setMetricType(String metricType) {
        this.metricType = metricType;
    }

    public boolean isInnerHtml() {
        return innerHtml;
    }

    public void setInnerHtml(boolean innerHtml) {
        this.innerHtml = innerHtml;
    }

    public String getMetricValue() {
        return metricValue;
    }

    public void setMetricValue(String metricValue) {
        this.metricValue = metricValue;
    }
}
