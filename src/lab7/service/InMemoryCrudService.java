
package lab7.service;

import lab7.exception.EntityNotFoundException;
import lab7.model.BaseEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class InMemoryCrudService<T extends BaseEntity>
        implements CRUDService<T> {

    private final Map<Long, T> storage = new LinkedHashMap<>();

    @Override
    public T findById(long id) {
        T entity = storage.get(id);

        if (entity == null) {
            throw new EntityNotFoundException(id);
        }

        return entity;
    }

    @Override
    public T save(T entity) {
        Objects.requireNonNull(entity, "Entity cannot be null");

        storage.put(entity.getId(), entity);

        return entity;
    }

    @Override
    public boolean delete(long id) {
        return storage.remove(id) != null;
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<T> findAllSorted(Comparator<? super T> comparator) {
        Objects.requireNonNull(comparator, "Comparator cannot be null");

        List<T> result = new ArrayList<>(storage.values());
        result.sort(comparator);

        return result;
    }
}
