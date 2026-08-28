package com.tomazbr9.tomtech.controller;

import com.tomazbr9.tomtech.dto.post.CreatePostRequest;
import com.tomazbr9.tomtech.security.UserDetailsImpl;
import com.tomazbr9.tomtech.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService service;

    @PostMapping
    public ResponseEntity<UUID> createPost(
            @RequestBody CreatePostRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        UUID response = service.createPost(request, userDetails.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}/publish")
    public ResponseEntity<Void> publish(
            @PathVariable UUID postId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        service.publish(postId, userDetails.getId());
        return ResponseEntity.accepted().build();
    }

    @PatchMapping("/{id}/archive")
    public ResponseEntity<Void> archive(
            @PathVariable UUID postId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        service.archive(postId, userDetails.getId());
        return ResponseEntity.accepted().build();
    }
}
