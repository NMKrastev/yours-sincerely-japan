package com.yourssincerelyjapan.web;

import com.yourssincerelyjapan.model.dto.NewsDataDTO;
import com.yourssincerelyjapan.service.NewsDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/japan")
public class NewsController {

    private final NewsDataService newsDataService;

    public NewsController(NewsDataService newsDataService) {
        this.newsDataService = newsDataService;
    }

    @GetMapping("/api/news")
    public ResponseEntity<List<NewsDataDTO>> getLatestNews() {
        return ResponseEntity.ok(this.newsDataService.getLatestNews());
    }

    @GetMapping("/news")
    public ModelAndView news(ModelAndView modelAndView) {

        modelAndView.setViewName("news");

        return modelAndView;
    }
}
