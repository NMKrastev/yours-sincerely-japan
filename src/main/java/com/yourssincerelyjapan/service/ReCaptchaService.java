package com.yourssincerelyjapan.service;

import com.yourssincerelyjapan.model.dto.ReCaptchaResponseDTO;

import java.util.Optional;

public interface ReCaptchaService {

    Optional<ReCaptchaResponseDTO> verify(String token);
}
