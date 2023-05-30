package com.keydoorhotel.service.dto;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

import com.keydoorhotel.service.formatter.DateFormatter;
import com.keydoorhotel.service.model.Reservation;
import com.keydoorhotel.service.model.User;

public class OrderDTO {

	private String dates;
	private int adultCount;
	private int childCount;
	private String[] roomTypes;
	private User user;

	public OrderDTO() {
		super();
	}

	public OrderDTO(String dates, int adultCount, int childCount, String[] roomTypes, User user) {
		super();
		this.dates = dates;
		this.adultCount = adultCount;
		this.childCount = childCount;
		this.roomTypes = roomTypes;
		this.user = user;
	}

	public Reservation getReservation() {
		Reservation reservation = new Reservation();
		String[] splittedDates = dates.split("-");
		reservation.setSettling(DateFormatter.getDate(splittedDates[0]));
		reservation.setEviction(DateFormatter.getDate(splittedDates[1]));
		reservation.setAdultCount(adultCount);
		reservation.setChildCount(childCount);
		reservation.setUser(user);
		reservation.setTotalPrice(0);

		return reservation;
	}

	public int getDurationInDates() {
		String[] splittedDates = dates.split("-");
		LocalDate startDate = DateFormatter.getDate(splittedDates[0]);
		LocalDate endDate = DateFormatter.getDate(splittedDates[1]);
		return (int) DAYS.between(startDate, endDate);
	}

	public String getDates() {
		return dates;
	}

	public void setDates(String dates) {
		this.dates = dates;
	}

	public int getAdultCount() {
		return adultCount;
	}

	public void setAdultCount(int adultCount) {
		this.adultCount = adultCount;
	}

	public int getChildCount() {
		return childCount;
	}

	public void setChildCount(int childCount) {
		this.childCount = childCount;
	}

	public String[] getRoomTypes() {
		return roomTypes;
	}

	public void setRoomTypes(String[] rooms) {
		this.roomTypes = rooms;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(roomTypes);
		result = prime * result + Objects.hash(adultCount, childCount, dates, user);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderDTO other = (OrderDTO) obj;
		return adultCount == other.adultCount && childCount == other.childCount && Objects.equals(dates, other.dates)
				&& Arrays.equals(roomTypes, other.roomTypes) && Objects.equals(user, other.user);
	}

	@Override
	public String toString() {
		return "OrderDTO [dates=" + dates + ", adultCount=" + adultCount + ", childCount=" + childCount + ", rooms="
				+ Arrays.toString(roomTypes) + ", user=" + user + "]";
	}

}
