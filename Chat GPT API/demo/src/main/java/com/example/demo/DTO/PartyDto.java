package com.example.demo.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Builder
public record PartyDto (    String title,
                            List<Course> courses,
                            @JsonProperty("route summary")
                            String routeSummary)
{
}
