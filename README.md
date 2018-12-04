Web Scrape Prometheus Exporter
-

[![DepShield Badge](https://depshield.sonatype.org/badges/mkez00/web-scrape-exporter/depshield.svg)](https://depshield.github.io)

A Prometheus Exporter for web scrapes.  This service uses an HTML parser (Jsoup) and scrapes metrics from the HTML returned from a specified URL.  These metrics will then be formatted and served for Prometheus scraping.

Example
-

The default configuration will serve metrics scraped from a static site hosted by me on S3.  The metrics will look similar to the following:

```
# HELP cost Cost of item
# TYPE cost gauge
cost{job="web_scrape",instance="metric"} 32.3
cost{job="web_scrape",instance="metric2"} 60.6
# HELP purchased Number of units purchased
# TYPE purchased counter
purchased{job="web_scrape",instance="metric"} 55
purchased{job="web_scrape",instance="metric2"} 88
# HELP temperature Temperature metric
# TYPE temperature gauge
temperature{job="web_scrape",instance="metric"} -111.35
temperature{job="web_scrape",instance="metric2"} -122.35
```

After configuring Prometheus to scrape metrics from this exporter:

![alt text](https://github.com/mkez00/web-scrape-exporter/blob/master/data/prom-dash2.png)

Docker
-

Running with Docker:

1. Download the Docker image: `docker pull mkez00/web-scrape-exporter`
2. Create a configuration file for scraping.  See Configuration below on attribute definitions or use `/data/scrape-config.yaml` as a guideline
3. Override the default configuration with your custom configuration and create the container: `docker run --mount type=bind,source="$(pwd)"/custom-scrape.yaml,target=/var/scrape-config.yaml -p 8089:8080 mkez00/web-scrape-exporter`
4. Configure Prometheus to gather metrics from this exporter

Build with Docker using the following after cloning the repository:

1. From project root `mvn clean package`
2. Build Docker image `docker build . -t mkez00/web-scrape-exporter`
3. Push Docker image to Docker Hub `docker push mkez00/web-scrape-exporter`

Configuration
-

A sample scrape config file is provided in `/data/scrape-config.yaml`.  Use that as a baseline for creating jobs.  The config file definition is defined below:

`<job_config>` 

The top level configuration of a job and all of its corresponding scrapes.  The configuration file can accept a list of `<job_config>` elements.

```
# The job name assigned to scraped metrics by default.  This is a required field
job: <string>

# List of scrapes that the job needs to complete
scrapes: 
  [ - <scrape_config> ... ]
```

`<scrape_config>`

Defines a specific URL along with all of the metrics that will be pulled from the returned payload.

```
# The url to be scraped.  This is a required field
url: <string>

# The instance label that is applied to each metric is defined here.  This is a required field
instance: <string>

# The Jsoup connection timeout when waiting for the HTTP request to the defined URL
timeout: <integer>

# Specify a user agent when executing HTTP request (ie. Mozilla)
user_agent: <string>

# List of extracts that are to be parsed from the returned payload.  This is a required element
extracts: 
  [ - <extract_config> ... ]
```

`<extract_config>`

Defines one or more sections of the HTML to be parsed and defined as individual metrics

```
# This attribute required selector syntax defined in Jsoup's Selector Documentation.  Once the element has been selected, the process extracts any integer or float that is part of the parsed HTML.  This is a required field
selector: <string>

# Parameter which defines how metrics are extracted from the parsed HTML.  Default is `false` which means the parser will match metrics that are floats
integer: <boolean>

# When the selector is executed, the value to be put through the regex filter (integer or float) should be the inner html of the tag and not the tag as a whole.  For example, when inner_html is false a selector that returns <div class="4_hd_div">23</div> will process the entire div for a metric which will return 4.  If inner_html is true the selector will process 23 instead.  Default value is false
inner_html: <boolean>

# The name of the Prometheus metric. This is a required field
metric_name: <string>

# The help descriptor for Prometheus
metric_help: <string>

# The Prometheus specific metric type.  Can be one of: counter, gauge, histogram, summary.  If none provided default is untyped
metric_type: <metric_type>
```

Note: <a href="https://jsoup.org/cookbook/extracting-data/selector-syntax">Jsoup's Selector Documentation</a>

