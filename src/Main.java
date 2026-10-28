
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("=== Облік спожитих калорій ===");

            System.out.print("Введіть кількість продуктів: ");
            int n = Integer.parseInt(scanner.nextLine().trim());

            Food[] foods = new Food[n];

            for (int i = 0; i < n; i++) {
                System.out.println("\nПродукт №" + (i + 1));

                System.out.print("Назва: ");
                String name = scanner.nextLine();

                double weight = readDouble(scanner, "Вага (г): ");
                double calories = readDouble(scanner, "Калорії на 100 г: ");
                double protein = readDouble(scanner, "Білки на 100 г: ");

                System.out.print(
                        "Тип прийому їжі (1-Сніданок, 2-Обід, 3-Вечеря, 4-Перекус): "
                );
                int mealType = Integer.parseInt(scanner.nextLine().trim());

                System.out.println("Категорія продукту:");
                System.out.println("1 — Приготована страва");
                System.out.println("2 — Фасований продукт");
                System.out.println("3 — Домашня страва");
                System.out.print("Ваш вибір: ");
                int category = Integer.parseInt(scanner.nextLine().trim());

                switch (category) {
                    case 1 -> {
                        System.out.print("Спосіб приготування: ");
                        String method = scanner.nextLine();

                        foods[i] = new PreparedFood(
                                name, weight, calories, protein,
                                mealType, method
                        );
                    }

                    case 2 -> {
                        System.out.print("Бренд: ");
                        String brand = scanner.nextLine();

                        foods[i] = new PackagedFood(
                                name, weight, calories, protein,
                                mealType, brand
                        );
                    }

                    case 3 -> {
                        System.out.print("Спосіб приготування: ");
                        String method = scanner.nextLine();

                        System.out.print("Назва рецепта: ");
                        String recipeName = scanner.nextLine();

                        System.out.print("Опис рецепта: ");
                        String recipeDescription = scanner.nextLine();

                        Recipe recipe = new Recipe(
                                recipeName, recipeDescription
                        );

                        foods[i] = new HomemadeDish(
                                name, weight, calories, protein,
                                mealType, method, recipe
                        );
                    }

                    default -> {
                        System.out.println(
                                "Невідома категорія. Створюємо приготовану страву."
                        );

                        foods[i] = new PreparedFood(
                                name, weight, calories, protein, mealType
                        );
                    }
                }
            }

            System.out.println("\n=== Продукти до сортування ===");
            printFoods(foods);

            bubbleSortByCalories(foods);

            System.out.println(
                    "\n=== Продукти після сортування за калорійністю ==="
            );
            printFoods(foods);

            countHighCalorie(foods);
            searchByName(scanner, foods);
            demonstrateInstanceof(foods);
            demonstrateSwitch(foods);
            testEqualsAndHashCode();
            testEqualsSymmetry();
            testHomemadeDishEquals();
        }
    }

    private static double readDouble(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);

            try {
                return Double.parseDouble(
                        scanner.nextLine().trim().replace(',', '.')
                );
            } catch (NumberFormatException e) {
                System.out.println("Помилка! Введіть число.");
            }
        }
    }

    private static void printFoods(Food[] foods) {
        for (Food food : foods) {
            System.out.println(food);
        }
    }

    private static void bubbleSortByCalories(Food[] foods) {
        for (int i = 0; i < foods.length - 1; i++) {
            for (int j = 0; j < foods.length - i - 1; j++) {
                if (foods[j].getTotalCalories()
                        > foods[j + 1].getTotalCalories()) {
                    Food temp = foods[j];
                    foods[j] = foods[j + 1];
                    foods[j + 1] = temp;
                }
            }
        }
    }

    private static void countHighCalorie(Food[] foods) {
        int count = 0;

        for (Food food : foods) {
            if (food.getTotalCalories() > 400) {
                count++;
            }
        }

        System.out.println(
                "\nКількість продуктів із калорійністю понад 400 ккал: "
                        + count
        );
    }

    private static void searchByName(Scanner scanner, Food[] foods) {
        System.out.print("\nВведіть назву продукту для пошуку: ");
        String name = scanner.nextLine();

        boolean found = false;

        for (Food food : foods) {
            if (food.getMealName().equalsIgnoreCase(name)) {
                System.out.println("Знайдено продукт:");
                System.out.println(food);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Продукт не знайдено.");
        }
    }

    private static void demonstrateInstanceof(Food[] foods) {
        System.out.println("\n=== Перевірка типів через instanceof ===");

        for (Food food : foods) {
            if (food instanceof HomemadeDish dish) {
                System.out.println(
                        dish.getMealName()
                                + " — домашня страва, рецепт: "
                                + dish.getRecipe().getName()
                                + "; опис: "
                                + dish.getRecipe().getDescription()
                );
            } else if (food instanceof PreparedFood prepared) {
                System.out.println(
                        prepared.getMealName()
                                + " — приготована страва, спосіб: "
                                + prepared.getPreparationMethod()
                );
            } else if (food instanceof PackagedFood packaged) {
                System.out.println(
                        packaged.getMealName()
                                + " — фасований продукт, бренд: "
                                + packaged.getBrand()
                );
            }
        }
    }

    private static void demonstrateSwitch(Food[] foods) {
        System.out.println("\n=== Перевірка типів через switch ===");

        for (Food food : foods) {
            String description = switch (food) {
                case HomemadeDish dish ->
                        "Домашня страва: "
                                + dish.getRecipe().getName();

                case PreparedFood prepared ->
                        "Приготована страва: "
                                + prepared.getPreparationMethod();

                case PackagedFood packaged ->
                        "Фасований продукт: "
                                + packaged.getBrand();
            };

            System.out.println(description);
        }
    }

    private static void testEqualsAndHashCode() {
        PreparedFood food1 = new PreparedFood(
                "Вівсянка", 200, 150, 5, 1, "Варіння"
        );

        PreparedFood food2 = new PreparedFood(
                "Вівсянка", 200, 150, 5, 1, "Варіння"
        );

        PreparedFood food3 = new PreparedFood(
                "Вівсянка", 200, 150, 5, 1, "Запікання"
        );

        System.out.println("\n=== Тест equals() і hashCode() ===");

        System.out.println(
                "food1 дорівнює food2: " + food1.equals(food2)
        );

        System.out.println(
                "Однаковий hashCode: "
                        + (food1.hashCode() == food2.hashCode())
        );

        System.out.println(
                "food1 дорівнює food3: " + food1.equals(food3)
        );
    }

    private static void testEqualsSymmetry() {
        System.out.println("\n=== Тест симетрії equals() ===");

        Food baseReference1 = new PreparedFood(
                "Рис", 150, 130, 2.7, 2, "Варіння"
        );

        Food baseReference2 = new PreparedFood(
                "Рис", 150, 130, 2.7, 2, "Варіння"
        );

        boolean firstComparison =
                baseReference1.equals(baseReference2);

        boolean secondComparison =
                baseReference2.equals(baseReference1);

        System.out.println(
                "Порівняння A.equals(B): " + firstComparison
        );

        System.out.println(
                "Порівняння B.equals(A): " + secondComparison
        );

        System.out.println(
                "Симетрія дотримується: "
                        + (firstComparison == secondComparison)
        );

        System.out.println("\n=== Порівняння різних рівнів ієрархії ===");

        Food prepared = new PreparedFood(
                "Рис", 150, 130, 2.7, 2, "Варіння"
        );

        HomemadeDish homemade = new HomemadeDish(
                "Рис", 150, 130, 2.7, 2,
                "Варіння",
                new Recipe(
                        "Рисовий гарнір",
                        "Рис, вода та сіль"
                )
        );

        System.out.println(
                "PreparedFood.equals(HomemadeDish): "
                        + prepared.equals(homemade)
        );

        System.out.println(
                "HomemadeDish.equals(PreparedFood): "
                        + homemade.equals(prepared)
        );
    }

    private static void testHomemadeDishEquals() {
        System.out.println("\n=== Тест equals() для домашніх страв ===");

        HomemadeDish dish1 = new HomemadeDish(
                "Рис", 150, 130, 2.7, 2,
                "Варіння",
                new Recipe("Рисовий гарнір", "Рис, вода та сіль")
        );

        HomemadeDish dish2 = new HomemadeDish(
                "Рис", 150, 130, 2.7, 2,
                "Варіння",
                new Recipe("Рисовий гарнір", "Рис, вода та сіль")
        );

        HomemadeDish dish3 = new HomemadeDish(
                "Рис", 150, 130, 2.7, 2,
                "Варіння",
                new Recipe("Рисова каша", "Рис і молоко")
        );

        System.out.println(
                "Однакові домашні страви: " + dish1.equals(dish2)
        );

        System.out.println(
                "Однаковий hashCode: "
                        + (dish1.hashCode() == dish2.hashCode())
        );

        System.out.println(
                "Страви з різними рецептами рівні: "
                        + dish1.equals(dish3)
        );
    }
}
