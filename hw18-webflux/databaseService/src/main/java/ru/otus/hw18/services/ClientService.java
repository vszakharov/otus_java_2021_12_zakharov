package ru.otus.hw18.services;

import ru.otus.hw18.model.Client;

import java.util.List;

public interface ClientService {
    List<Client> findAll();
    Client save(Client client);
}
