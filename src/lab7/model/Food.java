
package lab7.model;

public class Food extends BaseEntity {

    private final double weightGrams;
    private final double caloriesPer100g;
    private final double proteinPer100g;
    private final int mealTypeInt;

    public Food(long id, String name,
                double weightGrams,
                double caloriesPer100g,
                double proteinPer100g,
                int mealTypeInt) {

        super(id, name);

        if (weightGrams <= 0 || caloriesPer100g < 0 || proteinPer100g < 0) {
            throw new IllegalArgumentException("Invalid food values");
        }

        if (mealTypeInt < 1 || mealTypeInt > 4) {
            throw new IllegalArgumentException("Meal type must be from 1 to 4");
        }

        this.weightGrams = weightGrams;
        this.caloriesPer100g = caloriesPer100g;
        this.proteinPer100g = proteinPer100g;
        this.mealTypeInt = mealTypeInt;
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

    public double getTotalCalories() {
        return weightGrams * caloriesPer100g / 100;
    }

    public double getTotalProtein() {
        return weightGrams * proteinPer100g / 100;
    }

    public String getMealTypeName() {
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
        return "Food{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", weight=" + weightGrams + " г" +
                ", calories=" + String.format("%.2f", getTotalCalories()) +
                " ккал" +
                ", protein=" + String.format("%.2f", getTotalProtein()) +
                " г" +
                ", mealType='" + getMealTypeName() + '\'' +
                '}';
    }
}
