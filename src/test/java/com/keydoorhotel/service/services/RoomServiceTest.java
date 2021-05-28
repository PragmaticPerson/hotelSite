package com.keydoorhotel.service.services;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.keydoorhotel.dao.RoomRepository;
import com.keydoorhotel.service.model.Room;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class RoomServiceTest {

    @Mock
    private ReservationService reservationService;
    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;

    @Test
    void orderOfCallsWhenReservationServiceReturnNotEmptyListTest() {
        when(reservationService.findOrdersIdByDate(any(), any())).thenReturn(Arrays.asList(new Room()));
        LocalDate date = LocalDate.parse("2020-01-01");
        roomService.findRoomsByDate(date, date);

        verify(reservationService).findOrdersIdByDate(any(), any());
        verify(roomRepository).findEmptyRooms(any());
    }

    @Test
    void orderOfCallsWhenReservationServiceReturnEmptyListTest() {
        LocalDate date = LocalDate.parse("2020-01-01");
        roomService.findRoomsByDate(date, date);

        verify(reservationService).findOrdersIdByDate(any(), any());
    }
}
