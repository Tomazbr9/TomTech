package com.tomazbr9.tomtech.repository;

import com.tomazbr9.tomtech.entity.Category;
import com.tomazbr9.tomtech.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
