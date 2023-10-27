package com.yourssincerelyjapan.init;

import com.yourssincerelyjapan.config.NewsDataConfiguration;
import com.yourssincerelyjapan.model.dto.NewsDataWrapperDTO;
import com.yourssincerelyjapan.service.NewsDataService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class NewsDataInit implements CommandLineRunner {

    /*private final NewsDataConfiguration newsDataConfiguration;
    private final RestTemplate restTemplate;
    private final NewsDataService newsDataService;

    public NewsDataInit(NewsDataConfiguration newsDataConfiguration, RestTemplate restTemplate,
                        NewsDataService newsDataService) {

        this.newsDataConfiguration = newsDataConfiguration;
        this.restTemplate = restTemplate;
        this.newsDataService = newsDataService;
    }*/

    @Override
    public void run(String... args) throws Exception {

        /*if (this.newsDataConfiguration.isEnabled()) {
            final String newsDataUrlTemplate =
                    this.newsDataConfiguration.getSchema()
                    + "://"
                    + this.newsDataConfiguration.getHost()
                    + this.newsDataConfiguration.getPath()
                    + "?apikey={apikey}&country={country}&language={language}";

            final Map<String, String> requestParams = Map.of(
                    "apikey", this.newsDataConfiguration.getApikey(),
                    "country", this.newsDataConfiguration.getCountry(),
                    "language", this.newsDataConfiguration.getLanguage()
            );

            final NewsDataWrapperDTO newsDataWrapperDTO = this.restTemplate
                    .getForObject(newsDataUrlTemplate,
                            NewsDataWrapperDTO.class,
                            requestParams);

            this.newsDataService.getNewsData(newsDataWrapperDTO);
        }*/

        //this.newsDataService.getNewsData();
    }
}
