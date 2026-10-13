public class MealContext {
    private MealEvaluator strategy;

    public MealContext(MealEvaluator strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(MealEvaluator strategy) {
        this.strategy = strategy;
    }

    public String executeEvaluation(Food food) {
        strategy.printEvaluationInfo(food);
        return strategy.evaluate(food);
    }
}