package com.yourssincerelyjapan.service.impl;

import com.yourssincerelyjapan.config.NewsDataConfiguration;
import com.yourssincerelyjapan.model.dto.FetchNewsDataDTO;
import com.yourssincerelyjapan.model.dto.FetchNewsDataWrapperDTO;
import com.yourssincerelyjapan.model.dto.NewsDataDTO;
import com.yourssincerelyjapan.model.entity.NewsData;
import com.yourssincerelyjapan.model.mapper.NewsDataMapper;
import com.yourssincerelyjapan.repository.NewsDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class NewsDataServiceImplTest {


    @Mock
    private NewsDataRepository newsDataRepository;

    @Mock
    private NewsDataConfiguration newsDataConfiguration;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private NewsDataMapper newsDataMapper;

    @Mock
    private NewsDataServiceImpl newsDataService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        newsDataService =
                new NewsDataServiceImpl(newsDataRepository,
                        newsDataConfiguration,
                        restTemplate,
                        newsDataMapper);
    }

    @Test
    void fetchNews() {

        when(newsDataConfiguration.isEnabled()).thenReturn(true);
        when(newsDataConfiguration.getProtocol()).thenReturn("https://");
        when(newsDataConfiguration.getHost()).thenReturn("example.com");
        when(newsDataConfiguration.getPath()).thenReturn("/news");
        when(newsDataConfiguration.getApikey()).thenReturn("api-key");
        when(newsDataConfiguration.getCountry()).thenReturn("us");
        when(newsDataConfiguration.getLanguage()).thenReturn("en");
        when(restTemplate.getForObject(any(String.class), eq(FetchNewsDataWrapperDTO.class), any(Map.class)))
                .thenReturn(new FetchNewsDataWrapperDTO());
        when(newsDataMapper.newsDataDtoToNewsData(any(FetchNewsDataDTO.class))).thenReturn(new NewsData());
        when(newsDataMapper.newsDataToNewsDataDto(any(NewsData.class))).thenReturn(new NewsDataDTO());
        when(newsDataRepository.findNewsByFetchArticleId(anyString())).thenReturn(any());
        when(newsDataRepository.saveAll(List.of())).thenReturn(List.of());

        newsDataService.fetchNews();

        verify(restTemplate, times(1))
                .getForObject(any(String.class), eq(FetchNewsDataWrapperDTO.class), any(Map.class));

    }


    @Test
    void getLatestNews() {

        when(newsDataRepository.findAllByOrderByIdDesc()).thenReturn(List.of(new NewsData()));

        // Mock newsDataMapper newsDataToNewsDataDto method
        when(newsDataMapper.newsDataToNewsDataDto(any(NewsData.class))).thenReturn(new NewsDataDTO());

        // Test getLatestNews method
        List<NewsDataDTO> result = newsDataService.getLatestNews();

        // Assertions
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }


}