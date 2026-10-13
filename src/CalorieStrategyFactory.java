public class CalorieStrategyFactory {

    public static MealEvaluator createEvaluator(String type) {
        if (type == null) {
            throw new IllegalArgumentException("Тип оцінювача не може бути null");
        }

        switch (type.toLowerCase().trim()) {
            case "keto":
                return new KetoEvaluator();
            case "protein":
                return new ProteinEvaluator();
            default:
                throw new IllegalArgumentException("Невідомий тип оцінювача: " + type);
        }
    }
}