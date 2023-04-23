package com.keydoorhotel.configuration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

@Configuration
public class SpringMVCConfiguration implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/admin").setViewName("admin/admin.html");
		registry.addViewController("/login").setViewName("login.html");
		registry.addViewController("/").setViewName("home.html");
		registry.addViewController("/location").setViewName("location");
		registry.addViewController("/photos").setViewName("photos");
		registry.addViewController("/services").setViewName("services");
		registry.addViewController("/rooms/single").setViewName("roomType/single");
		registry.addViewController("/rooms/double").setViewName("roomType/double");
		registry.addViewController("/rooms/double-eco").setViewName("roomType/double-eco");
		registry.addViewController("/rooms/triple").setViewName("roomType/triple");
	}

	@Bean
	public SpringResourceTemplateResolver templateResolver(ApplicationContext applicationContext) {
		SpringResourceTemplateResolver bean = new SpringResourceTemplateResolver();
		bean.setApplicationContext(applicationContext);
		bean.setPrefix("/WEB-INF/views/");
		bean.setSuffix(".html");
		return bean;
	}
}
