package com.keydoorhotel.service.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "room")
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "title")
	private String title;

	@Column(name = "max_people")
	private int maxPeople;

	@Column(name = "price")
	private int price;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "room_image", joinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "image_id", referencedColumnName = "id"))
	private List<Image> images;

	public Room() {
		images = new ArrayList<>();
	}

	public Room(String title, int maxPeople, int price) {
		this.title = title;
		this.maxPeople = maxPeople;
		this.price = price;
		images = new ArrayList<>();
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

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public List<Image> addImage(Image image) {
		images.add(image);
		return images;
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
		Room other = (Room) obj;
		return id == other.id && Objects.equals(images, other.images) && maxPeople == other.maxPeople
				&& price == other.price && Objects.equals(title, other.title);
	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", title=" + title + ", maxPeople=" + maxPeople + ", price=" + price + "]";
	}
}
