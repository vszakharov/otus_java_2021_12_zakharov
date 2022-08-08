package ru.otus.service;

import java.io.InputStream;

import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;

public class StorageServiceImpl implements StorageService {
    private final MinioClient minioClient;
    private final String bucketName;

    public StorageServiceImpl(MinioClient minioClient, String bucketName) {
        this.minioClient = minioClient;
        this.bucketName = bucketName;
    }

    @Override
    public void upload(String documentId, InputStream content) {
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
            try (content) {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucketName)
                                .object(documentId)
                                .stream(content, content.available(), -1)
                                .build()
                );
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public InputStream download(String documentId) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(documentId)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
