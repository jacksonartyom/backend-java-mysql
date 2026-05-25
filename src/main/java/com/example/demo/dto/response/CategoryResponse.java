package com.example.demo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CategoryResponse {
    @JsonProperty("_id")
    private String categoryId;
    private String name;
	private String type;
}