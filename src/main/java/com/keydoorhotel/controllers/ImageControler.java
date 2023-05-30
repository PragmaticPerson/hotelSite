package com.keydoorhotel.controllers;

import org.springframework.http.MediaType;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.keydoorhotel.service.services.ImageService;

@Controller
public class ImageControler {

	private ImageService imageService;

	public ImageControler(ImageService imageService) {
		super();
		this.imageService = imageService;
	}

	@GetMapping("/img/rooms/{source}/{name}")
	public void getImageAsByteArray(HttpServletResponse response, @PathVariable String source,
			@PathVariable String name) throws IOException {
		var file = imageService.loadImageBySourceAndName(source, name);
		response.setContentType(MediaType.IMAGE_PNG_VALUE);
		IOUtils.copy(file, response.getOutputStream());
		file.close();
	}
}
