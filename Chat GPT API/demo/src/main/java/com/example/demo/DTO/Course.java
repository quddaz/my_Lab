package com.example.demo.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;


@Builder
public record Course(
    String name,
    String content,
    String rating,
    String address,
    @JsonProperty("estimated travel time") String estimatedTravelTime
) {
}