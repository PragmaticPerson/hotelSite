package com.keydoorhotel.service.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.keydoorhotel.service.dto.MobiscrollResourceDTO;

@Entity
@Table(name = "room")
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@ManyToOne
	@JoinColumn(name = "type_id", referencedColumnName = "id")
	private RoomType type;

	public Room() {
	}

	public Room(int id, String name, RoomType type) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
	}

	public MobiscrollResourceDTO convertToMobiscrollResourceDTO() {
		var resource = new MobiscrollResourceDTO();
		resource.setId(getId());
		resource.setName(getName());
		resource.setColor("#4981d6");

		return resource;
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

	public RoomType getType() {
		return type;
	}

	public void setType(RoomType type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, type);
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
		return id == other.id && Objects.equals(name, other.name) && Objects.equals(type, other.type);
	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", name=" + name + ", type=" + type + "]";
	}
}