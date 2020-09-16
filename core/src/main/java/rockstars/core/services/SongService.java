package rockstars.core.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rockstars.api.models.ISong;
import rockstars.api.repositories.ISongRepository;
import rockstars.api.repositories.RepositoryException;
import rockstars.api.services.ISongService;
import rockstars.api.services.ServiceException;

@Service
public class SongService implements ISongService {
    private ISongRepository songRepository;

    public SongService() {
    }

    @Autowired public SongService(ISongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override public List<ISong> getByGenre(String genreQuery) {
        return songRepository.getByGenre(genreQuery);
    }

    @Override public List<ISong> getBeforeYear(Long year) {
        return songRepository.getBeforeYear(year);
    }

    @Override public List<ISong> getAll() {
        return songRepository.getAll();
    }

    @Override public Optional<ISong> get(Long id) {
        return songRepository.get(id);
    }

    @Override public ISong add(ISong newItem) throws ServiceException {
        try {
            return songRepository.add(newItem);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override public void delete(Long id) throws ServiceException {
        try {
            songRepository.delete(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override public ISong update(ISong updatedItem) throws ServiceException {
        try {
            return songRepository.update(updatedItem);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
