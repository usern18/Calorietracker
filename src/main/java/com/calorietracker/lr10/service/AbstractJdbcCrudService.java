
package com.calorietracker.lr10.service;

import com.calorietracker.lr10.database.DBConnection;
import com.calorietracker.lr10.model.BaseEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractJdbcCrudService<T extends BaseEntity> {

    protected abstract String getTableName();

    protected abstract String getInsertSql();

    protected abstract String getUpdateSql();

    protected abstract void bindInsert(
            PreparedStatement statement, T entity) throws SQLException;

    protected abstract void bindUpdate(
            PreparedStatement statement, T entity) throws SQLException;

    protected abstract T mapRow(ResultSet resultSet) throws SQLException;

    // Кожен підклас визначає поле назви.
    protected abstract String getNameColumn();

    // Дозволені поля для додаткового сортування.
    protected abstract List<String> getSortableColumns();

    public T create(T entity) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     getInsertSql(), Statement.RETURN_GENERATED_KEYS)) {

            bindInsert(statement, entity);
            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    entity.setId(keys.getInt(1));
                }
            }
            return entity;
        }
    }

    public boolean update(T entity) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(getUpdateSql())) {

            bindUpdate(statement, entity);
            return statement.executeUpdate() > 0;
        }
    }

    // INSERT, якщо ID ще немає; UPDATE, якщо ID задано.
    public T save(T entity) throws SQLException {
        if (entity.getId() <= 0) {
            return create(entity);
        }

        if (!update(entity)) {
            throw new EntityNotFoundException(entity.getId());
        }

        return entity;
    }

    public T findById(int id) throws SQLException {
        String sql = "SELECT * FROM " + getTableName()
                + " WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRow(resultSet);
                }
            }
        }

        throw new EntityNotFoundException(id);
    }

    public List<T> findAll() throws SQLException {
        return executeSelect(
                "SELECT * FROM " + getTableName() + " ORDER BY id"
        );
    }

    public List<T> findAllSortedByName() throws SQLException {
        return executeSelect(
                "SELECT * FROM " + getTableName()
                        + " ORDER BY " + getNameColumn() + ", id"
        );
    }

    public List<T> findAllSortedByNameAndBy(String column)
            throws SQLException {

        if (!getSortableColumns().contains(column)) {
            throw new IllegalArgumentException(
                    "Недозволене поле сортування: " + column);
        }

        return executeSelect(
                "SELECT * FROM " + getTableName()
                        + " ORDER BY " + getNameColumn()
                        + ", " + column + ", id"
        );
    }

    public List<T> findPage(int limit, int offset)
            throws SQLException {

        if (limit <= 0 || offset < 0) {
            throw new IllegalArgumentException(
                    "limit має бути > 0, offset >= 0");
        }

        String sql = "SELECT * FROM " + getTableName()
                + " ORDER BY id LIMIT ? OFFSET ?";

        List<T> entities = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, limit);
            statement.setInt(2, offset);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    entities.add(mapRow(resultSet));
                }
            }
        }

        return entities;
    }

    private List<T> executeSelect(String sql) throws SQLException {
        List<T> entities = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                entities.add(mapRow(resultSet));
            }
        }

        return entities;
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM " + getTableName()
                + " WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        }
    }
}
