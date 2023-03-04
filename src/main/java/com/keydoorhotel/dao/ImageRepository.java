package com.keydoorhotel.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.keydoorhotel.service.model.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {

}
