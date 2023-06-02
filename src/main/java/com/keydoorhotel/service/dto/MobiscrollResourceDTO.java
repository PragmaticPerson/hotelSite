package com.keydoorhotel.service.dto;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

import com.keydoorhotel.service.model.Room;

public class MobiscrollResourceDTO {
	private int id;
	private String name;
	private String color;

	private static Queue<String> colors;

	static {
		colors = new ArrayDeque<>(Arrays.asList("#FFC300", "#FF5733", "#C70039", "#900C3F", "#581845", "#003366"));
	}

	public MobiscrollResourceDTO() {
		super();
	}

	public MobiscrollResourceDTO(Room r) {
		super();
		id = r.getId();
		name = r.getName();
		color = colors.poll();
		colors.offer(color);
	}

	public MobiscrollResourceDTO(int id, String name, String color) {
		super();
		this.id = id;
		this.name = name;
		this.color = color;
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
