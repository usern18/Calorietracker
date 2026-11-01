
package lab7.service;

import lab7.model.BaseEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public interface CRUDService<T extends BaseEntity> {

    T findById(long id);

    T save(T entity);

    boolean delete(long id);

    List<T> findAll();

    List<T> findAllSorted(Comparator<? super T> comparator);

    default List<T> findAllSortedByName() {
        return findAllSorted(
                Comparator.comparing(
                        BaseEntity::getName,
                        String.CASE_INSENSITIVE_ORDER
                )
        );
    }

    static void printAll(Collection<? extends BaseEntity> items) {
        for (BaseEntity item : items) {
            System.out.println(item);
        }
    }
}
