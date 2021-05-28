package com.keydoorhotel.service.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
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

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "people_id", referencedColumnName = "id")
    private People people;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "rooms_orders", joinColumns = { @JoinColumn(name = "orders_id") }, inverseJoinColumns = {
            @JoinColumn(name = "rooms_id") })
    private List<Rooms> rooms;

    @Column(name = "people_count")
    private int peopleCount;

    @Column(name = "settling")
    private LocalDate settling;

    @Column(name = "eviction")
    private LocalDate eviction;

    public Order() {
    }

    public Order(People people, List<Rooms> rooms, int peopleCount, LocalDate start, LocalDate end) {
        this.people = people;
        this.rooms = rooms;
        this.peopleCount = peopleCount;
        this.settling = start;
        this.eviction = end;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(int peopleCount) {
        this.peopleCount = peopleCount;
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
}
