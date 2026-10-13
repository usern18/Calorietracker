import java.util.Objects;

public class Food {
    private String mealName;
    private double weightGrams;
    private double caloriesPer100g;
    private double proteinPer100g;
    private int mealTypeInt;

    public Food(String mealName, double weightGrams, double caloriesPer100g, double proteinPer100g, int mealTypeInt) throws InvalidCaloriesException {
        setMealName(mealName);
        setWeightGrams(weightGrams);
        setCaloriesPer100g(caloriesPer100g);
        setProteinPer100g(proteinPer100g);
        setMealTypeInt(mealTypeInt);
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public void setWeightGrams(double weightGrams) throws InvalidCaloriesException {
        if (weightGrams <= 0) {
            throw new InvalidCaloriesException("Вага страви не може бути від'ємною або нульовою!", weightGrams);
        }
        this.weightGrams = weightGrams;
    }

    public void setCaloriesPer100g(double caloriesPer100g) throws InvalidCaloriesException {
        if (caloriesPer100g < 0) {
            throw new InvalidCaloriesException("Калорійність на 100г не може бути від'ємною!", caloriesPer100g);
        }
        this.caloriesPer100g = caloriesPer100g;
    }

    public void setProteinPer100g(double proteinPer100g) {
        this.proteinPer100g = proteinPer100g;
    }

    public void setMealTypeInt(int mealTypeInt) {
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
                mealTypeInt == food.mealTypeInt &&
                Objects.equals(mealName, food.mealName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mealName, weightGrams, caloriesPer100g, proteinPer100g, mealTypeInt);
    }
}