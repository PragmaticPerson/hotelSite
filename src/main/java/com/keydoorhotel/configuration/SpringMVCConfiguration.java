package com.keydoorhotel.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringMVCConfiguration implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home.html");
        registry.addViewController("/location").setViewName("location");
        registry.addViewController("/photos").setViewName("photos");
        registry.addViewController("/services").setViewName("services");
        registry.addViewController("/rooms").setViewName("rooms");
        registry.addViewController("/rooms/single").setViewName("roomType/single");
        registry.addViewController("/rooms/double").setViewName("roomType/double");
        registry.addViewController("/rooms/double-eco").setViewName("roomType/double-eco");
        registry.addViewController("/rooms/triple").setViewName("roomType/triple");
    }
}
