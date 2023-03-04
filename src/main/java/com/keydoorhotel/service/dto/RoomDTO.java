package com.keydoorhotel.service.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.keydoorhotel.service.model.Image;
import com.keydoorhotel.service.model.Room;

public class RoomDTO {

	private int id;
	private String title;
	private int maxPeople;
	private int price;
	private List<ImageDTO> images;

	public RoomDTO(Room room) {
		id = room.getId();
		title = room.getTitle();
		maxPeople = room.getMaxPeople();
		price = room.getPrice();

		images = new ArrayList<>();
		for (Image img : room.getImages()) {
			this.images.add(new ImageDTO(img));
		}
	}

	public RoomDTO(int id, String title, int maxPeople, int price, List<Image> images) {
		super();
		this.id = id;
		this.title = title;
		this.maxPeople = maxPeople;
		this.price = price;

		this.images = new ArrayList<>();
		for (Image img : images) {
			this.images.add(new ImageDTO(img));
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getMaxPeople() {
		return maxPeople;
	}

	public void setMaxPeople(int maxPeople) {
		this.maxPeople = maxPeople;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public List<ImageDTO> getImages() {
		return images;
	}

	public void setImages(List<ImageDTO> images) {
		this.images = images;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, images, maxPeople, price, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoomDTO other = (RoomDTO) obj;
		return id == other.id && Objects.equals(images, other.images) && maxPeople == other.maxPeople
				&& price == other.price && Objects.equals(title, other.title);
	}
}