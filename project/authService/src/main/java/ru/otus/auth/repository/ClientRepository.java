package ru.otus.auth.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.auth.model.Client;

public interface ClientRepository extends CrudRepository<Client, String> {
}
