package com.keydoorhotel.service.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "room_type")
public class RoomType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "title")
	private String title;

	@Column(name = "source")
	private String source;

	@Column(name = "price")
	private int price;

	@Column(name = "max_people")
	private int maxPeople;

	@Column(name = "image_count")
	private int imageCount;

	@Column(name = "panorama_url_id")
	private String panoramaUrlId;

	@Column(name = "description")
	private String description;

	public RoomType() {
		super();
	}

	public RoomType(int id, String title, String source, int price, int maxPeople, int imageCount, String panoramaUrlId,
			String description) {
		super();
		this.id = id;
		this.title = title;
		this.source = source;
		this.price = price;
		this.maxPeople = maxPeople;
		this.imageCount = imageCount;
		this.panoramaUrlId = panoramaUrlId;
		this.description = description;
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getMaxPeople() {
		return maxPeople;
	}

	public void setMaxPeople(int maxPeople) {
		this.maxPeople = maxPeople;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, id, imageCount, maxPeople, panoramaUrlId, price, source, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoomType other = (RoomType) obj;
		return Objects.equals(description, other.description) && id == other.id && imageCount == other.imageCount
				&& maxPeople == other.maxPeople && Objects.equals(panoramaUrlId, other.panoramaUrlId)
				&& price == other.price && Objects.equals(source, other.source) && Objects.equals(title, other.title);
	}

	@Override
	public String toString() {
		return "RoomType [id=" + id + ", title=" + title + ", source=" + source + ", price=" + price + ", maxPeople="
				+ maxPeople + ", imageCount=" + imageCount + ", panoramaUrlId=" + panoramaUrlId + ", description="
				+ description + "]";
	}
}
