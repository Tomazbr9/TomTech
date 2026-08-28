package com.tomazbr9.tomtech.service;

import com.tomazbr9.tomtech.dto.UrlS3Response;
import com.tomazbr9.tomtech.exception.BusinessRuleException;
import com.tomazbr9.tomtech.exception.ImageUploadFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;

import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.UUID;

@Service
@Slf4j
public class S3StorageService {

    private final S3Client s3Client;
    private final String bucketName;
    private final String bucketUrl;

    public S3StorageService(
            S3Client s3Client,
            @Value("${aws.s3.bucket.name}") String bucketName,
            @Value("${aws.s3.bucket.url}") String bucketUrl
    ) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
        this.bucketUrl = bucketUrl;
    }

    public UrlS3Response uploadFile(String fileOriginalName, String contentType, byte[] content) {

        if(contentType == null || !contentType.startsWith("image/")){
            throw new BusinessRuleException("Apenas imagens são permitidas");
        }

        String uniqueFilename = generateImageName(fileOriginalName);

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(uniqueFilename)
                    .contentType(contentType)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(content));

            String url = bucketUrl + "/" + uniqueFilename;
            return new UrlS3Response(url);

        } catch (Exception e) {
            log.error("Erro ao fazer upload da imagem: " + e);
            throw new ImageUploadFailedException("Falha ao fazer upload da imagem para o S3");
        }
    }

    private String generateImageName(String originalFileName){
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        return "blog-images" + UUID.randomUUID().toString() + extension;
    }
}