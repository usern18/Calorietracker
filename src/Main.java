import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (var scanner = new Scanner(System.in)) {
            System.out.println("=== Консольний калькулятор обліку калорій ===");

            System.out.print("Введіть назву страви (String): ");
            String mealName = scanner.nextLine();

            System.out.print("Введіть вагу порції у грамах (double): ");
            double weightGrams = scanner.nextDouble();

            System.out.print("Введіть калорійність на 100 г продукту (double): ");
            double caloriesPer100g = scanner.nextDouble();

            System.out.print("Введіть кількість білків на 100 г (double): ");
            double proteinPer100g = scanner.nextDouble();

            System.out.print("Виберіть тип прийому їжі (1 - Сніданок, 2 - Обід, 3 - Вечеря, 4 - Перекус) (int): ");
            int mealTypeInt = scanner.nextInt();

            double totalCalories = (weightGrams * caloriesPer100g) / 100.0;
            double totalProtein = (weightGrams * proteinPer100g) / 100.0;

            String mealTypeName = switch (mealTypeInt) {
                case 1 -> "Сніданок";
                case 2 -> "Обід";
                case 3 -> "Вечеря";
                default -> "Перекус";
            };

            System.out.println("\n--- Результат розрахунку ---");
            System.out.printf("Страва: %s (%s)%n", mealName, mealTypeName);
            System.out.printf("Вага порції: %.2f г%n", weightGrams);
            System.out.printf("Загальна калорійність: %.2f ккал%n", totalCalories);
            System.out.printf("Загальна кількість білків: %.2f г%n", totalProtein);

            if (totalCalories > 600) {
                System.out.println("Висновок: Це дуже калорійна страва для одного прийому їжі.");
            } else if (totalCalories < 200) {
                System.out.println("Висновок: Легкий перекус із низькою калорійністю.");
            } else {
                System.out.println("Висновок: Помірна та збалансована порція.");
            }
        }
    }
}