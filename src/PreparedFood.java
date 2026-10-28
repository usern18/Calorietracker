import java.util.Objects;

public sealed class PreparedFood extends Food permits HomemadeDish {
    private final String preparationMethod;

    public PreparedFood(String mealName, double weightGrams,
                        double caloriesPer100g, double proteinPer100g,
                        int mealTypeInt, String preparationMethod) {
        super(mealName, weightGrams, caloriesPer100g,
                proteinPer100g, mealTypeInt);
        this.preparationMethod = preparationMethod;

        System.out.println("Створено приготовану страву: " + getMealName());
    }

    public PreparedFood(String mealName, double weightGrams,
                        double caloriesPer100g, double proteinPer100g,
                        int mealTypeInt) {
        this(mealName, weightGrams, caloriesPer100g,
                proteinPer100g, mealTypeInt, "Не вказано");
    }

    public String getPreparationMethod() {
        return preparationMethod;
    }

    @Override
    public String getFoodCategory() {
        return "Приготована страва";
    }

    @Override
    public String toString() {
        return super.toString()
                + " | Спосіб приготування: " + preparationMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;

        PreparedFood that = (PreparedFood) o;
        return Objects.equals(preparationMethod, that.preparationMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), preparationMethod);
    }
}
