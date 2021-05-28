package com.keydoorhotel.configuration;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

@SpringBootConfiguration
@EnableJpaRepositories("com.keydoorhotel.dao")
@EntityScan("com.keydoorhotel.service.model")
public class SpringConfiguration {

    @Bean
    public SpringResourceTemplateResolver templateResolver(ApplicationContext applicationContext) {
        SpringResourceTemplateResolver bean = new SpringResourceTemplateResolver();
        bean.setApplicationContext(applicationContext);
        bean.setPrefix("/WEB-INF/views/");
        bean.setSuffix(".html");
        return bean;
    }
}
