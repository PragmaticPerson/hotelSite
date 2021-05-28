package com.keydoorhotel.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.keydoorhotel.service.dto.OrderDTO;
import com.keydoorhotel.service.model.Client;
import com.keydoorhotel.service.model.Room;
import com.keydoorhotel.service.services.ClientService;
import com.keydoorhotel.service.services.ReservationService;
import com.keydoorhotel.service.services.RoomService;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(BookController.class)
@Import(TemplateResolverConfiguration.class)
class BookControllerTest {

    @MockBean
    private ReservationService reservationService;
    @MockBean
    private ClientService clientService;
    @MockBean
    private RoomService roomService;

    @InjectMocks
    private BookController controller;

    @Autowired
    private MockMvc mvc;

    @Test
    void getBookPageGetRequestTest() throws Exception {
        mvc.perform(get("/book")).andExpect(model().attributeExists("order")).andExpect(view().name("book"));
    }

    @Test
    void fromBookPagePostRequestTestWhenAttributeValid() throws Exception {
        Client client = new Client("Name", "Surname", "8989676767", "address@mail.com");
        List<Room> rooms = Arrays.asList(new Room("Single", "single", 1, 3000));
        OrderDTO order = new OrderDTO("05.08.2020-10.08.2020", 1, client, rooms);
        mvc.perform(post("/book").flashAttr("order", order)).andExpect(redirectedUrl("/"));

        verify(clientService).save(any());
        verify(reservationService).save(any());
    }

    @Test
    void fromBookPagePostRequestTestWhenAttributeNotValid() throws Exception {
        Client client = new Client("", "", "", "");
        List<Room> rooms = Arrays.asList(new Room("Single", "single", 1, 3000));
        OrderDTO order = new OrderDTO("05.08.2020-10.08.2020", 100, client, rooms);
        mvc.perform(post("/book").flashAttr("order", order)).andExpect(model().attributeExists("error"))
                .andExpect(view().name("book"));
    }

    @Test
    void getAvaliableRoomsByDateGetRequestTestWhenRightInput() throws Exception {
        List<Room> rooms = Arrays.asList(new Room("Single", "single", 1, 3000));
        when(roomService.findRoomsByDate(any(), any())).thenReturn(rooms);
        mvc.perform(get("/api/rooms/05.04.2020/09.04.2020")).andExpect(model().attributeExists("order"))
                .andExpect(model().attributeExists("roomsList")).andExpect(model().attribute("roomsList", rooms));
    }
}
