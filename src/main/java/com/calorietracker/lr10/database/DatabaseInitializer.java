
package com.calorietracker.lr10.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    private DatabaseInitializer() {
    }

    public static void initialize() throws SQLException {

        InputStream input = DatabaseInitializer.class
                .getClassLoader()
                .getResourceAsStream("init.sql");

        if (input == null) {
            throw new IllegalStateException(
                    "Файл init.sql не знайдено");
        }

        StringBuilder sql = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(input, StandardCharsets.UTF_8))) {

            String line;

            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();

                if (trimmed.isEmpty()
                        || trimmed.startsWith("--")
                        || trimmed.startsWith("#")) {
                    continue;
                }

                sql.append(line).append("\n");
            }

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Помилка читання init.sql", e);
        }

        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()) {

            String[] commands = sql.toString().split(";");

            for (String command : commands) {
                String query = command.trim();

                if (!query.isEmpty()) {
                    statement.execute(query);
                }
            }
        }

        System.out.println("Базу даних успішно ініціалізовано!");
    }
}
