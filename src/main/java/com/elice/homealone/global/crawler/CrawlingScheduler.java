package com.elice.homealone.global.crawler;

import java.io.IOException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CrawlingScheduler {

    @Scheduled(cron= "0 0 0 * * SUN")
    public void runCrawling() {
        try {
            String projectDirectory = System.getProperty("user.dir");
            Runtime.getRuntime().exec("python" + projectDirectory + "/pythoncrawler/RecipeCrawler.py");
        } catch (IOException e) {
        }
    }
}
