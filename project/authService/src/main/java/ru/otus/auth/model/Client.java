package ru.otus.auth.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@Table("client")
public class Client implements Persistable<String> {

    @Id
    private final String id;

    private final String hash;

    @Transient
    private final boolean isNew;

    public Client(String id, String hash, boolean isNew) {
        this.id = id;
        this.hash = hash;
        this.isNew = isNew;
    }

    @PersistenceConstructor
    public Client(String id, String hash) {
        this(id, hash, false);
    }

    @Override
    public String getId() {
        return id;
    }

    public String getHash() {
        return hash;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}
