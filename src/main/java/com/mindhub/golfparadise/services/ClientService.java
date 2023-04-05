package com.mindhub.golfparadise.services;

import com.mindhub.golfparadise.dtos.ClientDTO;
import com.mindhub.golfparadise.models.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ClientService {

    List<ClientDTO> getClients();

    ClientDTO findById(Long id);

    Client findByEmail(String email);

    void save(Client client);

    ResponseEntity<Object> registerClient(@RequestParam String firstName,
                                          @RequestParam String lastName,
                                          @RequestParam String email,
                                          @RequestParam String password);
}
