package com.keydoorhotel.service.dto;

public class ClientCountDTO {

	private int adult;
	private int child;

	public ClientCountDTO() {
		super();
	}

	public ClientCountDTO(int adult, int child) {
		super();
		this.adult = adult;
		this.child = child;
	}

	public int getAdult() {
		return adult;
	}

	public void setAdult(int adult) {
		this.adult = adult;
	}

	public int getChild() {
		return child;
	}

	public void setChild(int child) {
		this.child = child;
	}

}