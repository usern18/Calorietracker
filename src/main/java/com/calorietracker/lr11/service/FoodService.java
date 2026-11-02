
package com.calorietracker.lr11.service;

import com.calorietracker.lr11.model.Food;

import java.util.List;

public interface FoodService extends CRUDService<Food> {

    List<Food> findAllSortedByNameAndByCalories();
}
