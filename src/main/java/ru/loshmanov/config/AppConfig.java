package ru.loshmanov.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan("ru.loshmanov.service")
@EnableJpaRepositories("ru.loshmanov.repository")
@Import(DataSourceConfig.class)
public class AppConfig {

}
