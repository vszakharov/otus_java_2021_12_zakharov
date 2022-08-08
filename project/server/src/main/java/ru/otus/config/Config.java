package ru.otus.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.otus.auth.TokenService;
import ru.otus.auth.TokenServiceImpl;
import ru.otus.dao.DocumentDao;
import ru.otus.dao.DocumentDaoImpl;
import ru.otus.service.DocumentIdGenerator;
import ru.otus.service.DocumentIdGeneratorImpl;
import ru.otus.service.DocumentService;
import ru.otus.service.DocumentServiceImpl;
import ru.otus.service.StorageService;
import ru.otus.service.StorageServiceImpl;

@Configuration
public class Config {

    @Bean
    public MinioClient minioClient(
            @Value("${minio.endpoint}") String endpoint,
            @Value("${minio.accesskey}") String accessKey,
            @Value("${minio.secretkey}") String secretKey
    ) {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }

    @Bean
    public StorageService storageService(
            MinioClient minioClient,
            @Value("${minio.bucketname}") String bucketName
    ) {
        return new StorageServiceImpl(minioClient, bucketName);
    }

    @Bean
    public DocumentDao documentDao(
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
    ) {
        return new DocumentDaoImpl(namedParameterJdbcTemplate);
    }

    @Bean
    public DocumentService documentService(
            DocumentDao documentDao
    ) {
        return new DocumentServiceImpl(documentDao);
    }

    @Bean
    public DocumentIdGenerator documentIdGenerator() {
        return new DocumentIdGeneratorImpl();
    }

    @Bean
    public TokenService tokenService(
            @Value("${auth.jwt.secret}")String secretKey
    ) {
        return new TokenServiceImpl(secretKey);
    }
}
