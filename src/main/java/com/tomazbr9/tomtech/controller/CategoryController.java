package com.tomazbr9.tomtech.controller;

import com.tomazbr9.tomtech.dto.category.CreateCategoryRequest;
import com.tomazbr9.tomtech.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/admin/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @PostMapping
    public ResponseEntity<UUID> createCategory(@RequestBody CreateCategoryRequest request){
        UUID response = service.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
