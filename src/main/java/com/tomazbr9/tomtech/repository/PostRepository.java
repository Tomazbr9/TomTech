package com.tomazbr9.tomtech.repository;

import com.tomazbr9.tomtech.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    Optional<Post> findByIdAndUserId(UUID postId, UUID userId);
    Optional<Post> findBySlug(String slug);
}
