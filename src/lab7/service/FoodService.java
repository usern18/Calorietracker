
package lab7.service;

import lab7.model.Food;

import java.util.List;

public interface FoodService extends CRUDService<Food> {

    List<Food> findAllSortedByNameAndByCalories();
}
