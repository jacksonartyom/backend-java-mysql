package com.example.demo.dto.request;

import lombok.Data;

@Data
public class CategoryRequest {
    private String name;
    private String type;
    private String userId;
}