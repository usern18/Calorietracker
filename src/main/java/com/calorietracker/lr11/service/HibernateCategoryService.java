
package com.calorietracker.lr11.service;

import com.calorietracker.lr11.config.JpaUtil;
import com.calorietracker.lr11.model.Category;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.function.Consumer;

public class HibernateCategoryService {

    // CREATE - додавання категорії
    public Category create(Category category) {
        return JpaUtil.transaction(em -> {
            em.persist(category);
            return category;
        });
    }

    // READ - пошук категорії за ID
    public Category getById(int id) {
        return JpaUtil.read(em ->
                em.find(Category.class, id)
        );
    }

    // READ - отримання всіх категорій
    public List<Category> getAll() {
        return JpaUtil.read(em ->
                em.createQuery(
                        "SELECT c FROM Category c ORDER BY c.id",
                        Category.class
                ).getResultList()
        );
    }

    // UPDATE - оновлення категорії
    public Category update(Category category) {
        return JpaUtil.transaction(em -> {
            Category existing = em.find(
                    Category.class,
                    category.getId()
            );

            if (existing == null) {
                throw new IllegalArgumentException(
                        "Категорію не знайдено: " + category.getId()
                );
            }

            existing.setName(category.getName());
            return existing;
        });
    }

    // DELETE - видалення категорії
    public void delete(int id) {
        JpaUtil.transaction((Consumer<EntityManager>) em -> {
            Category category = em.find(Category.class, id);

            if (category == null) {
                throw new IllegalArgumentException(
                        "Категорію не знайдено: " + id
                );
            }

            em.remove(category);
        });
    }

    // Пошук категорії за назвою
    public Category findByName(String name) {
        return JpaUtil.read(em ->
                em.createQuery(
                                "SELECT c FROM Category c WHERE c.name = :name",
                                Category.class
                        )
                        .setParameter("name", name)
                        .getResultStream()
                        .findFirst()
                        .orElse(null)
        );
    }
}
