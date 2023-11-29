package com.yourssincerelyjapan.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StringToLowerCaseMapperTest {

    @Autowired
    private StringToLowerCaseMapper stringToLowerCaseMapper;

    @Test
    void stringToLowerCase() {
        String originalString = "Hello World";
        String convertedString = stringToLowerCaseMapper.stringToLowerCase(originalString);

        assertEquals("hello world", convertedString);
    }
}