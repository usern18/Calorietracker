public class MealAdapter implements MealEvaluator {
    private LegacyCalorieCalculator legacyCalculator;

    public MealAdapter(LegacyCalorieCalculator legacyCalculator) {
        this.legacyCalculator = legacyCalculator;
    }

    @Override
    public String evaluate(Food food) {
        double ounces = food.getWeightGrams() / 28.3495;
        double score = legacyCalculator.computeImperialScore(ounces, food.getCaloriesPer100g());
        return "Адаптована оцінка за зовнішньою системою: " + String.format("%.2f", score);
    }
}