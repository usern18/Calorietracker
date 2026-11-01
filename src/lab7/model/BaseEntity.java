
package lab7.model;

import java.util.Objects;

public abstract class BaseEntity {

    private final long id;
    private final String name;

    public BaseEntity(long id, String name) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "Name cannot be null");
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof BaseEntity that)) {
            return false;
        }

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
