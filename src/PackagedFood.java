
import java.util.Objects;

public final class PackagedFood extends Food {
    private final String brand;

    public PackagedFood(String mealName, double weightGrams,
                        double caloriesPer100g, double proteinPer100g,
                        int mealTypeInt, String brand) {
        super(mealName, weightGrams, caloriesPer100g,
                proteinPer100g, mealTypeInt);
        this.brand = brand;

        System.out.println("Створено фасований продукт: " + getMealName());
    }

    public String getBrand() {
        return brand;
    }

    @Override
    public String getFoodCategory() {
        return "Фасований продукт";
    }

    @Override
    public String toString() {
        return super.toString() + " | Виробник: " + brand;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;

        PackagedFood that = (PackagedFood) o;
        return Objects.equals(brand, that.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), brand);
    }
}
