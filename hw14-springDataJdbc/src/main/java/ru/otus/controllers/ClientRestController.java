package ru.otus.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.dto.ClientDto;
import ru.otus.mapper.ClientMapper;
import ru.otus.model.Client;
import ru.otus.services.ClientService;

@RestController
public class ClientRestController {

    private final ClientService clientService;

    public ClientRestController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/api/client")
    public Client saveClient(@RequestBody ClientDto clientDto) {
        var client = ClientMapper.toModel(clientDto);
        return clientService.save(client);
    }

}
