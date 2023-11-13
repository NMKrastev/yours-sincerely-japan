package com.yourssincerelyjapan.utils;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocalDateTimeMapper {

    @LocalDateTimeMapping
    public LocalDateTime createdOn(LocalDateTime createdOn) {

        if (createdOn == null) {

            createdOn = LocalDateTime.now();
        }

        return createdOn;
    }
}
