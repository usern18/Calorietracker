
package lab7.model;

import java.util.Objects;

public class Recipe extends BaseEntity {

    private final String description;

    public Recipe(long id, String name, String description) {
        super(id, name);
        this.description = Objects.requireNonNull(
                description, "Description cannot be null"
        );
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
