package ru.otus.services;

import ru.otus.model.Client;

import java.util.List;

public interface ClientService {
    List<Client> findAll();
    Client save(Client client);
}
