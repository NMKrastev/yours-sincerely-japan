package com.yourssincerelyjapan.service;

import com.yourssincerelyjapan.model.dto.NewsDataDTO;
import com.yourssincerelyjapan.model.dto.NewsDataWrapperDTO;
import com.yourssincerelyjapan.model.entity.NewsData;
import com.yourssincerelyjapan.model.mapper.NewsDataMapper;
import com.yourssincerelyjapan.repository.NewsDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsDataService {

    private final NewsDataRepository newsDataRepository;
    private final NewsDataMapper newsDataMapper;

    public NewsDataService(NewsDataRepository newsDataRepository, NewsDataMapper newsDataMapper) {
        this.newsDataRepository = newsDataRepository;
        this.newsDataMapper = newsDataMapper;
    }

    public void getNewsData(NewsDataWrapperDTO newsDataWrapperDTO) {

        final List<NewsDataDTO> results = newsDataWrapperDTO.getResults();

        List<NewsData> newsData = results
                .stream()
                .map(newsDataMapper::newsDataDtoToNewsData)
                .toList();

        this.newsDataRepository.saveAll(newsData);
    }
}
