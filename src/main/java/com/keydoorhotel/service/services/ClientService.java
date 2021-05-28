package com.keydoorhotel.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.keydoorhotel.dao.ClientRepository;
import com.keydoorhotel.service.dto.OrderDTO;
import com.keydoorhotel.service.model.Client;

@Component
public class ClientService {

    private ClientRepository peopleRepository;

    @Autowired
    public ClientService(ClientRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public Client save(OrderDTO order) {
        return peopleRepository.save(order.getPeople());
    }
}
