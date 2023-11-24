package com.yourssincerelyjapan.service.impl;

import com.yourssincerelyjapan.config.ReCaptchaConfig;
import com.yourssincerelyjapan.model.dto.ReCaptchaResponseDTO;
import com.yourssincerelyjapan.service.ReCaptchaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.Optional;

@Service
public class ReCaptchaServiceImpl implements ReCaptchaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReCaptchaServiceImpl.class);

    private final WebClient webClient;
    private final ReCaptchaConfig reCaptchaConfig;

    public ReCaptchaServiceImpl(WebClient webClient, ReCaptchaConfig reCaptchaConfig) {
        this.webClient = webClient;
        this.reCaptchaConfig = reCaptchaConfig;
    }

    @Override
    public Optional<ReCaptchaResponseDTO> verify(String token) {

        return Optional.ofNullable(this.webClient
                .post()
                .uri(this::buildReCaptchaURI)
                .body(BodyInserters
                        .fromFormData("secret", this.reCaptchaConfig.getSecretKey())
                        .with("response", token))
                .retrieve()
                .bodyToMono(ReCaptchaResponseDTO.class)
                .doOnError(t -> LOGGER.error("Error fetching google response... ", t))
                .onErrorComplete()
                .block());
    }

    private URI buildReCaptchaURI(UriBuilder uriBuilder) {

        return uriBuilder
                .scheme("https")
                .host("www.google.com")
                .path("/recaptcha/api/siteverify")
                .build();

    }
}
