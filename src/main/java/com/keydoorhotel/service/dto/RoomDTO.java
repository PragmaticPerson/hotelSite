package com.keydoorhotel.service.dto;

import java.util.Objects;

import com.keydoorhotel.service.model.Room;

public class RoomDTO {

	private int id;
	private String title;
	private String source;
	private int maxPeople;
	private int price;
	private int imageCount;
	private String panoramaUrlId;

	public RoomDTO(Room room) {
		id = room.getId();
		title = room.getTitle();
		maxPeople = room.getMaxPeople();
		price = room.getPrice();
		source = room.getSource();
		imageCount = room.getImageCount();
		panoramaUrlId = room.getPanoramaUrlId();
	}

	public RoomDTO(int id, String title, int maxPeople, int price, String source, int imageCount,
			String panoramaUrlId) {
		super();
		this.id = id;
		this.title = title;
		this.maxPeople = maxPeople;
		this.price = price;
		this.source = source;
		this.imageCount = imageCount;
		this.panoramaUrlId = panoramaUrlId;
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getImageCount() {
		return imageCount;
	}

	public void setImageCount(int imageCount) {
		this.imageCount = imageCount;
	}

	public String getPanoramaUrlId() {
		return panoramaUrlId;
	}

	public void setPanoramaUrlId(String panoramaUrlId) {
		this.panoramaUrlId = panoramaUrlId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, imageCount, maxPeople, panoramaUrlId, price, source, title);
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
		return id == other.id && imageCount == other.imageCount && maxPeople == other.maxPeople
				&& Objects.equals(panoramaUrlId, other.panoramaUrlId) && price == other.price
				&& Objects.equals(source, other.source) && Objects.equals(title, other.title);
	}
}