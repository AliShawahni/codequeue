package com.ali.codequeue.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("prod")
public class DatabaseConfig {

    @Value("${DATABASE_URL:}")
    private String databaseUrl;

    @Bean
    public DataSource dataSource() {
        String url = databaseUrl;
        if (url.startsWith("postgresql://")) {
            url = "jdbc:postgresql://" + url.substring("postgresql://".length());
        } else if (!url.startsWith("jdbc:")) {
            url = "jdbc:" + url;
        }

        // Parse username and password from URL
        // Format: jdbc:postgresql://user:password@host:port/db
        String username = "";
        String password = "";

        try {
            String withoutPrefix = url.replace("jdbc:postgresql://", "");
            String credentials = withoutPrefix.split("@")[0];
            String[] parts = credentials.split(":");
            username = parts[0];
            password = parts[1];

            // Clean URL - remove credentials from URL
            String hostAndDb = withoutPrefix.split("@")[1];
            url = "jdbc:postgresql://" + hostAndDb;
        } catch (Exception e) {
            // fallback - use URL as is
        }

        return DataSourceBuilder.create()
                .url(url)
                .username(username)
                .password(password)
                .driverClassName("org.postgresql.Driver")
                .build();
    }
}