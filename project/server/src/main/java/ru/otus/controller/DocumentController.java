package ru.otus.controller;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.otus.response.ErrorResponse;
import ru.otus.dto.UploadInfoDto;
import ru.otus.exception.ForbiddenException;
import ru.otus.exception.StorageException;
import ru.otus.model.DocumentInfo;
import ru.otus.request.AuthenticatedRequest;
import ru.otus.service.DocumentIdGenerator;
import ru.otus.service.DocumentService;
import ru.otus.service.StorageService;

import static com.google.common.net.HttpHeaders.CONTENT_DISPOSITION;
import static java.lang.String.format;

@RestController
@RequestMapping("/documents")
public class DocumentController {
    private static final Logger log = LoggerFactory.getLogger(DocumentController.class);
    private static final String CONTENT_HEADER_VALUE = "attachment; filename = %s";

    private final StorageService storageService;
    private final DocumentService documentService;
    private final DocumentIdGenerator documentIdGenerator;

    public DocumentController(
            StorageService storageService,
            DocumentService documentService,
            DocumentIdGenerator documentIdGenerator
    ) {
        this.storageService = storageService;
        this.documentService = documentService;
        this.documentIdGenerator = documentIdGenerator;
    }

    @PostMapping("/upload")
    public UploadInfoDto upload(
            @RequestParam("document") MultipartFile document,
            AuthenticatedRequest request
    ) {
        var documentId = documentIdGenerator.generateId();
        var documentName = document.getOriginalFilename();
        var size = document.getSize();
        var owner = request.getUserId();
        var content = getContent(document);
        storageService.upload(documentId, content);
        documentService.create(new DocumentInfo(documentId, documentName, size, owner, false));

        return new UploadInfoDto(documentId);
    }

    @GetMapping("{documentId}/download")
    public ResponseEntity<Resource> download(
            @PathVariable String documentId,
            AuthenticatedRequest request
    ) {
        var owner = request.getUserId();
        var documentInfo = documentService.get(documentId);
        if (documentInfo.isEmpty()) {
            throw new StorageException("Document does not exist! documentId: " + documentId);
        }
        var document = documentInfo.get();
        if (!document.isPublic() && !document.owner().equals(owner)) {
            throw new ForbiddenException("The document is private, you do not own it!");
        }
        var content = storageService.download(documentId);
        InputStreamResource resource = new InputStreamResource(content);

        return ResponseEntity.ok()
                .header(CONTENT_DISPOSITION, format(CONTENT_HEADER_VALUE, document.documentName()))
                .contentLength(document.size())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("{documentId}/share")
    public ResponseEntity<Void> share(
            @PathVariable String documentId,
            AuthenticatedRequest request
    ) {
        var owner = request.getUserId();
        var documentInfo = documentService.get(documentId);
        if (documentInfo.isEmpty()) {
            throw new StorageException("Document does not exist! documentId: " + documentId);
        }
        var document = documentInfo.get();
        if (!document.isPublic() && !document.owner().equals(owner)) {
            throw new ForbiddenException("You cannot change information about the document because you are not its owner!");
        }
        documentService.share(documentId);

        return ResponseEntity.ok().build();
    }

    private InputStream getContent(MultipartFile file) {
        try {
            return file.getInputStream();
        } catch (IOException e) {
            log.error("Ошибка загрузки файла", e);
            throw new RuntimeException(e);
        }
    }

    @ExceptionHandler({ForbiddenException.class})
    public ResponseEntity<ErrorResponse> handleForbiddenException(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
//                .header(CONTENT_DISPOSITION, format(CONTENT_HEADER_VALUE, "error.msg"))
                .body(new ErrorResponse(ex.getMessage()));
    }
}
