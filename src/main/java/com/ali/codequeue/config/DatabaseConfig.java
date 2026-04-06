package com.ali.codequeue.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("prod")
public class DatabaseConfig {

    @Value("${PGHOST:postgres.railway.internal}")
    private String host;

    @Value("${PGPORT:5432}")
    private String port;

    @Value("${PGDATABASE:railway}")
    private String database;

    @Value("${PGUSER:postgres}")
    private String username;

    @Value("${PGPASSWORD:}")
    private String password;

    @Bean
    public DataSource dataSource() {
        String url = "jdbc:postgresql://" + host + ":" + port + "/" + database;
        return DataSourceBuilder.create()
                .url(url)
                .username(username)
                .password(password)
                .driverClassName("org.postgresql.Driver")
                .build();
    }
}