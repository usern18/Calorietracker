
package com.calorietracker.lr10.service;

import com.calorietracker.lr10.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlInjectionDemo {

    public static void run() throws SQLException {

        System.out.println("\n=== SQL INJECTION DEMO ===");

        String maliciousInput = "' OR '1'='1";

        String unsafeSql =
                "SELECT COUNT(*) FROM lr10_foods " +
                        "WHERE meal_name = '" + maliciousInput + "'";

        System.out.println("Небезпечна конкатенація SQL:");
        System.out.println(unsafeSql);
        System.out.println(
                "Такий запит може повернути записи, " +
                        "які не відповідають заданій назві."
        );

        String safeSql =
                "SELECT COUNT(*) FROM lr10_foods " +
                        "WHERE meal_name = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(safeSql)) {

            statement.setString(1, maliciousInput);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println(
                            "PreparedStatement: знайдено "
                                    + resultSet.getInt(1) + " записів."
                    );
                }
            }
        }

        System.out.println(
                "PreparedStatement обробляє введення як дані, " +
                        "а не як частину SQL-команди."
        );
    }
}
