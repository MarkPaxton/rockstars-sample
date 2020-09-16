package rockstars.core.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rockstars.api.models.IArtist;
import rockstars.api.repositories.IArtistRepository;
import rockstars.api.repositories.RepositoryException;
import rockstars.api.services.IArtistService;
import rockstars.api.services.ServiceException;

@Service
public class ArtistService implements IArtistService {
    private IArtistRepository artistRepository;

    public ArtistService() {
    }

    @Autowired public ArtistService(IArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Override public List<IArtist> getByName(String nameQuery) {
        return artistRepository.getByName(nameQuery);
    }

    @Override public List<IArtist> getAll() {
        return artistRepository.getAll();
    }

    @Override public Optional<IArtist> get(Long id) {
        return artistRepository.get(id);
    }

    @Override public IArtist add(IArtist newItem) throws ServiceException {
        if (nameExists(newItem.getName())) {
            throw new ServiceException("Artist with name '" + newItem.getName() + "' already exists");
        }
        try {
            return artistRepository.add(newItem);
        } catch (RepositoryException ex) {
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Override public void delete(Long id) throws ServiceException {
        try {
            artistRepository.delete(id);
        } catch (RepositoryException ex) {
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Override public IArtist update(IArtist updatedItem) throws ServiceException {
        if (nameExists(updatedItem.getName())) {
            throw new ServiceException("Artist with name '" + updatedItem.getName() + "' already exists");
        }
        try {
            return artistRepository.update(updatedItem);
        } catch (RepositoryException ex) {
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    private boolean nameExists(String name) {
        return !artistRepository.getByName(name).isEmpty();
    }
}
