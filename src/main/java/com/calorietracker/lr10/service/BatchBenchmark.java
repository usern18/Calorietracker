
package com.calorietracker.lr10.service;

import com.calorietracker.lr10.database.DBConnection;
import com.calorietracker.lr10.model.Category;
import com.calorietracker.lr10.model.Food;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BatchBenchmark {

    private static final int COUNT = 100;

    public static void run() throws SQLException {

        JdbcCategoryService categoryService =
                new JdbcCategoryService();

        JdbcFoodService foodService =
                new JdbcFoodService();

        // Окрема категорія лише для тестування
        String uniqueName = "ЛР10 Batch " +
                UUID.randomUUID().toString().substring(0, 8);

        Category category = new Category(uniqueName);
        categoryService.create(category);

        int categoryId = category.getId();

        try {
            System.out.println("\n=== BATCH BENCHMARK ===");

            // Підготовка тестових страв
            List<Food> foods = new ArrayList<>();

            for (int i = 0; i < COUNT; i++) {
                foods.add(new Food(
                        "Batch страва " + i,
                        200.0,
                        120.0,
                        5.0,
                        4,
                        categoryId
                ));
            }

            // Звичайна вставка по одному запису
            long startSingle = System.nanoTime();

            for (int i = 0; i < COUNT; i++) {
                Food food = new Food(
                        "Single страва " + i,
                        200.0,
                        120.0,
                        5.0,
                        4,
                        categoryId
                );

                foodService.create(food);
            }

            long endSingle = System.nanoTime();

            // Пакетна вставка
            long startBatch = System.nanoTime();

            int[] results = foodService.insertBatch(foods);

            long endBatch = System.nanoTime();

            double singleMs =
                    (endSingle - startSingle) / 1_000_000.0;

            double batchMs =
                    (endBatch - startBatch) / 1_000_000.0;

            System.out.println(
                    "Звичайна вставка: " + COUNT + " записів"
            );

            System.out.printf(
                    "Час звичайної вставки: %.2f мс%n",
                    singleMs
            );

            System.out.println(
                    "Пакетна вставка: " + results.length + " команд"
            );

            System.out.printf(
                    "Час пакетної вставки: %.2f мс%n",
                    batchMs
            );

            if (batchMs > 0) {
                System.out.printf(
                        "Співвідношення часу single/batch: %.2f%n",
                        singleMs / batchMs
                );
            }

        } finally {
            // Видаляємо тільки записи тестової категорії
            try (Connection connection =
                         DBConnection.getConnection();
                 PreparedStatement statement =
                         connection.prepareStatement(
                                 "DELETE FROM lr10_foods " +
                                         "WHERE category_id = ?"
                         )) {

                statement.setInt(1, categoryId);
                statement.executeUpdate();
            }

            categoryService.delete(categoryId);

            System.out.println(
                    "Тестові записи Batch Benchmark видалено."
            );
        }
    }
}
