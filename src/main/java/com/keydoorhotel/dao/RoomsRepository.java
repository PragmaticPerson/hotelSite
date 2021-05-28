package com.keydoorhotel.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.keydoorhotel.service.model.Rooms;

public interface RoomsRepository extends JpaRepository<Rooms, Integer> {

    public Rooms findBySrc(String src);
}
