
package lab7;

import lab7.exception.EntityNotFoundException;
import lab7.model.Food;
import lab7.model.Recipe;
import lab7.service.FoodCrudService;
import lab7.service.InMemoryCrudService;
import lab7.service.CRUDService;

import java.util.List;

public class Lab7Main {

    public static void main(String[] args) {

        System.out.println("===== LABORATORY WORK 7-9 =====");
        System.out.println("Облік спожитих калорій\n");

        // 1. Створення сервісу для страв
        FoodCrudService foodService = new FoodCrudService();

        // 2. Додавання тестових страв
        Food food1 = new Food(
                1, "Борщ", 300, 50, 2.5, 2
        );

        Food food2 = new Food(
                2, "Борщ", 400, 70, 3.0, 2
        );

        Food food3 = new Food(
                3, "Салат", 200, 80, 1.5, 3
        );

        Food food4 = new Food(
                4, "Каша", 250, 110, 4.0, 1
        );

        foodService.save(food1);
        foodService.save(food2);
        foodService.save(food3);
        foodService.save(food4);

        // 3. Виведення всіх записів
        System.out.println("1. Усі додані страви:");
        CRUDService.printAll(foodService.findAll());

        System.out.println("\nКількість записів: "
                + foodService.findAll().size());

        // 4. Оновлення наявної страви
        System.out.println("\n2. Оновлення страви:");

        Food updatedFood = new Food(
                1, "Борщ", 350, 60, 2.8, 2
        );

        foodService.save(updatedFood);

        System.out.println("Оновлений запис:");
        System.out.println(foodService.findById(1));

        System.out.println("Кількість після оновлення: "
                + foodService.findAll().size());

        // 5. Пошук за ID
        System.out.println("\n3. Пошук страви за ID:");

        Food foundFood = foodService.findById(2);
        System.out.println(foundFood);

        // 6. Обробка винятку
        System.out.println("\n4. Пошук неіснуючого ID:");

        try {
            foodService.findById(100);
        } catch (EntityNotFoundException exception) {
            System.out.println("Помилка: " + exception.getMessage());
            System.out.println("ID: " + exception.getId());
        }

        // 7. Успішне видалення
        System.out.println("\n5. Успішне видалення:");

        boolean deleted = foodService.delete(3);
        System.out.println("Видалення ID 3: " + deleted);

        // 8. Неуспішне видалення
        System.out.println("\n6. Неуспішне видалення:");

        boolean notDeleted = foodService.delete(100);
        System.out.println("Видалення ID 100: " + notDeleted);

        // 9. Виведення до сортування
        System.out.println("\n7. Дані перед сортуванням:");

        List<Food> beforeSorting = foodService.findAll();
        CRUDService.printAll(beforeSorting);

        // 10. Сортування за назвою
        System.out.println("\n8. Сортування за назвою:");

        List<Food> sortedByName =
                foodService.findAllSortedByName();

        CRUDService.printAll(sortedByName);

        // 11. Сортування за назвою та калорійністю
        System.out.println("\n9. Сортування за назвою та калорійністю:");

        List<Food> sortedByNameAndCalories =
                foodService.findAllSortedByNameAndByCalories();

        CRUDService.printAll(sortedByNameAndCalories);

        // 12. Перевірка, що сховище не змінилося
        System.out.println("\n10. Перевірка незмінності сховища:");

        List<Food> afterSorting = foodService.findAll();

        System.out.println("Дані у сховищі після сортування:");
        CRUDService.printAll(afterSorting);

        System.out.println("Склад сховища не змінився: "
                + beforeSorting.equals(afterSorting));

        // 13. Демонстрація роботи з другою сутністю
        System.out.println("\n11. Робота з Recipe:");

        InMemoryCrudService<Recipe> recipeService =
                new InMemoryCrudService<>();

        Recipe recipe1 = new Recipe(
                1, "Овочевий суп", "Суп із сезонних овочів"
        );

        Recipe recipe2 = new Recipe(
                2, "Каша", "Каша з молоком"
        );

        recipeService.save(recipe1);
        recipeService.save(recipe2);

        CRUDService.printAll(recipeService.findAll());

        System.out.println("\nСортування рецептів за назвою:");
        CRUDService.printAll(recipeService.findAllSortedByName());

        // 14. Демонстрація equals і hashCode
        System.out.println("\n12. Перевірка equals та hashCode:");

        Food sameIdFood = new Food(
                1, "Інша назва", 100, 20, 1.0, 1
        );

        System.out.println("Однаковий ID: "
                + foodService.findById(1).equals(sameIdFood));

        System.out.println("Однаковий hashCode: "
                + (foodService.findById(1).hashCode()
                == sameIdFood.hashCode()));

        System.out.println("\n===== PROGRAM FINISHED =====");
    }
}
