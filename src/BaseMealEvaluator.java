public abstract class BaseMealEvaluator implements MealEvaluator {

    protected boolean isLightPortion(Food food) {
        return food.getTotalCalories() < 300;
    }

    @Override
    public abstract String evaluate(Food food);
}