package com.keydoorhotel.service.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

	@Column(name = "source")
	private String source;

	@Column(name = "max_people")
	private int maxPeople;

	@Column(name = "price")
	private int price;

	public Room() {
	}

	public Room(String title, String source, int maxPeople, int price) {
		this.title = title;
		this.source = source;
		this.maxPeople = maxPeople;
		this.price = price;
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

	@Override
	public int hashCode() {
		return Objects.hash(id, maxPeople, price, source, title);
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
		return id == other.id && maxPeople == other.maxPeople && price == other.price
				&& Objects.equals(source, other.source) && Objects.equals(title, other.title);
	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", title=" + title + ", source=" + source + ", maxPeople=" + maxPeople + ", price="
				+ price + "]";
	}
}
