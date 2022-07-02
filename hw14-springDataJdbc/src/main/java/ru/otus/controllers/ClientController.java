package ru.otus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.mapper.ClientMapper;
import ru.otus.services.ClientService;

@Controller
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping({"/", "/client/list"})
    public String clientsListView(Model model) {
        var clients = clientService.findAll();
        model.addAttribute("clients", clients);

        return "clientsList";
    }

    @GetMapping("/client/create")
    public String clientCreateView(Model model) {
        return "clientCreate";
    }
}
