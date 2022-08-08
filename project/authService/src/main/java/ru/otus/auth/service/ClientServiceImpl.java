package ru.otus.auth.service;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import ru.otus.auth.exception.LoginException;
import ru.otus.auth.exception.RegistrationException;
import ru.otus.auth.model.Client;
import ru.otus.auth.repository.ClientRepository;

public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void register(String clientId, String clientSecret) {
        if(clientRepository.findById(clientId).isPresent())
            throw new RegistrationException(
                    "Client with id: " + clientId + " already registered");

        String hash = BCrypt.hashpw(clientSecret, BCrypt.gensalt());
        clientRepository.save(new Client(clientId, hash, true));
    }

    @Override
    public void checkCredentials(String clientId, String clientSecret) {
        Optional<Client> clientInfo = clientRepository
                .findById(clientId);
        if (clientInfo.isEmpty())
            throw new LoginException(
                    "Client with id: " + clientId + " not found");

        if (!BCrypt.checkpw(clientSecret, clientInfo.get().getHash()))
            throw new LoginException("Secret is incorrect");
    }
}
