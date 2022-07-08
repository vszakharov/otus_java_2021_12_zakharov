package ru.otus.hw18.services;

import java.util.List;

import org.springframework.stereotype.Service;
import ru.otus.hw18.model.Client;
import ru.otus.hw18.repostory.ClientRepository;
import ru.otus.hw18.sessionmanager.TransactionManager;

@Service
public class ClientServiceImpl implements ClientService {

    private final TransactionManager transactionManager;
    private final ClientRepository clientRepository;

    public ClientServiceImpl(TransactionManager transactionManager, ClientRepository clientRepository) {
        this.transactionManager = transactionManager;
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client save(Client client) {
        return transactionManager.doInTransaction(() -> clientRepository.save(client));
    }
}
