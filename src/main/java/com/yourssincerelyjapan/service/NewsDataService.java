package com.yourssincerelyjapan.service;

import com.yourssincerelyjapan.config.NewsDataConfiguration;
import com.yourssincerelyjapan.model.dto.NewsDataDTO;
import com.yourssincerelyjapan.model.dto.NewsDataWrapperDTO;
import com.yourssincerelyjapan.model.entity.NewsData;
import com.yourssincerelyjapan.model.mapper.NewsDataMapper;
import com.yourssincerelyjapan.repository.NewsDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class NewsDataService {

    private final NewsDataRepository newsDataRepository;
    private final NewsDataConfiguration newsDataConfiguration;
    private final RestTemplate restTemplate;
    private final NewsDataMapper newsDataMapper;

    public NewsDataService(NewsDataRepository newsDataRepository, NewsDataConfiguration newsDataConfiguration,
                           RestTemplate restTemplate, NewsDataMapper newsDataMapper) {

        this.newsDataRepository = newsDataRepository;
        this.newsDataConfiguration = newsDataConfiguration;
        this.restTemplate = restTemplate;
        this.newsDataMapper = newsDataMapper;
    }

    public void fetchAndSaveNews() {

        if (this.newsDataConfiguration.isEnabled()) {
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

            this.saveNewsData(newsDataWrapperDTO.getResults());
        }
    }

    private void saveNewsData(List<NewsDataDTO> newsDataDTOS) {

        final List<NewsData> newsData = newsDataDTOS
                .stream()
                .map(this.newsDataMapper::newsDataDtoToNewsData)
                .toList();

        this.newsDataRepository.saveAll(newsData);
    }
}
