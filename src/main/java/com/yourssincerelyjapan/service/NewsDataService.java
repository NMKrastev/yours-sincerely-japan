package com.yourssincerelyjapan.service;

import com.yourssincerelyjapan.config.NewsDataConfiguration;
import com.yourssincerelyjapan.model.dto.FetchNewsDataDTO;
import com.yourssincerelyjapan.model.dto.FetchNewsDataWrapperDTO;
import com.yourssincerelyjapan.model.dto.NewsDataDTO;
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
                            //Not every news site provides pictures, so by observing,
                            //you can choose the ones that provide images by their domain or domainUrl
                            // + "domain={domain}" OR + "&domainurl={domainurl}";

            final Map<String, String> requestParams = Map.of(
                    "apikey", this.newsDataConfiguration.getApikey(),
                    "country", this.newsDataConfiguration.getCountry(),
                    "language", this.newsDataConfiguration.getLanguage()
            );

            final FetchNewsDataWrapperDTO newsDataWrapperDTO = this.restTemplate
                    .getForObject(newsDataUrlTemplate,
                            FetchNewsDataWrapperDTO.class,
                            requestParams);

            this.saveNewsData(newsDataWrapperDTO.getResults());
        }
    }

    public List<NewsDataDTO> getLatestNews() {

        return this.newsDataRepository.findAllByOrderByIdDesc()
                .stream()
                .limit(10)
                .map(this.newsDataMapper::newsDataToNewsDataDto)
                .toList();
    }


    private void saveNewsData(List<FetchNewsDataDTO> newsDataDTOS) {

        final List<FetchNewsDataDTO> noDuplicatesNewsDataDTOS =
                this.checkForDuplicates(newsDataDTOS);

        if (!noDuplicatesNewsDataDTOS.isEmpty()) {

            noDuplicatesNewsDataDTOS
                    .forEach(n -> n.setDescription(n.getDescription().replace("…", "")));

            final List<NewsData> newsData = noDuplicatesNewsDataDTOS
                    .stream()
                    .map(this.newsDataMapper::newsDataDtoToNewsData)
                    .toList();

            this.newsDataRepository.saveAll(newsData);
        }
    }

    private List<FetchNewsDataDTO> checkForDuplicates(List<FetchNewsDataDTO> fetchNewsDataDTO) {

        return fetchNewsDataDTO
                .stream()
                .filter(e ->
                        this.newsDataRepository.findNewsByFetchArticleId(e.getArticle_id()).isEmpty())
                .toList();
    }
}
