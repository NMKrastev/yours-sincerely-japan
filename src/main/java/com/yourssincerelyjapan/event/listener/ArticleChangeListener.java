package com.yourssincerelyjapan.event.listener;

import com.yourssincerelyjapan.event.OnArticleChangeEvent;
import com.yourssincerelyjapan.service.CategoryService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ArticleChangeListener {

    private final CategoryService categoryService;

    public ArticleChangeListener(CategoryService categoryService) {

        this.categoryService = categoryService;
    }

    @EventListener
    public void handleArticleChange(OnArticleChangeEvent event) {

        this.categoryService.checkForCategoriesWithoutArticles();
    }
}
