
package com.calorietracker.lr10.service;

import com.calorietracker.lr10.model.Category;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbcCategoryService
        extends AbstractJdbcCrudService<Category> {

    @Override
    protected String getTableName() {
        return "lr10_categories";
    }

    @Override
    protected String getInsertSql() {
        return "INSERT INTO lr10_categories (name) VALUES (?)";
    }

    @Override
    protected String getUpdateSql() {
        return "UPDATE lr10_categories SET name = ? WHERE id = ?";
    }

    @Override
    protected void bindInsert(
            PreparedStatement statement, Category category)
            throws SQLException {

        statement.setString(1, category.getName());
    }

    @Override
    protected void bindUpdate(
            PreparedStatement statement, Category category)
            throws SQLException {

        statement.setString(1, category.getName());
        statement.setInt(2, category.getId());
    }

    @Override
    protected Category mapRow(ResultSet resultSet) throws SQLException {

        return new Category(
                resultSet.getInt("id"),
                resultSet.getString("name")
        );
    }

    @Override
    protected String getNameColumn() {
        return "name";
    }

    @Override
    protected List<String> getSortableColumns() {
        return List.of("id");
    }
}
