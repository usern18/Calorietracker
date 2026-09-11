import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (var scanner = new Scanner(System.in)) {
            System.out.println("=== Консольний калькулятор обліку калорій (ЛР2) ===");

            System.out.print("Введіть кількість страв для обліку масиву: ");
            int n = Integer.parseInt(scanner.nextLine().trim());

            Food[] foods = new Food[n];

            for (int i = 0; i < n; i++) {
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

                foods[i] = new Food(mealName, weightGrams, caloriesPer100g, proteinPer100g, mealTypeInt);
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
        }
    }
}