
package com.calorietracker.lr10.model;

public class Food extends BaseEntity {

    private String mealName;
    private double weightGrams;
    private double caloriesPer100g;
    private double proteinPer100g;
    private int mealTypeInt;
    private int categoryId;

    public Food() {
    }

    public Food(String mealName, double weightGrams,
                double caloriesPer100g, double proteinPer100g,
                int mealTypeInt, int categoryId) {
        this.mealName = mealName;
        this.weightGrams = weightGrams;
        this.caloriesPer100g = caloriesPer100g;
        this.proteinPer100g = proteinPer100g;
        this.mealTypeInt = mealTypeInt;
        this.categoryId = categoryId;
    }

    public Food(int id, String mealName, double weightGrams,
                double caloriesPer100g, double proteinPer100g,
                int mealTypeInt, int categoryId) {
        super(id);
        this.mealName = mealName;
        this.weightGrams = weightGrams;
        this.caloriesPer100g = caloriesPer100g;
        this.proteinPer100g = proteinPer100g;
        this.mealTypeInt = mealTypeInt;
        this.categoryId = categoryId;
    }

    public double getTotalCalories() {
        return weightGrams * caloriesPer100g / 100;
    }

    public double getTotalProtein() {
        return weightGrams * proteinPer100g / 100;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + getId() +
                ", mealName='" + mealName + '\'' +
                ", weightGrams=" + weightGrams +
                ", calories=" + String.format("%.2f", getTotalCalories()) +
                ", protein=" + String.format("%.2f", getTotalProtein()) +
                ", mealTypeInt=" + mealTypeInt +
                ", categoryId=" + categoryId +
                '}';
    }
}
