
package com.calorietracker.lr10.service;

import com.calorietracker.lr10.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FoodTransactionService {

    public void updateFoodAndCategory(
            int foodId,
            String newFoodName,
            int categoryId,
            String newCategoryName,
            boolean simulateError
    ) throws SQLException {

        String updateFood =
                "UPDATE lr10_foods SET meal_name = ? WHERE id = ?";

        String updateCategory =
                "UPDATE lr10_categories SET name = ? WHERE id = ?";

        try (Connection connection = DBConnection.getConnection()) {

            connection.setAutoCommit(false);

            try {
                // Перша зміна: оновлюємо страву
                try (PreparedStatement statement =
                             connection.prepareStatement(updateFood)) {

                    statement.setString(1, newFoodName);
                    statement.setInt(2, foodId);

                    if (statement.executeUpdate() != 1) {
                        throw new SQLException("Страву не знайдено");
                    }
                }

                // Імітуємо помилку для перевірки rollback
                if (simulateError) {
                    throw new SQLException(
                            "Тестова помилка для перевірки rollback"
                    );
                }

                // Друга зміна: оновлюємо категорію
                try (PreparedStatement statement =
                             connection.prepareStatement(updateCategory)) {

                    statement.setString(1, newCategoryName);
                    statement.setInt(2, categoryId);

                    if (statement.executeUpdate() != 1) {
                        throw new SQLException("Категорію не знайдено");
                    }
                }

                connection.commit();
                System.out.println("COMMIT: обидві зміни збережено");

            } catch (SQLException e) {
                try {
                    connection.rollback();
                    System.out.println(
                            "ROLLBACK: усі зміни транзакції скасовано"
                    );
                } catch (SQLException rollbackError) {
                    e.addSuppressed(rollbackError);
                }
                throw e;

            } finally {
                connection.setAutoCommit(true);
            }
        }
    }
}
