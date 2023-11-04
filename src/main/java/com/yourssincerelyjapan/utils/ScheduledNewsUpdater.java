package com.yourssincerelyjapan.utils;

import com.yourssincerelyjapan.service.impl.NewsDataServiceImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@EnableScheduling
public class ScheduledNewsUpdater {

    private final NewsDataServiceImpl newsDataService;

    public ScheduledNewsUpdater(NewsDataServiceImpl newsDataService) {

        this.newsDataService = newsDataService;
    }

    @Scheduled(fixedRate = 15, timeUnit = TimeUnit.MINUTES)
    public void fetchAndSaveNews() {
        //this.newsDataService.fetchNews();
    }
}
