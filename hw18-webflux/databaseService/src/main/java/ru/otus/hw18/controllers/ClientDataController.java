package ru.otus.hw18.controllers;


import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw18.dto.ClientDto;
import ru.otus.hw18.mapper.ClientMapper;
import ru.otus.hw18.services.ClientService;

@RestController
public class ClientDataController {
    private static final Logger log = LoggerFactory.getLogger(ClientDataController.class);
    private final ClientService clientService;

    private final ExecutorService executor = Executors.newFixedThreadPool(2);

    public ClientDataController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(value = "/api/client", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<ClientDto> findAll() {
        CompletableFuture<List<ClientDto>> future = CompletableFuture
                .supplyAsync(() ->
                        clientService.findAll().stream()
                                .map(ClientMapper::toDto)
                                .toList(),
                        executor
                );

        //Имитируем flux (по факту с базы всё приходит за раз)
        return Mono.fromFuture(future).flatMapMany(Flux::fromIterable);
    }

    @PostMapping(value = "/api/client", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ClientDto> save(@RequestBody ClientDto dto) {
        var client = ClientMapper.toModel(dto);
        var future = CompletableFuture
                .supplyAsync(() -> ClientMapper.toDto(clientService.save(client)), executor);

        return Mono.fromFuture(future);
    }
}
