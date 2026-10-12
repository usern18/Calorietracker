public interface CalorieCalculationStrategy {
    double calculateCalories(double weight, double height, int age, boolean isMale);
    default void printStrategyInfo() {
        System.out.println("Використовується стратегія розрахунку добової норми калорій.");
    }
}