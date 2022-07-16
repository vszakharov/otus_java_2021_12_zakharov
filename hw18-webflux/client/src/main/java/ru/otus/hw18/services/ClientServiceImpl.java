package ru.otus.hw18.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.otus.hw18.dto.ClientDto;

@Service
public class ClientServiceImpl implements ClientService {
    private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final WebClient client;

    public ClientServiceImpl(WebClient.Builder builder) {
        client = builder
                .baseUrl("http://localhost:8081")
                .build();
    }

    @Override
    public List<ClientDto> findAll() {
        var flux = client.get().uri("/api/client")
                .accept(MediaType.APPLICATION_NDJSON)
                .retrieve()
                .bodyToFlux(ClientDto.class)
                .doOnNext(val -> log.info("val:{}", val));

        return flux.collectList().block();
    }

    @Override
    public ClientDto save(ClientDto clientDto) {
        var mono = client.post().uri("/api/client")
                .bodyValue(clientDto)
                .retrieve()
                .bodyToMono(ClientDto.class)
                .doOnNext(val -> log.info("val:{}", val));

        return mono.block();
    }
}
