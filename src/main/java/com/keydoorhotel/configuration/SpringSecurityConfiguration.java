package com.keydoorhotel.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.keydoorhotel.service.services.UserService;

@SpringBootConfiguration
@EnableWebSecurity
public class SpringSecurityConfiguration {

	private UserService service;

	@Autowired
	public SpringSecurityConfiguration(UserService service) {
		super();
		this.service = service;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeHttpRequests((requests) -> requests
				.antMatchers("/admin").hasRole("ADMIN")
				.antMatchers("/logout").authenticated()
				.anyRequest().permitAll()
			)
			.formLogin((form) -> form
				.loginPage("/login")
				.loginProcessingUrl("/login")
				.permitAll()
			)
			.logout((logout) -> logout.permitAll());

		return http.build();		
	}

	@Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service).passwordEncoder(encoder());
    }

	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
}