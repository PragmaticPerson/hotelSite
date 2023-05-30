package com.keydoorhotel.configuration;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.keydoorhotel.service.model.Role;
import com.keydoorhotel.service.model.User;
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
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/logout", "/account").authenticated()
				.anyRequest().permitAll()
			)
			.formLogin((form) -> form
				.loginPage("/login")
				.loginProcessingUrl("/login")
				.successHandler(customAuthenticationSuccessHandler())
				.permitAll()
			)
				.logout((logout) -> logout.permitAll().deleteCookies("JSESSIONID"));

		return http.build();
	}

	@Bean
	public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
		return new CustomUrlAuthenticationSuccessHandler();
	}

	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth, BCryptPasswordEncoder encoder) throws Exception {
		User admin = new User(0, "Alex", "Chereshnev", "89895329829", "aleksejceresnev43@yandex.ru",
				encoder.encode("adminPassword"));
		admin.setRoles(Collections.singleton(new Role(0L, "ROLE_ADMIN")));
		auth.userDetailsService(service).passwordEncoder(encoder);
		auth.inMemoryAuthentication().passwordEncoder(encoder).withUser(admin);
	}
}