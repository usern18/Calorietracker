public class HarrisBenedictStrategy implements CalorieCalculationStrategy {
    @Override
    public double calculateCalories(double weight, double height, int age, boolean isMale) {
        if (isMale) {
            return 88.36 + (13.4 * weight) + (4.8 * height) - (5.7 * age);
        } else {
            return 447.6 + (9.2 * weight) + (3.1 * height) - (4.3 * age);
        }
    }
}