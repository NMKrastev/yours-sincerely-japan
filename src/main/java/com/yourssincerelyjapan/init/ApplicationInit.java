package com.yourssincerelyjapan.init;

import com.yourssincerelyjapan.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.yourssincerelyjapan.constant.SQLScripts.*;

@Component
public class ApplicationInit implements CommandLineRunner {

    private final UserService userService;
    private final JdbcTemplate jdbcTemplate;

    public ApplicationInit(UserService userService, JdbcTemplate jdbcTemplate) {
        this.userService = userService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {

        this.userService.administratorInit();

        this.initializeArticles();

        this.initializeArticlesPictures();
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

            System.out.println("Database initialization complete!!!");

        } catch (IOException e) {

            System.out.println(e.getMessage());

        }
    }
}
