package com.yourssincerelyjapan.init;

import com.yourssincerelyjapan.repository.ArticlePictureRepository;
import com.yourssincerelyjapan.repository.ArticleRepository;
import com.yourssincerelyjapan.repository.NewsDataRepository;
import com.yourssincerelyjapan.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.yourssincerelyjapan.constant.AppConstants.DATABASE_INITIALIZATION_COMPLETED;
import static com.yourssincerelyjapan.constant.SQLScripts.*;

@Component
public class ApplicationInit implements CommandLineRunner {

    private final UserService userService;
    private final JdbcTemplate jdbcTemplate;
    private final ArticleRepository articleRepository;
    private final ArticlePictureRepository pictureRepository;
    private final NewsDataRepository newsDataRepository;

    public ApplicationInit(UserService userService, JdbcTemplate jdbcTemplate,
                           ArticleRepository articleRepository, ArticlePictureRepository pictureRepository,
                           NewsDataRepository newsDataRepository) {
        this.userService = userService;
        this.jdbcTemplate = jdbcTemplate;
        this.articleRepository = articleRepository;
        this.pictureRepository = pictureRepository;
        this.newsDataRepository = newsDataRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        this.userService.administratorInit();

        /*if (this.articleRepository.count() == 0) {
            this.initializeArticles();
        }

        if (this.pictureRepository.count() == 0) {
            this.initializeArticlesPictures();
        }

        if (this.newsDataRepository.count() == 0) {
            this.initializeNews();
        }*/
    }

    private void initializeNews() {
        executeScript(NEWS);
    }

    private void initializeArticlesPictures() {
        executeScript(ARTICLES_PICTURES);
    }

    private void initializeArticles() {
        executeScript(ARTICLES);
    }

    private void executeScript(String script) {

        try {

            final ClassPathResource resource = new ClassPathResource(script);

            byte[] scriptBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());

            final String scriptContent = new String(scriptBytes, StandardCharsets.UTF_8);

            final String[] statements = scriptContent.split(";");

            for (String statement : statements) {
                this.jdbcTemplate.execute(statement);
            }

            System.out.println(DATABASE_INITIALIZATION_COMPLETED);

        } catch (IOException e) {

            System.out.println(e.getMessage());

        }
    }
}
