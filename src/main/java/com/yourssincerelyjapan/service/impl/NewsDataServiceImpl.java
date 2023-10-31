package com.yourssincerelyjapan.service.impl;

import com.yourssincerelyjapan.config.NewsDataConfiguration;
import com.yourssincerelyjapan.model.dto.FetchNewsDataDTO;
import com.yourssincerelyjapan.model.dto.FetchNewsDataWrapperDTO;
import com.yourssincerelyjapan.model.dto.NewsDataDTO;
import com.yourssincerelyjapan.model.entity.NewsData;
import com.yourssincerelyjapan.model.mapper.NewsDataMapper;
import com.yourssincerelyjapan.repository.NewsDataRepository;
import com.yourssincerelyjapan.service.NewsDataService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static com.yourssincerelyjapan.constant.NewsDataConstant.*;

@Service
public class NewsDataServiceImpl implements NewsDataService {

    private final NewsDataRepository newsDataRepository;
    private final NewsDataConfiguration newsDataConfiguration;
    private final RestTemplate restTemplate;
    private final NewsDataMapper newsDataMapper;

    public NewsDataServiceImpl(NewsDataRepository newsDataRepository, NewsDataConfiguration newsDataConfiguration,
                               RestTemplate restTemplate, NewsDataMapper newsDataMapper) {

        this.newsDataRepository = newsDataRepository;
        this.newsDataConfiguration = newsDataConfiguration;
        this.restTemplate = restTemplate;
        this.newsDataMapper = newsDataMapper;
    }

    @Override
    public void fetchAndSaveNews() {

        if (this.newsDataConfiguration.isEnabled()) {

            final StringBuilder newsDataUrlTemplate = new StringBuilder();

                    newsDataUrlTemplate.append(this.newsDataConfiguration.getProtocol())
                            .append(this.newsDataConfiguration.getHost())
                            .append(this.newsDataConfiguration.getPath())
                            .append(API_KEY)
                            .append(COUNTRY_PARAM)
                            .append(LANGUAGE_PARAM)
                            .append(IMAGE_PARAM);

            final Map<String, String> requestParams = Map.of(
                    "apikey", this.newsDataConfiguration.getApikey(),
                    "country", this.newsDataConfiguration.getCountry(),
                    "language", this.newsDataConfiguration.getLanguage(),
                    "image", this.newsDataConfiguration.getImage(),
                    "timeframe", this.newsDataConfiguration.getTimeframe()
            );

            final FetchNewsDataWrapperDTO newsDataWrapperDTO = this.restTemplate
                    .getForObject(newsDataUrlTemplate.toString(),
                            FetchNewsDataWrapperDTO.class,
                            requestParams);

            this.saveNewsData(newsDataWrapperDTO.getResults());
        }
    }

    @Override
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
                        this.newsDataRepository
                                .findNewsByFetchArticleId(e.getArticle_id())
                                .isEmpty())
                .toList();
    }
}
