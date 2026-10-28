import java.util.Objects;

public final class HomemadeDish extends PreparedFood {
    private final Recipe recipe;

    public HomemadeDish(String mealName, double weightGrams,
                        double caloriesPer100g, double proteinPer100g,
                        int mealTypeInt, String preparationMethod,
                        Recipe recipe) {
        super(mealName, weightGrams, caloriesPer100g,
                proteinPer100g, mealTypeInt, preparationMethod);

        this.recipe = Objects.requireNonNull(recipe, "Рецепт не може бути null");

        System.out.println("Створено домашню страву: " + recipe.getName());
    }

    public Recipe getRecipe() {
        return recipe;
    }

    @Override
    public String getFoodCategory() {
        return "Домашня страва";
    }

    @Override
    public String toString() {
        return super.toString() + " | Рецепт: " + recipe;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }

        HomemadeDish that = (HomemadeDish) o;
        return Objects.equals(recipe, that.recipe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(),
                recipe.getName(), recipe.getDescription());
    }
}