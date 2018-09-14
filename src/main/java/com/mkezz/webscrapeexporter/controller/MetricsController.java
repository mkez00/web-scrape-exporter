package com.mkezz.webscrapeexporter.controller;

import com.mkezz.webscrapeexporter.service.ProcessMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MetricsController {

    @Autowired
    ProcessMetricsService processMetricsService;

    @RequestMapping(value = "/metrics", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
    public String metrics() throws Exception{
        return processMetricsService.process();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
    public String index() throws Exception {
        return metrics();
    }

}
