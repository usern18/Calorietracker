public class ProteinEvaluator implements MealEvaluator {
    @Override
    public String evaluate(Food food) {
        double protein = food.getTotalProtein();
        if (protein >= 20.0) {
            return "Протеїновий вердикт: Високий вміст білка (" + protein + "г). Ідеально після тренування!";
        } else {
            return "Протеїновий вердикт: Низький вміст білка (" + protein + "г). Рекомендується додати джерело протеїну.";
        }
    }
}