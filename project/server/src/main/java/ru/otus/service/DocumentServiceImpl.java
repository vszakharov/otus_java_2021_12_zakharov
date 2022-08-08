package ru.otus.service;

import java.util.Optional;

import ru.otus.dao.DocumentDao;
import ru.otus.model.DocumentInfo;

public class DocumentServiceImpl implements DocumentService {
    private final DocumentDao documentDao;

    public DocumentServiceImpl(DocumentDao documentDao) {
        this.documentDao = documentDao;
    }

    @Override
    public void create(DocumentInfo documentInfo) {
        documentDao.create(documentInfo);
    }

    @Override
    public void share(String documentId) {
        documentDao.share(documentId);
    }

    @Override
    public Optional<DocumentInfo> get(String documentId) {
        return documentDao.get(documentId);
    }
}
