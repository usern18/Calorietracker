public class MiffStJeorStrategy implements CalorieCalculationStrategy {
    @Override
    public double calculateCalories(double weight, double height, int age, boolean isMale) {
        double bmr = (10 * weight) + (6.25 * height) - (5 * age);
        return isMale ? (bmr + 5) : (bmr - 161);
    }
}