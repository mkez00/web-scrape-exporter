package com.mkezz.webscrapeexporter.model;

import com.mkezz.webscrapeexporter.enumerator.MetricType;

import java.util.ArrayList;
import java.util.List;

public class MetricSummary {

    private String metricName;
    private String helpText;
    private MetricType metricType;
    private List<Metric> metrics = new ArrayList<>();

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public String getHelpText() {
        return helpText;
    }

    public void setHelpText(String helpText) {
        this.helpText = helpText;
    }

    public MetricType getMetricType() {
        return metricType;
    }

    public void setMetricType(MetricType metricType) {
        this.metricType = metricType;
    }

    public List<Metric> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<Metric> metrics) {
        this.metrics = metrics;
    }

    @Override
    public String toString() {
        String returnString = "";
        if (helpText!=null && !helpText.isEmpty()){
            returnString+="# HELP " + metricName + " " + helpText + "\n";
        }
        returnString += "# TYPE " + metricName + " " + (metricType==null ? "untyped" : metricType.toString()) + "\n";
        if (metrics!=null && !metrics.isEmpty()){
            for (Metric metric : metrics){
                returnString+=metric.toString()+"\n";
            }
            returnString = returnString.substring(0, returnString.length()-1);
        }
        return returnString;
    }
}
