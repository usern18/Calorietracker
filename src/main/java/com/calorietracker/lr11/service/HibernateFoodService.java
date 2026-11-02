
package com.calorietracker.lr11.service;

import com.calorietracker.lr11.config.JpaUtil;
import com.calorietracker.lr11.exception.EntityNotFoundException;
import com.calorietracker.lr11.model.Category;
import com.calorietracker.lr11.model.Food;

import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class HibernateFoodService implements FoodService {

    private int toIntId(long id) {
        if (id <= 0 || id > Integer.MAX_VALUE) {
            throw new EntityNotFoundException(id);
        }
        return (int) id;
    }

    @Override
    public Food findById(long id) {
        int foodId = toIntId(id);

        return JpaUtil.read(em -> {
            Food food = em.find(Food.class, foodId);

            if (food == null) {
                throw new EntityNotFoundException(id);
            }

            return food;
        });
    }

    @Override
    public Food save(Food food) {
        if (food == null) {
            throw new IllegalArgumentException("Food cannot be null");
        }

        if (food.getId() == null) {
            return JpaUtil.transaction(em -> {
                if (food.getCategory() == null ||
                        food.getCategory().getId() == null) {
                    throw new IllegalArgumentException(
                            "Food must have an existing category"
                    );
                }

                Category category = em.getReference(
                        Category.class,
                        food.getCategory().getId()
                );

                food.setCategory(category);
                em.persist(food);
                return food;
            });
        }

        return JpaUtil.transaction(em -> {
            Food existing = em.find(Food.class, food.getId());

            if (existing == null) {
                throw new EntityNotFoundException(food.getId());
            }

            existing.setName(food.getName());
            existing.setWeightGrams(food.getWeightGrams());
            existing.setCaloriesPer100g(food.getCaloriesPer100g());
            existing.setProteinPer100g(food.getProteinPer100g());
            existing.setMealTypeInt(food.getMealTypeInt());

            if (food.getCategory() != null) {
                if (food.getCategory().getId() == null) {
                    throw new IllegalArgumentException(
                            "Category must have an ID"
                    );
                }

                Category category = em.getReference(
                        Category.class,
                        food.getCategory().getId()
                );

                existing.setCategory(category);
            }

            return existing;
        });
    }

    @Override
    public boolean delete(long id) {
        int foodId = toIntId(id);

        return JpaUtil.transaction(em -> {
            Food food = em.find(Food.class, foodId);

            if (food == null) {
                throw new EntityNotFoundException(id);
            }

            em.remove(food);
            return true;
        });
    }

    @Override
    public List<Food> findAll() {
        return JpaUtil.read(em ->
                em.createQuery(
                        "SELECT f FROM Food f ORDER BY f.id",
                        Food.class
                ).getResultList()
        );
    }

    @Override
    public List<Food> findAllSorted(
            Comparator<? super Food> comparator
    ) {
        if (comparator == null) {
            throw new IllegalArgumentException(
                    "Comparator cannot be null"
            );
        }

        List<Food> foods = new ArrayList<>(findAll());
        foods.sort(comparator);
        return foods;
    }

    @Override
    public List<Food> findAllSortedByNameAndByCalories() {
        return JpaUtil.read(em ->
                em.createQuery(
                        "SELECT f FROM Food f " +
                                "ORDER BY LOWER(f.name) ASC, " +
                                "(f.weightGrams * f.caloriesPer100g) DESC",
                        Food.class
                ).getResultList()
        );
    }

    // Compatibility with the existing LR11 Main

    public Food create(Food food) {
        if (food.getId() != null) {
            throw new IllegalArgumentException(
                    "New food must not already have an ID"
            );
        }

        return save(food);
    }

    public Food getById(int id) {
        return findById(id);
    }

    public List<Food> getAll() {
        return findAll();
    }

    public Food update(Food food) {
        if (food == null || food.getId() == null) {
            throw new IllegalArgumentException(
                    "Food ID is required for update"
            );
        }

        return save(food);
    }

    public void delete(int id) {
        delete((long) id);
    }

    public List<Food> sortByCaloriesDesc() {
        return JpaUtil.read(em ->
                em.createQuery(
                        "SELECT f FROM Food f " +
                                "ORDER BY " +
                                "(f.weightGrams * f.caloriesPer100g) DESC",
                        Food.class
                ).getResultList()
        );
    }

    public List<Food> findByCategory(int categoryId) {
        return JpaUtil.read(em ->
                em.createQuery(
                                "SELECT f FROM Food f " +
                                        "WHERE f.category.id = :categoryId",
                                Food.class
                        )
                        .setParameter("categoryId", categoryId)
                        .getResultList()
        );
    }

    public List<Food> getPage(int page, int size) {
        if (page < 0 || size <= 0 ||
                (long) page * size > Integer.MAX_VALUE) {
            throw new IllegalArgumentException(
                    "Invalid page number or size"
            );
        }

        return JpaUtil.read(em ->
                em.createQuery(
                                "SELECT f FROM Food f ORDER BY f.id",
                                Food.class
                        )
                        .setFirstResult(page * size)
                        .setMaxResults(size)
                        .getResultList()
        );
    }

    public List<Food> getAllWithCategories() {
        return JpaUtil.read(em ->
                em.createQuery(
                        "SELECT f FROM Food f " +
                                "JOIN FETCH f.category ORDER BY f.id",
                        Food.class
                ).getResultList()
        );
    }
}
