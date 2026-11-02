
package com.calorietracker.lr11.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lr10_categories")
public class Category extends BaseEntity {

    @OneToMany(
            mappedBy = "category",
            fetch = FetchType.LAZY
    )
    private List<Food> foods = new ArrayList<>();

    public Category() {
        super();
    }

    public Category(String name) {
        super(name);
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void addFood(Food food) {
        if (food == null) {
            throw new IllegalArgumentException(
                    "Food cannot be null"
            );
        }

        if (food.getCategory() != null &&
                food.getCategory() != this) {
            food.getCategory().getFoods().remove(food);
        }

        if (!foods.contains(food)) {
            foods.add(food);
        }

        food.setCategory(this);
    }

    public void removeFood(Food food) {
        if (food != null && foods.remove(food)) {
            food.setCategory(null);
        }
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                '}';
    }
}
