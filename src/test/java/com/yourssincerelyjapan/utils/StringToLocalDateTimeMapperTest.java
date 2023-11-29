package com.yourssincerelyjapan.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StringToLocalDateTimeMapperTest {

    @Autowired
    private StringToLocalDateTimeMapper stringToLocalDateTimeMapper;

    @Test
    void convertToLocalDateTime() {
        String dateTimeString = "2023-01-01 12:34:56";
        LocalDateTime convertedDateTime = stringToLocalDateTimeMapper.convertToLocalDateTime(dateTimeString);

        assertNotNull(convertedDateTime);
        assertEquals(dateTimeString, convertedDateTime.toString().replace("T", " "));
    }
}