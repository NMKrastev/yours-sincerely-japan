package com.yourssincerelyjapan.utils;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StringToLowerCaseMapper {

    @StringToLowerCaseMapping
    public String stringToLowerCase(String value) {

        return value.toLowerCase();
    }
}
