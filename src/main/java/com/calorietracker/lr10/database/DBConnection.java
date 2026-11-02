
package com.calorietracker.lr10.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static HikariDataSource dataSource;

    private DBConnection() {
    }

    private static synchronized HikariDataSource getDataSource() {

        if (dataSource == null) {

            Properties properties = new Properties();

            // Базові налаштування з resources
            try (InputStream input =
                         DBConnection.class.getClassLoader()
                                 .getResourceAsStream("application.properties")) {

                if (input == null) {
                    throw new IllegalStateException(
                            "Файл application.properties не знайдено"
                    );
                }

                properties.load(input);

            } catch (IOException e) {
                throw new IllegalStateException(
                        "Не вдалося прочитати application.properties", e
                );
            }

            // Локальні налаштування, які не додаються до Git
            Path localConfig = Path.of("application-local.properties");

            if (Files.exists(localConfig)) {
                try (InputStream input =
                             Files.newInputStream(localConfig)) {

                    properties.load(input);

                } catch (IOException e) {
                    throw new IllegalStateException(
                            "Не вдалося прочитати application-local.properties",
                            e
                    );
                }
            }

            // За потреби змінні середовища мають найвищий пріоритет
            overrideFromEnv(properties, "DB_URL", "db.url");
            overrideFromEnv(properties, "DB_USERNAME", "db.username");
            overrideFromEnv(properties, "DB_PASSWORD", "db.password");

            HikariConfig config = new HikariConfig();

            config.setDriverClassName(
                    properties.getProperty("db.driver")
            );

            config.setJdbcUrl(
                    properties.getProperty("db.url")
            );

            config.setUsername(
                    properties.getProperty("db.username")
            );

            config.setPassword(
                    properties.getProperty("db.password")
            );

            config.setMaximumPoolSize(
                    Integer.parseInt(properties.getProperty(
                            "db.pool.maximumPoolSize", "10"))
            );

            config.setMinimumIdle(
                    Integer.parseInt(properties.getProperty(
                            "db.pool.minimumIdle", "2"))
            );

            dataSource = new HikariDataSource(config);
        }

        return dataSource;
    }

    private static void overrideFromEnv(
            Properties properties,
            String envName,
            String propertyName) {

        String value = System.getenv(envName);

        if (value != null && !value.isBlank()) {
            properties.setProperty(propertyName, value);
        }
    }

    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    public static synchronized void close() {
        if (dataSource != null) {
            dataSource.close();
            dataSource = null;
        }
    }
}
