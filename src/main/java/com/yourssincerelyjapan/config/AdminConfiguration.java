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
@ConfigurationProperties(prefix = "yours.sincerely.japan.admin")
public class AdminConfiguration {

    private String password;
}
