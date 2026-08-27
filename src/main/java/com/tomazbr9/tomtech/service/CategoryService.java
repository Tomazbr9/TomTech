package com.tomazbr9.tomtech.service;

import com.tomazbr9.tomtech.dto.category.CreateCategoryRequest;
import com.tomazbr9.tomtech.entity.Category;
import com.tomazbr9.tomtech.repository.CategoryRepository;
import com.tomazbr9.tomtech.utils.SlugUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public UUID createCategory(CreateCategoryRequest request){

        System.out.println(request.name());

        Category category = Category.builder()
                .name(request.name())
                .slug(SlugUtils.generateSlug(request.name()))
                .color(request.color())
                .build();

        Category savedCategory = categoryRepository.save(category);

        return savedCategory.getId();
    }
}
