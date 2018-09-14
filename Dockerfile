FROM openjdk:8-jdk-alpine

ADD data/scrape-config.yaml /var/scrape-config.yaml
ADD target/web-scrape-exporter-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080

ENV CONFIG_FILE="/var/scrape-config.yaml"

ENTRYPOINT exec java -Dconfig.file=$CONFIG_FILE -Djava.security.egd=file:/dev/./urandom -jar /app.jar