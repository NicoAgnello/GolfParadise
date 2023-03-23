package com.mindhub.golfparadise.services;

import com.mindhub.golfparadise.dtos.ClientDTO;
import com.mindhub.golfparadise.models.Client;

import java.util.List;

public interface ClientService {
    List<ClientDTO> getClients();

    ClientDTO findById(Long id);

    Client findByEmail(String email);

    void save(Client client);
}
