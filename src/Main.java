import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(System.in);
            System.out.println("=== Консольний калькулятор обліку калорій (ЛР3 — обробка винятків) ===");

            int n = readPositiveInt(scanner, "Введіть кількість страв для обліку масиву: ");

            Food[] foods = new Food[n];

            for (int i = 0; i < n; i++) {
                System.out.println("\n--- Введення даних для страви #" + (i + 1) + " ---");
                foods[i] = readFood(scanner);
            }

            System.out.println("\n=== Список страв до сортування ===");
            printFoods(foods);

            bubbleSortByCalories(foods);

            System.out.println("\n=== Список страв після сортування за калоріями ===");
            printFoods(foods);

            int countOver400 = countHighCalorie(foods);
            System.out.println("\nПідсумок: Кількість страв із загальною калорійністю понад 400 ккал: " + countOver400);

            searchSample(scanner, foods);

        } catch (DomainException e) {
            // Базовий catch перехоплює всі підтипи DomainException
            System.err.println("Доменна помилка (базовий DomainException): " + e.getMessage());
            if (e.getFieldName() != null) {
                System.err.println("  Поле, що спричинило помилку: " + e.getFieldName());
            }
        } catch (NumberFormatException e) {
            System.err.println("Помилка формату числа: введено некоректне числове значення. " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Некоректний аргумент: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Непередбачена помилка: " + e.getMessage());
        } finally {
              if (scanner != null) {
                scanner.close();
            }
            System.out.println("\n=== Робота програми завершена (блок finally) ===");
        }
    }

    private static int readPositiveInt(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String line = scanner.nextLine().trim();
                int value = Integer.parseInt(line);
                if (value <= 0) {
                    throw new IllegalArgumentException("Кількість страв повинна бути додатною");
                }
                return value;
            } catch (NumberFormatException e) {
                System.err.println("Помилка: очікується ціле число. Спробуйте ще раз.");
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage() + ". Спробуйте ще раз.");
            }
        }
    }

    private static Food readFood(Scanner scanner) throws DomainException {
        try {
            System.out.print("Введіть назву страви (String): ");
            String mealName = scanner.nextLine();

            System.out.print("Введіть вагу порції у грамах (double): ");
            double weightGrams = parseDouble(scanner.nextLine());

            System.out.print("Введіть калорійність на 100 г продукту (double): ");
            double caloriesPer100g = parseDouble(scanner.nextLine());

            System.out.print("Введіть кількість білків на 100 г (double): ");
            double proteinPer100g = parseDouble(scanner.nextLine());

            System.out.print("Виберіть тип прийому їжі (1 - Сніданок, 2 - Обід, 3 - Вечеря, 4 - Перекус) (int): ");
            int mealTypeInt = Integer.parseInt(scanner.nextLine().trim());

             return new Food(mealName, weightGrams, caloriesPer100g, proteinPer100g, mealTypeInt);

        } catch (NumberFormatException e) {
            System.err.println("[локальна обробка] Невірний формат числа при введенні страви.");
            throw e;
        } catch (InvalidWeightException e) {
            System.err.println("[локальна обробка] Некоректна вага: " + e.getInvalidValue() + " г");
            throw e;
        } catch (InvalidCaloriesException e) {
            System.err.println("[локальна обробка] Некоректна калорійність: " + e.getInvalidValue());
            throw e;
        } catch (InvalidMealTypeException e) {
            System.err.println("[локальна обробка] Некоректний тип прийому їжі: " + e.getInvalidValue());
            throw e;
        } catch (DomainException e) {
             System.err.println("[локальна обробка] Доменна помилка: " + e.getMessage());
            throw e;
        }
    }

    private static double parseDouble(String input) {
        return Double.parseDouble(input.trim().replace(',', '.'));
    }

    private static void printFoods(Food[] foods) {
        for (Food food : foods) {
            System.out.println(food);
        }
    }

    private static void bubbleSortByCalories(Food[] foods) {
        for (int i = 0; i < foods.length - 1; i++) {
            for (int j = 0; j < foods.length - 1 - i; j++) {
                if (foods[j].getTotalCalories() > foods[j + 1].getTotalCalories()) {
                    Food temp = foods[j];
                    foods[j] = foods[j + 1];
                    foods[j + 1] = temp;
                }
            }
        }
    }

    private static int countHighCalorie(Food[] foods) {
        int count = 0;
        for (Food food : foods) {
            if (food.getTotalCalories() > 400) {
                count++;
            }
        }
        return count;
    }

     private static void searchSample(Scanner scanner, Food[] foods) {
        System.out.println("\n=== Пошук страви в масиві за зразком ===");
        try {
            System.out.print("Введіть назву страви для пошуку: ");
            String sName = scanner.nextLine();
            System.out.print("Вага порції: ");
            double sWeight = parseDouble(scanner.nextLine());
            System.out.print("Калорійність на 100г: ");
            double sCal100 = parseDouble(scanner.nextLine());
            System.out.print("Білки на 100г: ");
            double sProt100 = parseDouble(scanner.nextLine());
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
        } catch (InvalidWeightException | InvalidCaloriesException | InvalidMealTypeException e) {
            System.err.println("Помилка валідації зразка для пошуку: " + e.getMessage());
            if (e instanceof InvalidWeightException) {
                System.err.println("  Некоректне значення ваги: " + ((InvalidWeightException) e).getInvalidValue());
            } else if (e instanceof InvalidCaloriesException) {
                System.err.println("  Некоректне значення калорій: " + ((InvalidCaloriesException) e).getInvalidValue());
            } else if (e instanceof InvalidMealTypeException) {
                System.err.println("  Некоректний тип: " + ((InvalidMealTypeException) e).getInvalidValue());
            }
        } catch (DomainException e) {
            System.err.println("Доменна помилка при створенні зразка: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Помилка формату числа при введенні зразка для пошуку.");
        }
    }
}