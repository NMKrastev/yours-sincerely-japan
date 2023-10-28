package com.yourssincerelyjapan.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@ToString
@Configuration
@ConfigurationProperties(prefix = "news.data")
public class NewsDataConfiguration {

    private String schema;
    private String host;
    private String path;
    private String apikey;
    private String country;
    private String language;
    private String image;
    private String timeframe;
    private boolean enabled;
}
