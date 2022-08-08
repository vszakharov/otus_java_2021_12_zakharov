package ru.otus.service;

import java.io.InputStream;

/**
 * Сервис для работы документами (непосредственно с содержимым документов)
 */
public interface StorageService {

    void upload(String documentId, InputStream content);

    InputStream download(String documentId);
}
