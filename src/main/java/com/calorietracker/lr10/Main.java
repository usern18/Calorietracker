
package com.calorietracker.lr10;

import com.calorietracker.lr10.database.DBConnection;
import com.calorietracker.lr10.database.DatabaseInitializer;
import com.calorietracker.lr10.model.Category;
import com.calorietracker.lr10.model.Food;
import com.calorietracker.lr10.service.BatchBenchmark;
import com.calorietracker.lr10.service.FoodTransactionService;
import com.calorietracker.lr10.service.JdbcCategoryService;
import com.calorietracker.lr10.service.JdbcFoodService;
import com.calorietracker.lr10.service.SqlInjectionDemo;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        try {
            System.out.println("=== Лабораторна робота №10 ===");
            System.out.println("Maven + JDBC + MySQL");

            // 1. Автоматична ініціалізація БД
            DatabaseInitializer.initialize();

            JdbcCategoryService categoryService =
                    new JdbcCategoryService();

            JdbcFoodService foodService =
                    new JdbcFoodService();

            FoodTransactionService transactionService =
                    new FoodTransactionService();

            // 2. Виведення категорій
            System.out.println("\n=== КАТЕГОРІЇ ===");

            for (Category category : categoryService.findAll()) {
                System.out.println(
                        category.getId() + " - " + category.getName()
                );
            }

            // 3. Пошук категорії "Сніданок"
            int breakfastCategoryId = -1;

            for (Category category : categoryService.findAll()) {
                if ("Сніданок".equals(category.getName())) {
                    breakfastCategoryId = category.getId();
                    break;
                }
            }

            if (breakfastCategoryId == -1) {
                throw new IllegalStateException(
                        "Категорію 'Сніданок' не знайдено"
                );
            }

            // 4. CREATE
            System.out.println("\n=== CREATE ===");

            Food food = new Food(
                    "Вівсяна каша",
                    250.0,
                    120.0,
                    4.0,
                    1,
                    breakfastCategoryId
            );

            foodService.save(food);

            System.out.println(
                    "Додано страву з ID: " + food.getId()
            );

            // 5. READ
            System.out.println("\n=== READ ===");

            System.out.println(
                    foodService.findById(food.getId())
            );

            // 6. UPDATE
            System.out.println("\n=== UPDATE ===");

            food.setMealName("Вівсяна каша з бананом");
            foodService.save(food);

            System.out.println(
                    foodService.findById(food.getId())
            );

            // 7. SORT BY NAME
            System.out.println("\n=== SORT BY NAME ===");

            for (Food item : foodService.findAllSortedByName()) {
                System.out.println(item);
            }

            // 8. PAGINATION
            System.out.println("\n=== PAGINATION ===");

            for (Food item : foodService.findPage(5, 0)) {
                System.out.println(item);
            }

            // 9. DELETE
            System.out.println("\n=== DELETE ===");

            System.out.println(
                    "Страву видалено: "
                            + foodService.delete(food.getId())
            );

            // 10. TRANSACTIONS
            System.out.println("\n=== TRANSACTIONS ===");

            Category testCategory =
                    new Category("ЛР10 Тестова категорія");

            categoryService.create(testCategory);

            Food testFood = new Food(
                    "ЛР10 Тестова страва",
                    200.0,
                    150.0,
                    5.0,
                    4,
                    testCategory.getId()
            );

            foodService.create(testFood);

            try {
                // Успішна транзакція
                System.out.println("\n--- COMMIT ---");

                transactionService.updateFoodAndCategory(
                        testFood.getId(),
                        "ЛР10 Страва після COMMIT",
                        testCategory.getId(),
                        "ЛР10 Категорія після COMMIT",
                        false
                );

                System.out.println(
                        "Страва: "
                                + foodService.findById(testFood.getId())
                );

                System.out.println(
                        "Категорія: "
                                + categoryService.findById(
                                testCategory.getId()
                        ).getName()
                );

                // Транзакція з навмисною помилкою
                System.out.println("\n--- ROLLBACK ---");

                try {
                    transactionService.updateFoodAndCategory(
                            testFood.getId(),
                            "ЛР10 Назва, яка не має зберегтися",
                            testCategory.getId(),
                            "ЛР10 Категорія після помилки",
                            true
                    );

                } catch (SQLException e) {
                    System.out.println(
                            "Очікувана помилка: " + e.getMessage()
                    );
                }

                // Перевірка ROLLBACK
                Food afterRollback =
                        foodService.findById(testFood.getId());

                Category categoryAfterRollback =
                        categoryService.findById(
                                testCategory.getId()
                        );

                System.out.println(
                        "Страва після ROLLBACK: "
                                + afterRollback.getMealName()
                );

                System.out.println(
                        "Категорія після ROLLBACK: "
                                + categoryAfterRollback.getName()
                );

                if (!"ЛР10 Страва після COMMIT".equals(
                        afterRollback.getMealName())) {
                    throw new IllegalStateException(
                            "ROLLBACK не відновив назву страви"
                    );
                }

                if (!"ЛР10 Категорія після COMMIT".equals(
                        categoryAfterRollback.getName())) {
                    throw new IllegalStateException(
                            "ROLLBACK не відновив назву категорії"
                    );
                }

                System.out.println(
                        "Перевірка ROLLBACK пройдена успішно!"
                );

            } finally {
                // Видаляємо тестові записи транзакції
                foodService.delete(testFood.getId());
                categoryService.delete(testCategory.getId());

                System.out.println(
                        "\nТестові записи транзакції видалено."
                );
            }

            // 11. BATCH INSERT BENCHMARK
            System.out.println(
                    "\n=== ПОРІВНЯННЯ ШВИДКОСТІ ВСТАВКИ ==="
            );

            BatchBenchmark.run();

            // 12. SQL INJECTION DEMO
            SqlInjectionDemo.run();

            System.out.println(
                    "\nПрограма завершена успішно!"
            );

        } catch (Exception e) {
            System.err.println(
                    "Помилка виконання програми: "
                            + e.getMessage()
            );
            e.printStackTrace();

        } finally {
            DBConnection.close();
        }
    }
}
