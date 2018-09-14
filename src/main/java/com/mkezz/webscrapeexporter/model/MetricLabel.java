package com.mkezz.webscrapeexporter.model;

public class MetricLabel {
    private String key;
    private String value;

    public MetricLabel(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return key + "=\"" + getValue() + "\"";
    }
}
