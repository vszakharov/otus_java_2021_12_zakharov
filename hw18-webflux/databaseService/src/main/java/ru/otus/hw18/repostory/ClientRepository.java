package ru.otus.hw18.repostory;

import org.springframework.data.repository.CrudRepository;
import ru.otus.hw18.model.Client;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client, Long> {

    List<Client> findAll();
}
