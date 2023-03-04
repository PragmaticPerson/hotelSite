package com.keydoorhotel.service.services;

import org.springframework.stereotype.Service;

import com.keydoorhotel.dao.ImageRepository;
import com.keydoorhotel.service.model.Image;

@Service
public class ImageService {

	private ImageRepository imageRepository;

	public ImageService(ImageRepository imageRepository) {
		super();
		this.imageRepository = imageRepository;
	}
	
	public Image save(Image image) {
		return imageRepository.save(image);
	}
}
