package ru.otus.dao;

import java.util.Optional;

import ru.otus.model.DocumentInfo;

public interface DocumentDao {

    /**
     * Сохранить в базу информацию о документе.
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
