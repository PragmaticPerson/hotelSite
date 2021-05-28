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
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client people;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "room_reservation", joinColumns = { @JoinColumn(name = "reservation_id") }, inverseJoinColumns = {
            @JoinColumn(name = "room_id") })
    private List<Room> rooms;

    @Column(name = "people_count")
    private int peopleCount;

    @Column(name = "settling")
    private LocalDate settling;

    @Column(name = "eviction")
    private LocalDate eviction;

    public Reservation() {
    }

    public Reservation(Client people, List<Room> rooms, int peopleCount, LocalDate start, LocalDate end) {
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

    public Client getPeople() {
        return people;
    }

    public void setPeople(Client people) {
        this.people = people;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
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
