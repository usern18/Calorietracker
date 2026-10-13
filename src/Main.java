import java.util.Scanner;

class Main {

    public static void validateAndLogFood(String name, double weight, double cal100, double prot100, int type) throws DomainException {
        System.out.printf("[Лог] Перевірка страви '%s': вага=%.1fg, ккал/100г=%.1f, білки/100г=%.1f, тип=%d%n",
                name, weight, cal100, prot100, type);
        if (weight > 2000) {
            throw new DailyLimitExceededException("Занадто велика порція для одного прийому!", (int) weight, 2000);
        }
    }

    static void main(String[] args) {
        if (args.length > 0) {
            System.out.println("Отримано аргументів запуска: " + args.length);
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Консольний калькулятор обліку калорій (ЛР3) ===");

        int n = 0;
        try {
            System.out.print("Введіть кількість страв для обліку масиву: ");
            n = Integer.parseInt(scanner.nextLine().trim());

            if (n > 0) {
                int dummyAverage = 2000 / n;
                System.out.println("Орієнтовно калорій на 1 страву при ліміті 2000: " + dummyAverage);
            }

            int[] testArray = {100, 200, 300};
            int safeIndex = Math.min(n, testArray.length - 1);
            if (safeIndex >= 0) {
                System.out.println("Тестовий елемент масиву за індексом: " + testArray[safeIndex]);
            }

        } catch (NumberFormatException e) {
            System.out.println("Помилка вводу: очікувалося ціле число для кількості страв!");
            n = 1;
        } catch (ArithmeticException e) {
            System.out.println("Помилка математичної операції: ділення на нуль.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Помилка: індекс виходить за межі тестового масиву.");
        } finally {
            System.out.println("[Finally] Блок ініціалізації та перевірки завершено.\n");
        }

        Food[] foods = new Food[n];

        for (int i = 0; i < n; i++) {
            boolean success = false;
            while (!success) {
                try {
                    System.out.println("\n--- Введення даних для страви #" + (i + 1) + " ---");

                    System.out.print("Введіть назву страви (String): ");
                    String mealName = scanner.nextLine();

                    System.out.print("Введіть вагу порції у грамах (double): ");
                    double weightGrams = Double.parseDouble(scanner.nextLine().trim().replace(',', '.'));

                    System.out.print("Введіть калорійність на 100 г продукту (double): ");
                    double caloriesPer100g = Double.parseDouble(scanner.nextLine().trim().replace(',', '.'));

                    System.out.print("Введіть кількість білків на 100 г (double): ");
                    double proteinPer100g = Double.parseDouble(scanner.nextLine().trim().replace(',', '.'));

                    System.out.print("Виберіть тип прийому їжі (1 - Сніданок, 2 - Обід, 3 - Вечеря, 4 - Перекус) (int): ");
                    int mealTypeInt = Integer.parseInt(scanner.nextLine().trim());

                    validateAndLogFood(mealName, weightGrams, caloriesPer100g, proteinPer100g, mealTypeInt);

                    foods[i] = new Food(mealName, weightGrams, caloriesPer100g, proteinPer100g, mealTypeInt);
                    success = true;

                } catch (InvalidCaloriesException e) {
                    System.out.println("[Помилка домену] Некоректні калорії/вага: " + e.getMessage());
                    System.out.println("Невалідне значення: " + e.getInvalidValue());
                    System.out.println("Час фіксування: " + e.getTimestamp() + "\n");

                } catch (DailyLimitExceededException e) {
                    System.out.println("[Помилка ліміту] " + e.getMessage());
                    System.out.println("Спроба ввести порцію: " + e.getCurrentTotal() + "г при ліміті " + e.getDailyLimit() + "г.\n");

                } catch (NumberFormatException e) {
                    System.out.println("Помилка формату чисел: вводьте коректні числові значення.\n");
                } catch (DomainException e) {
                    System.out.println("Загальна доменна помилка: " + e.getMessage() + "\n");
                }
            }
        }

        System.out.println("\n=== Список страв до сортування ===");
        for (Food food : foods) {
            System.out.println(food);
        }

        for (int i = 0; i < foods.length - 1; i++) {
            for (int j = 0; j < foods.length - 1 - i; j++) {
                if (foods[j].getTotalCalories() > foods[j + 1].getTotalCalories()) {
                    Food temp = foods[j];
                    foods[j] = foods[j + 1];
                    foods[j + 1] = temp;
                }
            }
        }

        System.out.println("\n=== Список страв після сортування за калоріями ===");
        for (Food food : foods) {
            System.out.println(food);
        }

        int countOver400 = 0;
        for (Food food : foods) {
            if (food.getTotalCalories() > 400) {
                countOver400++;
            }
        }
        System.out.println("\nПідсумок: Кількість страв із загальною калорійністю понад 400 ккал: " + countOver400);

        try {
            System.out.println("\n=== Пошук страви в масиві за зразком ===");
            System.out.print("Введіть назву страви для пошуку: ");
            String sName = scanner.nextLine();
            System.out.print("Вага порції: ");
            double sWeight = Double.parseDouble(scanner.nextLine().trim().replace(',', '.'));
            System.out.print("Калорійність на 100г: ");
            double sCal100 = Double.parseDouble(scanner.nextLine().trim().replace(',', '.'));
            System.out.print("Білки на 100г: ");
            double sProt100 = Double.parseDouble(scanner.nextLine().trim().replace(',', '.'));
            System.out.print("Тип прийому їжі (int): ");
            int sType = Integer.parseInt(scanner.nextLine().trim());

            Food sample = new Food(sName, sWeight, sCal100, sProt100, sType);
            int foundIndex = -1;

            for (int i = 0; i < foods.length; i++) {
                if (foods[i].equals(sample)) {
                    foundIndex = i;
                    break;
                }
            }

            if (foundIndex != -1) {
                System.out.println("Результат пошуку: Знайдено ідентичну страву під індексом " + foundIndex);
            } else {
                System.out.println("Результат пошуку: Ідентичної страви за введеним зразком не знайдено.");
            }
        } catch (Exception e) {
            System.out.println("Помилка під час виконання пошуку: " + e.getMessage());
        } finally {
            scanner.close();
            System.out.println("\nРоботу програми успішно завершено. Ресурси закрито.");
        }
    }
}