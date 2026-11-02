
package com.calorietracker.lr11.model;

import jakarta.persistence.*;

@Entity
@Table(name = "lr10_foods")
@AttributeOverride(
        name = "name",
        column = @Column(
                name = "meal_name",
                nullable = false,
                length = 150
        )
)
public class Food extends BaseEntity {

    @Column(name = "weight_grams", nullable = false)
    private double weightGrams;

    @Column(name = "calories_per_100g", nullable = false)
    private double caloriesPer100g;

    @Column(name = "protein_per_100g", nullable = false)
    private double proteinPer100g;

    @Column(name = "meal_type_int", nullable = false)
    private int mealTypeInt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public Food() {
        super();
    }

    public Food(
            String name,
            double weightGrams,
            double caloriesPer100g,
            double proteinPer100g,
            int mealTypeInt,
            Category category
    ) {
        super(name);
        this.weightGrams = weightGrams;
        this.caloriesPer100g = caloriesPer100g;
        this.proteinPer100g = proteinPer100g;
        this.mealTypeInt = mealTypeInt;
        setCategory(category);
    }

    public double getWeightGrams() {
        return weightGrams;
    }

    public void setWeightGrams(double weightGrams) {
        this.weightGrams = weightGrams;
    }

    public double getCaloriesPer100g() {
        return caloriesPer100g;
    }

    public void setCaloriesPer100g(double caloriesPer100g) {
        this.caloriesPer100g = caloriesPer100g;
    }

    public double getProteinPer100g() {
        return proteinPer100g;
    }

    public void setProteinPer100g(double proteinPer100g) {
        this.proteinPer100g = proteinPer100g;
    }

    public int getMealTypeInt() {
        return mealTypeInt;
    }

    public void setMealTypeInt(int mealTypeInt) {
        this.mealTypeInt = mealTypeInt;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category newCategory) {
        this.category = newCategory;
    }

    @Transient
    public double getTotalCalories() {
        return weightGrams * caloriesPer100g / 100.0;
    }

    @Transient
    public double getTotalProtein() {
        return weightGrams * proteinPer100g / 100.0;
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", weight=" + weightGrams +
                ", calories=" + getTotalCalories() +
                '}';
    }
}
