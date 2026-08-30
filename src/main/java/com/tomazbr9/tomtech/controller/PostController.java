package com.tomazbr9.tomtech.controller;

import com.tomazbr9.tomtech.dto.post.RecoveryPostResponse;
import com.tomazbr9.tomtech.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService service;

    @GetMapping("/{slug}")
    public ResponseEntity<RecoveryPostResponse> getPost(@PathVariable String slug){
        RecoveryPostResponse response = service.getPost(slug);
        return ResponseEntity.ok(response);
    }


}
