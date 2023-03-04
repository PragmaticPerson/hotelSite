package com.keydoorhotel.service.dto;

import java.util.Base64;
import java.util.Objects;

import com.keydoorhotel.service.model.Image;

public class ImageDTO {

	private int id;
	private String name;
	private String image;

	public ImageDTO() {
		super();
	}

	public ImageDTO(Image image) {
		super();
		id = image.getId();
		name = image.getName();
		this.image = Base64.getEncoder().encodeToString(image.getImage());
	}

	public ImageDTO(int id, String name, byte[] image) {
		super();
		this.id = id;
		this.name = name;
		this.image = Base64.getEncoder().encodeToString(image);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, image, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImageDTO other = (ImageDTO) obj;
		return id == other.id && Objects.equals(image, other.image) && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "ImageDTO [id=" + id + ", name=" + name + ", image=" + image + "]";
	}
}