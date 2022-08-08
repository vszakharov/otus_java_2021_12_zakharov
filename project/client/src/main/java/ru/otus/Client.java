package ru.otus;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.dto.TokenResponseDto;
import ru.otus.dto.UploadInfoDto;
import ru.otus.dto.UserDto;
import ru.otus.multipart.MimeMultipartData;

public class Client {
    private static final Logger log = LoggerFactory.getLogger(Client.class);
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String user1 = "user1";
    private final String secret1 = "secret1";
    private final String user2 = "user2";
    private final String secret2 = "secret2";
    private final String inputDirectory = "input";
    private final String outputDirectory = "output";

    private final String fileName = "file1";

    public static void main(String[] args) throws IOException, InterruptedException {
        new Client().start();
    }

    private void start() throws IOException, InterruptedException {
        var uploadPath1 = Path.of(System.getProperty("user.dir"), user1, inputDirectory,fileName);
        var downloadPath1 = Path.of(System.getProperty("user.dir"), user1, outputDirectory);
        var downloadPath2 = Path.of(System.getProperty("user.dir"), user2, outputDirectory);

        //User1 загружает документ на сервер
        var documentId = uploadDocument(user1, secret1, uploadPath1);
        //User1 скачивает документ в свою папку
        downloadDocument(user1, secret1, documentId, downloadPath1);
        //User2 пытается скачать документ в свою папку и получает ошибку
        try {
            downloadDocument(user2, secret2, documentId, downloadPath2);
        } catch (Exception e) {
            log.error("Ошибка загрузки документа: {}", documentId);
        }
        //User1 расшаривает документ (делает публичным)
        shareDocument(user1, secret1, documentId);
        //User2 успешно скачивает документ в свою папку
        downloadDocument(user2, secret2, documentId, downloadPath2);
    }

    private String uploadDocument(
            String clientId,
            String clientSecret,
            Path uploadPath
    ) throws IOException, InterruptedException {

        String url = "http://localhost:8081/documents/upload";
        var token = requestToken(clientId, clientSecret);

        var mimeMultipartData = MimeMultipartData.newBuilder()
                .addFile("document", uploadPath, Files.probeContentType(uploadPath))
                .build();

        var request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", mimeMultipartData.getContentType())
                .header("Accept", "application/json")
                .POST(mimeMultipartData.getBodyPublisher())
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        var uploadInfoDto = objectMapper.readValue(response.body(), UploadInfoDto.class);

        return uploadInfoDto.documentId();
    }

    private void downloadDocument(
            String clientId,
            String clientSecret,
            String documentId,
            Path path
    ) throws IOException, InterruptedException {
        String url = "http://localhost:8081/documents/" + documentId + "/download";

        HttpRequest request = createRequest(clientId, clientSecret, url);
        HttpResponse<Path> response = client.send(request,
                HttpResponse.BodyHandlers.ofFileDownload(path,
                        StandardOpenOption.CREATE, StandardOpenOption.WRITE));
    }

    private void shareDocument(
            String clientId,
            String clientSecret,
            String documentId
    ) throws IOException, InterruptedException {
        String url = "http://localhost:8081/documents/" + documentId + "/share";
        HttpRequest request = createRequest(clientId, clientSecret, url);
        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private HttpRequest createRequest(
            String clientId,
            String clientSecret,
            String url
    ) throws IOException, InterruptedException {
        var token = requestToken(clientId, clientSecret);
        var requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json");

        return requestBuilder.build();
    }

    private String requestToken(String clientId, String clientSecret) throws IOException, InterruptedException {
        String url = "http://localhost:8082/auth/token";
        UserDto userDto = new UserDto(clientId, clientSecret);
        String requestBody = objectMapper.writeValueAsString(userDto);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        TokenResponseDto tokenResponseDto = objectMapper.readValue(response.body(), TokenResponseDto.class);

        return tokenResponseDto.token();
    }
}
