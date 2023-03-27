package com.mindhub.golfparadise.controllers;

import com.mindhub.golfparadise.dtos.ClientDTO;
import com.mindhub.golfparadise.models.Client;
import com.mindhub.golfparadise.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    ClientService clientService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/clients")
    public List<ClientDTO> getClients() {
        return clientService.getClients();
    }

    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id) {
        return clientService.findById(id);
    }

    @PostMapping("/clients")
    public ResponseEntity<Object> registerClient(@RequestParam String firstName,
                                           @RequestParam String lastName,
                                           @RequestParam String email,
                                           @RequestParam String password) {

        if (firstName.isEmpty()) {
            return new ResponseEntity<>("Missing first name.", HttpStatus.FORBIDDEN);
        } else if (lastName.isEmpty()) {
            return new ResponseEntity<>("Missing last name.", HttpStatus.FORBIDDEN);
        } else if (email.isEmpty()) {
            return new ResponseEntity<>("Missing email", HttpStatus.FORBIDDEN);
        } else if (password.isEmpty()) {
            return new ResponseEntity<>("Missing password", HttpStatus.FORBIDDEN);
        } else if (clientService.findByEmail(email) != null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }

        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientService.save(client);
        return new ResponseEntity<>("Client created.", HttpStatus.CREATED);
    }

    @GetMapping("/clients/current")
    public ClientDTO getCurrentClient(Authentication authentication) {
        return new ClientDTO(clientService.findByEmail(authentication.getName()));
    }

}
