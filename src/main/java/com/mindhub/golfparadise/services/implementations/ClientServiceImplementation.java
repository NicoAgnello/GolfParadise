package com.mindhub.golfparadise.services.implementations;

import com.mindhub.golfparadise.dtos.ClientDTO;
import com.mindhub.golfparadise.models.Client;
import com.mindhub.golfparadise.repositories.ClientRepository;
import com.mindhub.golfparadise.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ClientServiceImplementation implements ClientService {
    @Autowired
    ClientRepository clientRepository;

    @Override
    public List<ClientDTO> getClients() {
        return clientRepository.findAll().stream().map(ClientDTO::new).collect(toList());
    }

    @Override
    public ClientDTO findById(Long id) {
        return clientRepository.findById(id).map(ClientDTO::new).orElse(null);
    }

    @Override
    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public void save(Client client) {
        clientRepository.save(client);
    }
}
