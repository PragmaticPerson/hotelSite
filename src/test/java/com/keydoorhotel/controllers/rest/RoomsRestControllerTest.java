//package com.keydoorhotel.controllers.rest;
//
//import static org.hamcrest.Matchers.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//@ActiveProfiles("test")
//@RunWith(SpringRunner.class)
//@WebMvcTest(RoomsRestController.class)
//class RoomsRestControllerTest {
//
//    @Autowired
//    private MockMvc mvc;
//
//    @Test
//    void test() throws Exception {
//        mvc.perform(get("/api/rooms/24.05.2021/28.5.2021")).andExpect(jsonPath("$[0].header", is("Some new Room")));
//    }
//
//}
