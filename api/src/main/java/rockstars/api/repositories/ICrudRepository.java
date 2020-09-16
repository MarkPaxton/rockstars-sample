package rockstars.api.repositories;

import java.util.List;
import java.util.Optional;

public interface ICrudRepository<T> {
    List<T> getAll();

    Optional<T> get(Long id);

    T add(T newItem) throws RepositoryException;

    void delete(Long id) throws RepositoryException;

    T update(T updatedItem) throws RepositoryException;
}
