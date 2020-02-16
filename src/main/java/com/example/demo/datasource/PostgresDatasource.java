package com.example.demo.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class PostgresDatasource {

    @Value("${spring.datasource.hikari.jdbc-url}")
    private String dbUrl;

    @Value("${spring.datasource.hikari.password}")
    private String password;

    @Value("${spring.datasource.hikari.username}")
    private String username;

    @Primary
    @Bean
    @ConfigurationProperties("application.properties")
    public HikariDataSource hikariDataSource() {
        HikariDataSource dataSource =

            DataSourceBuilder
                .create()
                .type(HikariDataSource.class)
                .build();

        dataSource.setJdbcUrl(dbUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }
}
