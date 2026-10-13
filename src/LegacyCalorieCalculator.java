class LegacyCalorieCalculator {
    public double computeImperialScore(double weightInOunces, double caloriesPer100g) {
       return (weightInOunces * 0.035) * (caloriesPer100g / 10);
    }
}