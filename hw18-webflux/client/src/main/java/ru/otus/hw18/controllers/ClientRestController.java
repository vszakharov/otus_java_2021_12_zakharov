package ru.otus.hw18.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw18.dto.ClientDto;
import ru.otus.hw18.services.ClientService;

@RestController
public class ClientRestController {

    private final ClientService clientService;

    public ClientRestController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/api/client")
    public ClientDto saveClient(@RequestBody ClientDto client) {
        return clientService.save(client);
    }

}
