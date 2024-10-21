package com.example.demo.DTO;

import lombok.Builder;

@Builder
public record ChatResponse(
    Choice[] choices
) {
}
