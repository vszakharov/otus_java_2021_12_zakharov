package ru.otus.model;

public record DocumentInfo(
        String documentId,
        String documentName,
        Long size,
        String owner,
        boolean isPublic
) {

}
