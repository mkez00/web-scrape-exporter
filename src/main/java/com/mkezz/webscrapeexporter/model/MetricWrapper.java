package com.mkezz.webscrapeexporter.model;

import java.util.ArrayList;
import java.util.List;

public class MetricWrapper {
    private List<MetricSummary> metricSummaries = new ArrayList<>();

    public List<MetricSummary> getMetricSummaries() {
        return metricSummaries;
    }

    public void setMetricSummaries(List<MetricSummary> metricSummaries) {
        this.metricSummaries = metricSummaries;
    }

    @Override
    public String toString() {
        String returnString = "";
        if (metricSummaries!=null && !metricSummaries.isEmpty()){
            for (MetricSummary metricSummary : metricSummaries){
                returnString += metricSummary.toString() + "\n";
            }
            returnString = returnString.substring(0, returnString.length()-1);
        }
        return returnString;
    }
}
