package com.keydoorhotel.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String getHomePageGetRequest() {
        return "home";
    }

    @GetMapping("/location")
    public String getLocationPageGetRequest() {
        return "location";
    }

    @GetMapping("/photos")
    public String getPhotosPageGetRequest() {
        return "photos";
    }

    @GetMapping("/services")
    public String getServicesPageGetRequest() {
        return "services";
    }

    @GetMapping("/rooms")
    public String getRoomsPageGetRequest() {
        return "rooms";
    }

    @GetMapping("/rooms/single")
    public String getSingleRoomPageGetRequest() {
        return "roomType/single";
    }

    @GetMapping("/rooms/double")
    public String getDoubleRoomPageGetRequest() {
        return "roomType/double";
    }

    @GetMapping("/rooms/double-eco")
    public String getDoubleEcoRoomPageGetRequest() {
        return "roomType/double-eco";
    }

    @GetMapping("/rooms/triple")
    public String getTripleRoomPageGetRequest() {
        return "roomType/triple";
    }
}
