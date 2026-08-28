package com.tomazbr9.tomtech.service;

import com.tomazbr9.tomtech.dto.post.CreatePostRequest;
import com.tomazbr9.tomtech.entity.Category;
import com.tomazbr9.tomtech.entity.Post;
import com.tomazbr9.tomtech.entity.User;
import com.tomazbr9.tomtech.enums.PostStatus;
import com.tomazbr9.tomtech.exception.BusinessRuleException;
import com.tomazbr9.tomtech.exception.ResourceNotFoundException;
import com.tomazbr9.tomtech.repository.CategoryRepository;
import com.tomazbr9.tomtech.repository.PostRepository;
import com.tomazbr9.tomtech.repository.UserRepository;
import com.tomazbr9.tomtech.utils.SlugUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private static final String DEFAULT_COVER =
            "https://images.unsplash.com/photo-1555066931-4365d14bab8c?q=80&w=600";

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Transactional
    public UUID createPost(CreatePostRequest request, UUID userId){

        log.info("Solicitação para criação de Artigo com titulo: {}", request.title());

        User user = userRepository.getReferenceById(userId);

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        String coverImageUrl = getUrlFirstImageContent(request.content()).orElse(DEFAULT_COVER);

        Post post = Post.builder()
                .title(request.title())
                .slug(SlugUtils.generateSlug(request.title()))
                .summary(request.summary())
                .content(request.content())
                .coverImageUrl(coverImageUrl)
                .user(user)
                .category(category)
                .status(request.status())
                .build();

        Post savedPost = postRepository.save(post);

        log.info("Artigo criado com sucesso: {}", savedPost.getTitle());

        return savedPost.getId();
    }

    public void publish(UUID postId, UUID userId){

        log.info("Solicitação para publicar artigo: {}", postId);

        Post post = postRepository.findByIdAndUserId(postId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Artigo não encontrado"));

        if (post.getStatus() != PostStatus.DRAFT){
            throw new BusinessRuleException("Não é possivel publicar artigo com status " + post.getStatus());
        }

        post.setStatus(PostStatus.PUBLISHED);

        log.info("Artigo publicado com sucesso: {}", postId);

        postRepository.save(post);

    }

    public void archive(UUID postId, UUID userId){

        log.info("Solicitação para arquivar artigo: {}", postId);

        Post post = postRepository.findByIdAndUserId(postId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Artigo não encontrado"));

        if(post.getStatus() != PostStatus.PUBLISHED){
            throw new BusinessRuleException("Não é possivel publicar artigo com status " + post.getStatus());
        }

        post.setStatus(PostStatus.ARCHIVED);

        log.info("Artigo arquivado com sucesso: {}", postId);

        postRepository.save(post);
    }

    private Optional<String> getUrlFirstImageContent(String content){
        Document document = Jsoup.parse(content);
        Element img = document.selectFirst("img");

        if(img == null){
            return Optional.empty();
        }

        String src = img.attr("src");

        if (src.isBlank()) {
            return Optional.empty();
        }

        return Optional.of(src);
    }

}
