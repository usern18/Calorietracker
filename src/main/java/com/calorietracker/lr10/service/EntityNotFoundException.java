
package com.calorietracker.lr10.service;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(int id) {
        super("Запис з ID " + id + " не знайдено");
    }
}
