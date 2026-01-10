package com.enesincekara.dreamshops.response;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public record ValidationErrorResponse(
        String message,
        Map<String, String> errors,
        int status,
        LocalDateTime timestamp
) {
}
