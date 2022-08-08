package ru.otus.service;

import java.time.Instant;
import java.util.UUID;

public class DocumentIdGeneratorImpl implements DocumentIdGenerator {
    @Override
    public String generateId() {
        return UUID.randomUUID() + "-" + Instant.now().getEpochSecond();
    }
}
