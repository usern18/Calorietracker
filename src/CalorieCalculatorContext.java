public class CalorieCalculatorContext {
    private CalorieCalculationStrategy strategy;

    public CalorieCalculatorContext(CalorieCalculationStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(CalorieCalculationStrategy strategy) {
        this.strategy = strategy;
    }

    public double executeCalculation(double weight, double height, int age, boolean isMale) {
        strategy.printStrategyInfo();
        return strategy.calculateCalories(weight, height, age, isMale);
    }
}