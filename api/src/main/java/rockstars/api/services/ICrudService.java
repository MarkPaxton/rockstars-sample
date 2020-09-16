package rockstars.api.services;

import java.util.List;
import java.util.Optional;

public interface ICrudService<T> {
    List<T> getAll();

    Optional<T> get(Long id);

    T add(T newItem) throws ServiceException;

    void delete(Long id) throws Exception;

    T update(T updatedItem) throws Exception;
}
