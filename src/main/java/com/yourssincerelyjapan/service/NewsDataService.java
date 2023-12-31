package com.yourssincerelyjapan.service;

import com.yourssincerelyjapan.model.dto.NewsDataDTO;

import java.util.List;

public interface NewsDataService {

    void fetchNews();

    List<NewsDataDTO> getLatestNews();
}
