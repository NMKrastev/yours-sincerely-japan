package com.yourssincerelyjapan.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LocalDateTimeMapperTest {

    @Autowired
    private LocalDateTimeMapper localDateTimeMapper;

    @Test
    void createdOn() {
        LocalDateTime inputDateTime = LocalDateTime.of(2023, 1, 1, 12, 0, 0);
        LocalDateTime result = localDateTimeMapper.createdOn(inputDateTime);
        assertNotNull(result);
        assertEquals(inputDateTime, result);
    }

    @Test
    void createdOnWithNullInput() {
        LocalDateTime result = localDateTimeMapper.createdOn(null);
        assertNotNull(result);
    }
}