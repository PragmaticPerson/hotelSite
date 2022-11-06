package com.keydoorhotel.dao;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.SpringBootDependencyInjectionTestExecutionListener;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.keydoorhotel.service.model.User;
import com.keydoorhotel.service.model.Reservation;
import com.keydoorhotel.service.model.Room;

@DataJpaTest
@Import(DataSourceConfiguration.class)
@DatabaseSetup(value = "classpath:data.xml")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestExecutionListeners({ SpringBootDependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
class ReservationRepositoryTest {

    private ReservationRepository reservationRepository;
    private static final String PATH = "classpath:dbunit/reservation/";

    @Autowired
    public ReservationRepositoryTest(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED, value = PATH + "addReservation.xml")
    void addReservationTest() {
        Room first = new Room();
        first.setId(1);
        Room second = new Room();
        second.setId(2);
        User client = new User();
        client.setId(1);

        Reservation reservation = new Reservation();
        reservation.setRooms(Arrays.asList(first, second));
        reservation.setUser(client);
        reservation.setPeopleCount(2);
        reservation.setSettling(LocalDate.parse("2019-01-17"));
        reservation.setEviction(LocalDate.parse("2019-01-31"));

        reservationRepository.saveAndFlush(reservation);
    }

    @Test
    void getNotEmptyRoomsByReservationDatesWhenDatesNotMeet() {
        List<Room> rooms = reservationRepository.findNotEmptyRoomByDate(LocalDate.parse("2020-04-10"),
                LocalDate.parse("2020-04-20"));
        assertTrue(rooms.isEmpty());
    }

    @Test
    void getNotEmptyRoomsByReservationDatesWhenActualStartDateInsiteDateRangeInDB() {
        Room room = new Room();
        room.setId(3);
        List<Room> rooms = reservationRepository.findNotEmptyRoomByDate(LocalDate.parse("2020-05-15"),
                LocalDate.parse("2020-05-30"));
        assertEquals(room.getId(), rooms.get(0).getId());
    }

    @Test
    void getNotEmptyRoomsByReservationDatesWhenActualEndDateInsiteDateRangeInDB() {
        Room room = new Room();
        room.setId(3);
        List<Room> rooms = reservationRepository.findNotEmptyRoomByDate(LocalDate.parse("2020-05-05"),
                LocalDate.parse("2020-05-15"));
        assertEquals(room.getId(), rooms.get(0).getId());
    }

    @Test
    void getNotEmptyRoomsByReservationDatesWhenDBEndDateInsiteActualDateRange() {
        Room room = new Room();
        room.setId(3);
        List<Room> rooms = reservationRepository.findNotEmptyRoomByDate(LocalDate.parse("2020-05-05"),
                LocalDate.parse("2020-05-30"));
        assertEquals(room.getId(), rooms.get(0).getId());
    }
}
