package com.gdpass.data.mining.config;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.davidmoten.rx.jdbc.ConnectionProvider;
import org.davidmoten.rx.jdbc.Database;
import org.davidmoten.rx.jdbc.pool.DatabaseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfig {
    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    @Value("${spring.datasource.driverClassName}")
    private String driverClass;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Bean(name="dmDatabase")
    @Primary
    public Database dmDatabase() throws SQLException, ClassNotFoundException {
        logger.info("driverClass: " + driverClass);
        Class.forName(driverClass);
        return Database
            .nonBlocking()
            .maxPoolSize(Runtime.getRuntime().availableProcessors() * 5)
            .maxIdleTime(30, TimeUnit.MINUTES)
            .healthCheck(DatabaseType.ORACLE)
            .idleTimeBeforeHealthCheck(5, TimeUnit.SECONDS)
            .connectionRetryInterval(30, TimeUnit.SECONDS)
            .connectionProvider(ConnectionProvider.from(url, username, password))
            .build();
    }
}
