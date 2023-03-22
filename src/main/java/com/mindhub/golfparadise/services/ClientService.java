package com.mindhub.golfparadise.services;

import com.mindhub.golfparadise.dtos.ClientDTO;

import java.util.List;

public interface ClientService {
    List<ClientDTO> getClients();
}
