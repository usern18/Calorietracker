import java.util.Objects;

public class Food {
    private String mealName;
    private double weightGrams;
    private double caloriesPer100g;
    private double proteinPer100g;
    private int mealTypeInt;

    public Food(String mealName, double weightGrams, double caloriesPer100g,
                double proteinPer100g, int mealTypeInt) throws DomainException {
        if (mealName == null || mealName.trim().isEmpty()) {
            throw new DomainException("Назва страви не може бути порожньою", "mealName");
        }
        if (weightGrams <= 0) {
            throw new InvalidWeightException(
                    "Вага порції повинна бути додатною (більшою за 0 г)", weightGrams);
        }
        if (caloriesPer100g < 0) {
            throw new InvalidCaloriesException(
                    "Калорійність на 100 г не може бути від'ємною", caloriesPer100g);
        }
        if (proteinPer100g < 0) {
            throw new DomainException(
                    "Вміст білків на 100 г не може бути від'ємним", "proteinPer100g");
        }
        if (mealTypeInt < 1 || mealTypeInt > 4) {
            throw new InvalidMealTypeException(
                    "Тип прийому їжі повинен бути в діапазоні 1–4 (1-Сніданок, 2-Обід, 3-Вечеря, 4-Перекус)",
                    mealTypeInt);
        }

        this.mealName = mealName.trim();
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