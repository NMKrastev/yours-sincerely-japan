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
                            + "?apikey={apikey}&country={country}&language={language}&image={image}";
                            //Choose from which domains to receive news.
                            // + "&domain={domain}" OR + "&domainurl={domainurl}";

                            //Search the news articles for a specific timeframe (Minutes and Hours).
                            //For hours, you can set a timeframe of 1 to 48, and for minutes, you can define a timeframe of 1m to 2880m.
                            //For example, if you want to get the news for the past 6 hours then use timeframe=6
                            //and if you want to get news for the last 15 min then use timeframe=15m.
                            //Note - You can only use timeframe either in hours or minutes.
                            //For Hours
                            // + "&timeframe={timeframe}";
                            //For Minutes
                            // + "timeframe={timeframe}m";

            final Map<String, String> requestParams = Map.of(
                    "apikey", this.newsDataConfiguration.getApikey(),
                    "country", this.newsDataConfiguration.getCountry(),
                    "language", this.newsDataConfiguration.getLanguage(),
                    "image", this.newsDataConfiguration.getImage()
                    //"timeframe", this.newsDataConfiguration.getTimeframe()
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
