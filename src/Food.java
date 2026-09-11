import java.util.Objects;

public class Food {
    private String mealName;
    private double weightGrams;
    private double caloriesPer100g;
    private double proteinPer100g;
    private int mealTypeInt;

    public Food(String mealName, double weightGrams, double caloriesPer100g, double proteinPer100g, int mealTypeInt) {
        this.mealName = mealName;
        this.weightGrams = weightGrams;
        this.caloriesPer100g = caloriesPer100g;
        this.proteinPer100g = proteinPer100g;
        this.mealTypeInt = mealTypeInt;
    }

    public double getTotalCalories() {
        return (weightGrams * caloriesPer100g) / 100.0;
    }

    public double getTotalProtein() {
        return (weightGrams * proteinPer100g) / 100.0;
    }

    @Override
    public String toString() {
        String mealTypeName = switch (mealTypeInt) {
            case 1 -> "Сніданок";
            case 2 -> "Обід";
            case 3 -> "Вечеря";
            default -> "Перекус";
        };

        String conclusion;
        double totalCalories = getTotalCalories();
        if (totalCalories > 600) {
            conclusion = "Дуже калорійна страва для одного прийому їжі";
        } else if (totalCalories < 200) {
            conclusion = "Легкий перекус із низькою калорійністю";
        } else {
            conclusion = "Помірна та збалансована порція";
        }

        return String.format("Страва: %s (%s) | Вага: %.2f г | Калорії: %.2f ккал | Білки: %.2f г | Висновок: %s",
                mealName, mealTypeName, weightGrams, totalCalories, getTotalProtein(), conclusion);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return Double.compare(food.weightGrams, weightGrams) == 0 &&
                Double.compare(food.caloriesPer100g, caloriesPer100g) == 0 &&
                Double.compare(food.proteinPer100g, proteinPer100g) == 0 &&
                mealTypeInt == mealTypeInt &&
                Objects.equals(mealName, food.mealName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mealName, weightGrams, caloriesPer100g, proteinPer100g, mealTypeInt);
    }
}