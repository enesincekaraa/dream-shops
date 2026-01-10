package com.enesincekara.dreamshops.request;

import jakarta.validation.constraints.PositiveOrZero;

public record StockUpdateRequest(
        @PositiveOrZero(message = "Quantity must be greater than zero")
        int quantity
) {
}
