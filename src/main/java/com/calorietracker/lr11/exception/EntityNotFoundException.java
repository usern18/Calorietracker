package com.calorietracker.lr11.exception;

public class EntityNotFoundException extends RuntimeException {

    private final long id;

    public EntityNotFoundException(long id) {
        super("Entity with ID " + id + " was not found");
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
