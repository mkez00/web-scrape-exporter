package com.mkezz.webscrapeexporter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkezz.webscrapeexporter.enumerator.MetricType;
import com.mkezz.webscrapeexporter.model.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class ProcessMetricsService {

    protected final Log LOG = LogFactory.getLog(this.getClass());

    @Value("${config.file}")
    String configFileLocation;

    public Config[] parseConfig() throws IOException{
        LOG.info("parseConfig");
        byte[] jsonData = Files.readAllBytes(Paths.get(configFileLocation));
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        return mapper.readValue(jsonData, Config[].class);
    }

    public String process() throws IOException{
        LOG.info("process");
        Config[] config = parseConfig();
        String finalString = "";
        for (int x = 0; x < config.length; x++){
            try {
                updateScrapes(config[x]);
                finalString += buildMetricWrapper(config[x]).toString() + "\n";
            } catch (IOException e) {
                LOG.warn("Error processing scrape", e);
            }
        }
        return finalString;
    }

    public MetricWrapper buildMetricWrapper(Config config){
        LOG.info("buildMetricWrapper");
        MetricWrapper metricWrapper = new MetricWrapper();
        Map<String,MetricSummary> metricSummaryMap = new HashMap<>();
        if (config!=null && config.getConfigScrapes()!=null){
            for (ConfigScrape configScrape : config.getConfigScrapes()){
                if (configScrape.getExtracts()!=null){
                    for (ConfigExtract configExtract : configScrape.getExtracts()){
                        if (!metricSummaryMap.containsKey(configExtract.getMetricName())){
                            metricSummaryMap.put(configExtract.getMetricName(), buildMetricSummary(configExtract));
                        }
                        metricSummaryMap.get(configExtract.getMetricName()).getMetrics().add(buildMetric(config, configScrape, configExtract));
                    }
                }
            }
        }
        metricWrapper.setMetricSummaries(new ArrayList<>(metricSummaryMap.values()));
        return metricWrapper;
    }

    private MetricSummary buildMetricSummary(ConfigExtract configExtract){
        MetricSummary metricSummary = new MetricSummary();
        try {
            metricSummary.setMetricType(MetricType.valueOf(configExtract.getMetricType()));
        } catch (IllegalArgumentException e) {
            metricSummary.setMetricType(null);
        }
        metricSummary.setHelpText(configExtract.getMetricHelp());
        metricSummary.setMetricName(configExtract.getMetricName());
        return metricSummary;
    }

    public void updateScrapes(Config config) throws IOException {
        LOG.info("updateScrapes");
        if (config!=null && config.getConfigScrapes()!=null){
            for (int x = 0; x < config.getConfigScrapes().size(); x++){
                ConfigScrape configScrape = config.getConfigScrapes().get(x);
                Connection connection = Jsoup.connect(configScrape.getUrl());
                if (configScrape.getTimeout()!=null) connection.timeout(configScrape.getTimeout());
                Document doc;
                if (configScrape.getUserAgent()!=null && !configScrape.getUserAgent().isEmpty()){
                    doc = connection.userAgent(configScrape.getUserAgent()).get();
                } else {
                    doc = connection.get();
                }

                if (configScrape.getExtracts()!=null){
                    for (int y = 0; y < configScrape.getExtracts().size(); y++){
                        ConfigExtract configExtract = configScrape.getExtracts().get(y);
                        Element element = doc.select(configExtract.getSelector()).first();
                        if (element != null) {
                            String finalString;

                            String elementText;
                            if (configExtract.isInnerHtml()){
                                elementText = element.html();
                            } else {
                                elementText = element.toString();
                            }

                            if (configExtract.isInteger()){
                                finalString = getInteger(elementText);
                            } else {
                                finalString = getFloat(elementText);
                            }
                            LOG.info("Final string: " + finalString + " for config metric: " + configExtract.getMetricName() + ", instance: " + configScrape.getInstance());
                            configExtract.setMetricValue(finalString);
                        } else {
                            LOG.warn("Could not scrape with given selector: " + configExtract.getSelector() + " for config metric: " + configExtract.getMetricName() + ", instance: " + configScrape.getInstance());
                            configExtract.setMetricValue("NaN");
                        }
                    }
                }
            }
        }
    }

    public String getInteger(String in){
        Pattern p = Pattern.compile("-?\\d+");
        Matcher m = p.matcher(in);
        while (m.find()) {
            return m.group();
        }
        return "";
    }

    public String getFloat(String in){
        Pattern p = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?");
        Matcher m = p.matcher(in);
        while (m.find()) {
            return m.group();
        }
        return "";
    }

    public Metric buildMetric(Config config, ConfigScrape configScrape, ConfigExtract configExtract){
        Metric metric = new Metric();
        metric.setMetricName(configExtract.getMetricName());
        metric.setMetricValue(configExtract.getMetricValue());

        metric.getMetricLabels().add(new MetricLabel("job", config.getJobName()));
        metric.getMetricLabels().add(new MetricLabel("instance", configScrape.getInstance()));

        LOG.info(metric.toString());
        return metric;
    }

}

