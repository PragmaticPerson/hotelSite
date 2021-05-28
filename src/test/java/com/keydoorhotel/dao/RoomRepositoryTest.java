package com.keydoorhotel.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import com.keydoorhotel.service.model.Room;

@DataJpaTest
@Import(DataSourceConfiguration.class)
@DatabaseSetup(value = "classpath:data.xml")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestExecutionListeners({ SpringBootDependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
class RoomRepositoryTest {

    private RoomRepository roomRepository;
    private static final String PATH = "classpath:dbunit/room/";

    @Autowired
    public RoomRepositoryTest(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED, value = PATH + "addRoom.xml")
    void addRoomTest() {
        Room room = new Room("Test room", "test", 8, 10000);
        roomRepository.save(room);
    }

    @Test
    void getAllRoomsNotInArrayTest() {
        Room first = new Room("Single room", "single", 1, 2400);
        first.setId(1);
        Room second = new Room("Double room", "double", 2, 4500);
        second.setId(2);
        Room fourth = new Room("Triple room", "triple", 3, 6200);
        fourth.setId(4);

        List<Room> rooms = Arrays.asList(first, second, fourth);

        Room third = new Room("Double room with bunk bed", "double-eco", 2, 4000);
        third.setId(3);

        List<Room> actualRooms = roomRepository.findEmptyRooms(Arrays.asList(third));

        for (int i = 0; i < actualRooms.size(); i++) {
            assertEquals(rooms.get(i).getId(), actualRooms.get(i).getId());
            assertEquals(rooms.get(i).getTitle(), actualRooms.get(i).getTitle());
            assertEquals(rooms.get(i).getMaxPeople(), actualRooms.get(i).getMaxPeople());
            assertEquals(rooms.get(i).getPrice(), actualRooms.get(i).getPrice());
            assertEquals(rooms.get(i).getSource(), actualRooms.get(i).getSource());
        }
    }

    @Test
    void getAllRoomsTest() {
        Room first = new Room("Single room", "single", 1, 2400);
        first.setId(1);
        Room second = new Room("Double room", "double", 2, 4500);
        second.setId(2);
        Room third = new Room("Double room with bunk bed", "double-eco", 2, 4000);
        third.setId(3);
        Room fourth = new Room("Triple room", "triple", 3, 6200);
        fourth.setId(4);

        List<Room> rooms = Arrays.asList(first, second, third, fourth);

        List<Room> actualRooms = roomRepository.findAll();
        for (int i = 0; i < actualRooms.size(); i++) {
            assertEquals(rooms.get(i).getId(), actualRooms.get(i).getId());
            assertEquals(rooms.get(i).getTitle(), actualRooms.get(i).getTitle());
            assertEquals(rooms.get(i).getMaxPeople(), actualRooms.get(i).getMaxPeople());
            assertEquals(rooms.get(i).getPrice(), actualRooms.get(i).getPrice());
            assertEquals(rooms.get(i).getSource(), actualRooms.get(i).getSource());
        }
    }
}
