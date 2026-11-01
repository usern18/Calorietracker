package lab7.service;

import lab7.model.Food;

import java.util.Comparator;
import java.util.List;

public class FoodCrudService
        extends InMemoryCrudService<Food>
        implements FoodService {

    @Override
    public List<Food> findAllSortedByNameAndByCalories() {

        Comparator<Food> comparator =
                Comparator.comparing(
                        Food::getName,
                        String.CASE_INSENSITIVE_ORDER
                ).thenComparingDouble(Food::getTotalCalories);

        return findAllSorted(comparator);
    }
}
