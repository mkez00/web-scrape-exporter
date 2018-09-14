package com.mkezz.webscrapeexporter.model;

import java.util.ArrayList;
import java.util.List;

public class Metric {
    private String metricName;
    private List<MetricLabel> metricLabels = new ArrayList<>();
    private String metricValue;

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public List<MetricLabel> getMetricLabels() {
        return metricLabels;
    }

    public void setMetricLabels(List<MetricLabel> metricLabels) {
        this.metricLabels = metricLabels;
    }

    public String getMetricValue() {
        return metricValue;
    }

    public void setMetricValue(String metricValue) {
        this.metricValue = metricValue;
    }

    @Override
    public String toString() {
        String returnString = metricName;
        if (metricLabels !=null && !metricLabels.isEmpty()) {
            returnString += "{";
            for (MetricLabel metricLabel : metricLabels){
                returnString += metricLabel.toString() + ",";
            }
            //remove trailing ,
            returnString = returnString.substring(0, returnString.length()-1);
            returnString += "}";
        }
        returnString+= " " + metricValue;
        return returnString;
    }
}
