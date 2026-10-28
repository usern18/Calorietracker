
import java.util.Objects;

public abstract sealed class Food permits PreparedFood, PackagedFood {
    private final String mealName;
    private final double weightGrams;
    private final double caloriesPer100g;
    private final double proteinPer100g;
    private final int mealTypeInt;

    public Food(String mealName, double weightGrams,
                double caloriesPer100g, double proteinPer100g,
                int mealTypeInt) {
        this.mealName = mealName;
        this.weightGrams = weightGrams;
        this.caloriesPer100g = caloriesPer100g;
        this.proteinPer100g = proteinPer100g;
        this.mealTypeInt = mealTypeInt;

        System.out.println("Створено об'єкт Food: " + mealName);
    }

    public String getMealName() {
        return mealName;
    }

    public double getWeightGrams() {
        return weightGrams;
    }

    public double getCaloriesPer100g() {
        return caloriesPer100g;
    }

    public double getProteinPer100g() {
        return proteinPer100g;
    }

    public int getMealTypeInt() {
        return mealTypeInt;
    }

    public final double getTotalCalories() {
        return weightGrams * caloriesPer100g / 100.0;
    }

    public final double getTotalProtein() {
        return weightGrams * proteinPer100g / 100.0;
    }

    public abstract String getFoodCategory();

    protected String getMealTypeName() {
        return switch (mealTypeInt) {
            case 1 -> "Сніданок";
            case 2 -> "Обід";
            case 3 -> "Вечеря";
            case 4 -> "Перекус";
            default -> "Невідомий тип";
        };
    }

    @Override
    public String toString() {
        double calories = getTotalCalories();

        String conclusion;
        if (calories > 600) {
            conclusion = "Дуже калорійна страва для одного прийому їжі";
        } else if (calories < 200) {
            conclusion = "Легкий перекус із низькою калорійністю";
        } else {
            conclusion = "Помірна та збалансована порція";
        }

        return String.format(
                "Страва: %s (%s) | Категорія: %s | Вага: %.2f г | " +
                        "Калорії: %.2f ккал | Білки: %.2f г | Висновок: %s",
                mealName, getMealTypeName(), getFoodCategory(),
                weightGrams, calories, getTotalProtein(), conclusion
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Food food = (Food) o;

        return Double.compare(weightGrams, food.weightGrams) == 0
                && Double.compare(caloriesPer100g, food.caloriesPer100g) == 0
                && Double.compare(proteinPer100g, food.proteinPer100g) == 0
                && mealTypeInt == food.mealTypeInt
                && Objects.equals(mealName, food.mealName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getClass(), mealName, weightGrams,
                caloriesPer100g, proteinPer100g, mealTypeInt
        );
    }
}
