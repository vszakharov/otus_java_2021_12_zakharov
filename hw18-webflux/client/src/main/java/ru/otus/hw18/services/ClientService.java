package ru.otus.hw18.services;

import java.util.List;

import ru.otus.hw18.dto.ClientDto;

public interface ClientService {
    List<ClientDto> findAll();
    ClientDto save(ClientDto client);
}
