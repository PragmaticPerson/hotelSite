package com.keydoorhotel.service.dto;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.keydoorhotel.service.formatter.DateFormatter;
import com.keydoorhotel.service.model.Order;
import com.keydoorhotel.service.model.People;
import com.keydoorhotel.service.model.Rooms;

public class OrderDTO {
    @NotBlank(message = "Dates not valid")
    private String dates;

    @Min(value = 1, message = "Count of people must be more then 1")
    @Max(value = 8, message = "Too many people")
    private int peopleCount;

    @NotNull
    private People people;

    @NotEmpty
    private List<Rooms> rooms;

    public OrderDTO() {
    }

    public OrderDTO(String dates, int peopleCount, People people, List<Rooms> rooms) {
        this.dates = dates;
        this.peopleCount = peopleCount;
        this.people = people;
        this.rooms = rooms;
    }

    public Order getOrder() {
        Order order = new Order();
        String[] splittedDates = dates.split("-");
        order.setSettling(DateFormatter.getDate(splittedDates[0]));
        order.setEviction(DateFormatter.getDate(splittedDates[1]));
        order.setPeopleCount(peopleCount);
        order.setRooms(rooms);
        order.setPeople(people);
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

    public People getPeople() {
        return people;
    }

    public void setPeople(People people) {
        this.people = people;
    }

    public List<Rooms> getRooms() {
        return rooms;
    }

    public void setRooms(List<Rooms> rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        return "OrderDTO [dates=" + dates + ", peopleCount=" + peopleCount + ", people=" + people + ", rooms=" + rooms
                + "]";
    }

}
