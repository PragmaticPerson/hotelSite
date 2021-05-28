package com.keydoorhotel.service.dto;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.keydoorhotel.service.formatter.DateFormatter;
import com.keydoorhotel.service.model.Reservation;
import com.keydoorhotel.service.model.Client;
import com.keydoorhotel.service.model.Room;

public class OrderDTO {
    @NotBlank(message = "Dates not valid")
    private String dates;

    @Min(value = 1, message = "Count of people must be more then 1")
    @Max(value = 8, message = "Too many people")
    private int peopleCount;

    @NotNull
    private Client client;

    @NotEmpty
    private List<Room> rooms;

    public OrderDTO() {
    }

    public OrderDTO(String dates, int peopleCount, Client client, List<Room> rooms) {
        this.dates = dates;
        this.peopleCount = peopleCount;
        this.client = client;
        this.rooms = rooms;
    }

    public Reservation getReservation() {
        Reservation order = new Reservation();
        String[] splittedDates = dates.split("-");
        order.setSettling(DateFormatter.getDate(splittedDates[0]));
        order.setEviction(DateFormatter.getDate(splittedDates[1]));
        order.setPeopleCount(peopleCount);
        order.setRooms(rooms);
        order.setPeople(client);
        return order;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public int getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(int peopleCount) {
        this.peopleCount = peopleCount;
    }

    public Client getPeople() {
        return client;
    }

    public void setPeople(Client people) {
        this.client = people;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        return "OrderDTO [dates=" + dates + ", peopleCount=" + peopleCount + ", client=" + client + ", rooms=" + rooms
                + "]";
    }

}
