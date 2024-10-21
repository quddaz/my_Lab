package com.example.demo.DTO;

import lombok.Builder;

@Builder
public record Message(
    String role,
    String content
) {
}
