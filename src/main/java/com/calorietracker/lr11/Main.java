
package com.calorietracker.lr11;

import com.calorietracker.lr11.config.JpaUtil;
import com.calorietracker.lr11.exception.EntityNotFoundException;
import com.calorietracker.lr11.model.Category;
import com.calorietracker.lr11.model.Food;
import com.calorietracker.lr11.service.FoodService;
import com.calorietracker.lr11.service.HibernateCategoryService;
import com.calorietracker.lr11.service.HibernateFoodService;

import jakarta.persistence.EntityManager;
import org.hibernate.Hibernate;
import org.hibernate.LazyInitializationException;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        HibernateFoodService foodService =
                new HibernateFoodService();

        FoodService crudService = foodService;

        HibernateCategoryService categoryService =
                new HibernateCategoryService();

        List<Integer> demoIds = new ArrayList<>();

        try {
            System.out.println(
                    "\n=== Лабораторна №11: Hibernate ORM ==="
            );

            // Знаходимо або створюємо категорії
            Category breakfast =
                    categoryService.findByName("Сніданок");

            if (breakfast == null) {
                breakfast = categoryService.create(
                        new Category("Сніданок")
                );
            }

            Category lunch =
                    categoryService.findByName("Обід");

            if (lunch == null) {
                lunch = categoryService.create(
                        new Category("Обід")
                );
            }

            // CREATE через спільний інтерфейс
            Food oatmeal = crudService.save(
                    new Food("Вівсянка", 200, 120, 4, 1, breakfast)
            );
            demoIds.add(oatmeal.getId());

            Food soup = crudService.save(
                    new Food("Суп", 300, 50, 3, 2, lunch)
            );
            demoIds.add(soup.getId());

            Food salad = crudService.save(
                    new Food("Салат", 150, 80, 2, 2, lunch)
            );
            demoIds.add(salad.getId());

            System.out.println("\n=== CREATE ===");
            System.out.println(oatmeal);
            System.out.println(soup);
            System.out.println(salad);

            // READ через інтерфейс
            System.out.println("\n=== READ ===");
            System.out.println(
                    crudService.findById(oatmeal.getId())
            );

            // UPDATE через save()
            System.out.println("\n=== UPDATE ===");

            oatmeal.setWeightGrams(250);
            crudService.save(oatmeal);

            System.out.println(
                    crudService.findById(oatmeal.getId())
            );

            // Сортування за назвою
            System.out.println(
                    "\n=== Сортування за назвою ==="
            );

            crudService.findAllSortedByName()
                    .forEach(System.out::println);

            // JPQL: сортування за назвою та калорійністю
            System.out.println(
                    "\n=== JPQL: назва та калорійність ==="
            );

            crudService.findAllSortedByNameAndByCalories()
                    .forEach(System.out::println);

            // JPQL: сортування за калоріями
            System.out.println(
                    "\n=== JPQL: сортування за калоріями ==="
            );

            foodService.sortByCaloriesDesc()
                    .forEach(System.out::println);

            // JPQL: фільтрація
            System.out.println(
                    "\n=== JPQL: фільтрація за категорією ==="
            );

            foodService.findByCategory(lunch.getId())
                    .forEach(System.out::println);

            // Пагінація
            System.out.println("\n=== Пагінація ===");

            foodService.getPage(0, 2)
                    .forEach(System.out::println);

            // Власний виняток
            System.out.println(
                    "\n=== EntityNotFoundException ==="
            );

            try {
                crudService.findById(Long.MAX_VALUE);
            } catch (EntityNotFoundException e) {
                System.out.println(
                        "Очікувана помилка: " + e.getMessage()
                );
            }

            // Двосторонній зв'язок Category -> Food
            System.out.println(
                    "\n=== Зв'язок Category -> Food ==="
            );

            try (EntityManager em =
                         JpaUtil.createEntityManager()) {

                Category managedBreakfast =
                        em.find(Category.class, breakfast.getId());

                System.out.println(
                        "Категорія: " +
                                managedBreakfast.getName()
                );

                for (Food food : managedBreakfast.getFoods()) {
                    System.out.println(
                            "Страва: " + food.getName()
                    );
                }
            }

            // Демонстрація LAZY
            demonstrateLazyInitialization();

            // Порівняння N+1 та JOIN FETCH
            demonstrateNPlusOne();

            System.out.println(
                    "\n=== Демонстрацію завершено ==="
            );

        } finally {

            // Видаляємо лише створені тестові страви
            for (Integer id : demoIds) {
                try {
                    crudService.delete((long) id);

                    System.out.println(
                            "DELETE: видалено страву ID " + id
                    );

                } catch (Exception e) {
                    System.out.println(
                            "Не вдалося видалити ID " + id +
                                    ": " + e.getMessage()
                    );
                }
            }

            JpaUtil.close();
        }
    }

    private static void demonstrateLazyInitialization() {

        System.out.println(
                "\n=== Демонстрація LazyInitializationException ==="
        );

        Category category;

        try (EntityManager em = JpaUtil.createEntityManager()) {

            category = em.createQuery(
                            "SELECT c FROM Category c ORDER BY c.id",
                            Category.class
                    )
                    .setMaxResults(1)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);

            if (category == null) {
                System.out.println("Категорій немає.");
                return;
            }

            System.out.println(
                    "Колекція завантажена: " +
                            Hibernate.isInitialized(category.getFoods())
            );
        }

        // EntityManager уже закритий
        try {
            System.out.println(
                    "Кількість страв: " +
                            category.getFoods().size()
            );

        } catch (LazyInitializationException e) {

            System.out.println(
                    "Очікувана LazyInitializationException!"
            );

            System.out.println(
                    "Причина: EntityManager уже закритий."
            );
        }
    }

    private static void demonstrateNPlusOne() {

        System.out.println(
                "\n=== Порівняння N+1 та JOIN FETCH ==="
        );

        SessionFactory sessionFactory;

        try (EntityManager em = JpaUtil.createEntityManager()) {
            sessionFactory = em.getEntityManagerFactory()
                    .unwrap(SessionFactory.class);
        }

        Statistics statistics = sessionFactory.getStatistics();
        statistics.setStatisticsEnabled(true);

        // LAZY: звичайний запит
        statistics.clear();

        try (EntityManager em = JpaUtil.createEntityManager()) {

            List<Food> foods = em.createQuery(
                    "SELECT f FROM Food f ORDER BY f.id",
                    Food.class
            ).getResultList();

            for (Food food : foods) {
                System.out.println(
                        food.getName() + " -> " +
                                food.getCategory().getName()
                );
            }
        }

        long lazyQueries =
                statistics.getPrepareStatementCount();

        System.out.println(
                "Кількість SQL-запитів з LAZY: " +
                        lazyQueries
        );

        // JOIN FETCH
        statistics.clear();

        try (EntityManager em = JpaUtil.createEntityManager()) {

            List<Food> foods = em.createQuery(
                    "SELECT f FROM Food f " +
                            "JOIN FETCH f.category ORDER BY f.id",
                    Food.class
            ).getResultList();

            for (Food food : foods) {
                System.out.println(
                        food.getName() + " -> " +
                                food.getCategory().getName()
                );
            }
        }

        long joinFetchQueries =
                statistics.getPrepareStatementCount();

        System.out.println(
                "Кількість SQL-запитів з JOIN FETCH: " +
                        joinFetchQueries
        );

        System.out.println("\nРезультат порівняння:");

        System.out.println(
                "LAZY: " + lazyQueries +
                        " | JOIN FETCH: " + joinFetchQueries
        );
    }
}
