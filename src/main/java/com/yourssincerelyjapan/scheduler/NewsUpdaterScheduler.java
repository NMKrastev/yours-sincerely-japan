package com.yourssincerelyjapan.scheduler;

import com.yourssincerelyjapan.service.impl.NewsDataServiceImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@EnableScheduling
public class NewsUpdaterScheduler {

    private final NewsDataServiceImpl newsDataService;

    public NewsUpdaterScheduler(NewsDataServiceImpl newsDataService) {

        this.newsDataService = newsDataService;
    }

    @Scheduled(fixedRate = 15, timeUnit = TimeUnit.MINUTES)
    public void fetchNews() {
        this.newsDataService.fetchNews();
    }
}
