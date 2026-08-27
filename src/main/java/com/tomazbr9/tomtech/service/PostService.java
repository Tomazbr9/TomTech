package com.tomazbr9.tomtech.service;

import com.tomazbr9.tomtech.dto.post.CreatePostRequest;
import com.tomazbr9.tomtech.entity.Category;
import com.tomazbr9.tomtech.entity.Post;
import com.tomazbr9.tomtech.entity.User;
import com.tomazbr9.tomtech.enums.PostStatus;
import com.tomazbr9.tomtech.exception.AccessDeniedException;
import com.tomazbr9.tomtech.exception.BusinessRuleException;
import com.tomazbr9.tomtech.exception.ResourceNotFoundException;
import com.tomazbr9.tomtech.repository.CategoryRepository;
import com.tomazbr9.tomtech.repository.PostRepository;
import com.tomazbr9.tomtech.repository.UserRepository;
import com.tomazbr9.tomtech.utils.SlugUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Transactional
    public UUID createPost(CreatePostRequest request, UUID userId){

        User user = userRepository.getReferenceById(userId);

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        Post post = Post.builder()
                .title(request.title())
                .slug(SlugUtils.generateSlug(request.title()))
                .summary(request.summary())
                .content(request.content())
                .coverImageUrl("teste")
                .user(user)
                .category(category)
                .status(request.status())
                .build();

        Post savedPost = postRepository.save(post);

        return savedPost.getId();
    }

    public UUID publish(UUID postId, UUID userId){

        Post post = postRepository.findByIdAndUserId(postId, userId)
                .orElseThrow(() -> new AccessDeniedException("Você não tem permissão para publicar esse artigo"));

        if (post.getStatus().equals(PostStatus.PUBLISHED)){
            throw new BusinessRuleException("Artigo já publicado!");
        }

        post.setStatus(PostStatus.PUBLISHED);

        postRepository.save(post);

        return post.getId();

    }

}
