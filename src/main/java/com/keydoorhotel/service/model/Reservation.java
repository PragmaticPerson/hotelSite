package com.keydoorhotel.service.model;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.keydoorhotel.service.dto.MobiscrollTimelineDTO;

@Entity
@Table(name = "reservation")
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "room_reservation", joinColumns = { @JoinColumn(name = "reservation_id") }, inverseJoinColumns = {
			@JoinColumn(name = "room_id") })
	private List<Room> rooms;

	@Column(name = "adult_count")
	private int adultCount;

	@Column(name = "child_count")
	private int childCount;

	@Column(name = "settling")
	private LocalDate settling;

	@Column(name = "eviction")
	private LocalDate eviction;

	@Column(name = "total_price")
	private int totalPrice;

	@Transient
	private int dateDiff;

	public Reservation() {
	}

	public Reservation(int id, User user, List<Room> rooms, int adultCount, int childCount, LocalDate settling,
			LocalDate eviction, int totalPrice) {
		super();
		this.id = id;
		this.user = user;
		this.rooms = rooms;
		this.adultCount = adultCount;
		this.childCount = childCount;
		this.settling = settling;
		this.eviction = eviction;
		this.totalPrice = totalPrice;
	}

	public List<MobiscrollTimelineDTO> convertToMobiscrollTimelineDTOs() {
		List<MobiscrollTimelineDTO> data = new ArrayList<>();

		for (Room r : getRooms()) {
			var timeline = new MobiscrollTimelineDTO();
			timeline.setEnd(getEviction().atTime(12, 0));
			timeline.setStart(getSettling().atTime(14, 0));
			timeline.setResource(r.getId());
			timeline.setTitle(getUser().getSurname());

			data.add(timeline);
		}
		return data;
	}

	public int getDurationInDates() {
		return (int) DAYS.between(settling, eviction);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
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

	public LocalDate getSettling() {
		return settling;
	}

	public void setSettling(LocalDate settling) {
		this.settling = settling;
	}

	public LocalDate getEviction() {
		return eviction;
	}

	public void setEviction(LocalDate eviction) {
		this.eviction = eviction;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void addTotalPrice(int price) {
		totalPrice += price;
	}

	public int getDateDiff() {
		return dateDiff;
	}

	public void setDateDiff(int dateDiff) {
		this.dateDiff = dateDiff;
	}

	@Override
	public int hashCode() {
		return Objects.hash(adultCount, childCount, eviction, id, rooms, settling, totalPrice, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reservation other = (Reservation) obj;
		return adultCount == other.adultCount && childCount == other.childCount
				&& Objects.equals(eviction, other.eviction) && id == other.id && Objects.equals(rooms, other.rooms)
				&& Objects.equals(settling, other.settling) && totalPrice == other.totalPrice
				&& Objects.equals(user, other.user);
	}

	@Override
	public String toString() {
		return "Reservation [id=" + id + ", user=" + user + ", rooms=" + rooms + ", adultCount=" + adultCount
				+ ", childCount=" + childCount + ", settling=" + settling + ", eviction=" + eviction + ", totalPrice="
				+ totalPrice + "]";
	}
}
