package com.yourssincerelyjapan.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PasswordEncoderMapperTest {

    @Autowired
    private PasswordEncoderMapper passwordEncoderMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void encode() {

        String passwordToEncode = "testPassword";
        String encodedPassword = passwordEncoderMapper.encode(passwordToEncode);

        assertNotNull(encodedPassword);
        assertTrue(passwordEncoder.matches(passwordToEncode, encodedPassword));
    }
}