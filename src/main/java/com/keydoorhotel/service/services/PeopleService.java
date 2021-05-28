package com.keydoorhotel.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.keydoorhotel.dao.PeopleRepository;
import com.keydoorhotel.service.dto.OrderDTO;
import com.keydoorhotel.service.model.People;

@Component
public class PeopleService {

    private PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public People save(OrderDTO order) {
        return peopleRepository.save(order.getPeople());
    }
}
