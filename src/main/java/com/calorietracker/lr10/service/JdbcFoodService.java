
package com.calorietracker.lr10.service;

import com.calorietracker.lr10.database.DBConnection;
import com.calorietracker.lr10.model.Food;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbcFoodService extends AbstractJdbcCrudService<Food> {

    @Override
    protected String getTableName() {
        return "lr10_foods";
    }

    @Override
    protected String getInsertSql() {
        return "INSERT INTO lr10_foods " +
                "(meal_name, weight_grams, calories_per_100g, " +
                "protein_per_100g, meal_type_int, category_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateSql() {
        return "UPDATE lr10_foods SET meal_name = ?, weight_grams = ?, " +
                "calories_per_100g = ?, protein_per_100g = ?, " +
                "meal_type_int = ?, category_id = ? WHERE id = ?";
    }

    @Override
    protected void bindInsert(
            PreparedStatement statement, Food food)
            throws SQLException {

        statement.setString(1, food.getMealName());
        statement.setDouble(2, food.getWeightGrams());
        statement.setDouble(3, food.getCaloriesPer100g());
        statement.setDouble(4, food.getProteinPer100g());
        statement.setInt(5, food.getMealTypeInt());
        statement.setInt(6, food.getCategoryId());
    }

    @Override
    protected void bindUpdate(
            PreparedStatement statement, Food food)
            throws SQLException {

        statement.setString(1, food.getMealName());
        statement.setDouble(2, food.getWeightGrams());
        statement.setDouble(3, food.getCaloriesPer100g());
        statement.setDouble(4, food.getProteinPer100g());
        statement.setInt(5, food.getMealTypeInt());
        statement.setInt(6, food.getCategoryId());
        statement.setInt(7, food.getId());
    }

    @Override
    protected Food mapRow(ResultSet resultSet) throws SQLException {

        return new Food(
                resultSet.getInt("id"),
                resultSet.getString("meal_name"),
                resultSet.getDouble("weight_grams"),
                resultSet.getDouble("calories_per_100g"),
                resultSet.getDouble("protein_per_100g"),
                resultSet.getInt("meal_type_int"),
                resultSet.getInt("category_id")
        );
    }

    @Override
    protected String getNameColumn() {
        return "meal_name";
    }

    @Override
    protected List<String> getSortableColumns() {
        return List.of(
                "weight_grams",
                "calories_per_100g",
                "protein_per_100g",
                "meal_type_int"
        );
    }

    // Пакетне додавання кількох страв до бази даних
    public int[] insertBatch(List<Food> foods) throws SQLException {

        if (foods == null || foods.isEmpty()) {
            return new int[0];
        }

        try (Connection connection = DBConnection.getConnection()) {

            connection.setAutoCommit(false);

            try {
                int[] results;

                try (PreparedStatement statement =
                             connection.prepareStatement(getInsertSql())) {

                    for (Food food : foods) {
                        bindInsert(statement, food);
                        statement.addBatch();
                    }

                    results = statement.executeBatch();
                }

                connection.commit();

                return results;

            } catch (SQLException e) {

                try {
                    connection.rollback();
                } catch (SQLException rollbackError) {
                    e.addSuppressed(rollbackError);
                }

                throw e;

            } finally {
                connection.setAutoCommit(true);
            }
        }
    }
}
