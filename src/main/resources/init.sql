
CREATE TABLE IF NOT EXISTS lr10_categories (
                                               id INT PRIMARY KEY AUTO_INCREMENT,
                                               name VARCHAR(100) NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS lr10_foods (
                                          id INT PRIMARY KEY AUTO_INCREMENT,
                                          meal_name VARCHAR(150) NOT NULL,
    weight_grams DOUBLE NOT NULL,
    calories_per_100g DOUBLE NOT NULL,
    protein_per_100g DOUBLE NOT NULL,
    meal_type_int INT NOT NULL,
    category_id INT NOT NULL,
    CONSTRAINT fk_lr10_food_category
    FOREIGN KEY (category_id)
    REFERENCES lr10_categories(id)
    ON DELETE RESTRICT
    );

INSERT IGNORE INTO lr10_categories (name)
VALUES
    ('Сніданок'),
    ('Обід'),
    ('Вечеря'),
    ('Перекус');
