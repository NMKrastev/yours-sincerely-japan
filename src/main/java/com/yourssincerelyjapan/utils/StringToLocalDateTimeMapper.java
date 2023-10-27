package com.yourssincerelyjapan.utils;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StringToLocalDateTimeMapper {

    @StringToLocalDateTime
    public LocalDateTime convertToLocalDateTime(String value) {

        final String date = value.split("\\s+")[0];
        final String time = value.split("\\s+")[1];

        return LocalDateTime.parse(date.concat("T").concat(time))/*.plusHours(8)*/;
    }
}
