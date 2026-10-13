public interface MealEvaluator {
     String evaluate(Food food);
     default void printEvaluationInfo(Food food) {
        System.out.println("[Інфо] Аналіз страви: " + food.getMealName() + " (Загальні калорії: " + food.getTotalCalories() + " ккал)");
    }
}