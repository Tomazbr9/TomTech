package com.tomazbr9.tomtech.controller;

import com.tomazbr9.tomtech.dto.UrlS3Response;
import com.tomazbr9.tomtech.exception.InvalidImageException;
import com.tomazbr9.tomtech.service.S3StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/media")
@RequiredArgsConstructor
public class MediaController {

    private final S3StorageService service;

    @PostMapping("/upload")
    public ResponseEntity<UrlS3Response> uploadImage(@RequestParam("file") MultipartFile file) {
        try {

            UrlS3Response response = service.uploadFile(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes()
            );

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            throw new InvalidImageException("A Imagem enviada não é válida");
        } catch (IOException e) {
            throw new RuntimeException("Erro ao extrair os bytes do MultipartFile");
        }
    }
}