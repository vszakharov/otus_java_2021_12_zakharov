package ru.otus.service;

import java.util.Optional;

import ru.otus.model.DocumentInfo;

/**
 * Сервис для работы с метаинформацией о документах.
 */
public interface DocumentService {

    /**
     * Сохранить информацию о документе.
     */
    void create(DocumentInfo documentInfo);

    /**
     * Расшарить документ (сделать публичным).
     */
    void share(String documentId);

    /**
     * Получить информацию о документе.
     */
    Optional<DocumentInfo> get(String documentId);
}
