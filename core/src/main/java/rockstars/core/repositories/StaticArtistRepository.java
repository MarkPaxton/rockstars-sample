package rockstars.core.repositories;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import rockstars.api.models.IArtist;
import rockstars.api.repositories.IArtistRepository;
import rockstars.api.repositories.RepositoryException;
import rockstars.core.models.Artist;

/**
 * Instance of a repository that gets data from the static JSON file and holds data in memory
 */
@Component
public class StaticArtistRepository implements IArtistRepository {
    private final Set<IArtist> artists = new HashSet<>();

    public StaticArtistRepository() throws RepositoryException {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Artist>> typeReference = new TypeReference<>() { };
        InputStream inputStream = TypeReference.class.getResourceAsStream("/artists.json");
        try {
            List<Artist> artistsImported = mapper.readValue(inputStream, typeReference);
            artists.addAll(artistsImported);
        } catch (IOException e) {
            throw new RepositoryException("Unable to load save users json: " + e.getMessage());
        }
    }

    @Override public List<IArtist> getAll() {
        return new ArrayList<>(artists);
    }

    @Override public Optional<IArtist> get(Long id) {
        return artists.stream()
                        .filter(a -> a.getId().equals(id))
                        .findFirst();
    }

    @Override public IArtist add(IArtist newItem) {
        Long nextId = artists.stream().mapToLong(IArtist::getId).max().orElse(0) + 1;
        newItem.setId(nextId);
        artists.add(newItem);
        return newItem;
    }

    @Override public void delete(Long id) throws RepositoryException {
        List<IArtist> toRemove = artists.stream()
                        .filter(a -> a.getId().equals(id))
                        .collect(Collectors.toList());
        if (toRemove.isEmpty()) {
            throw new RepositoryException("Artist with id '" + id + "' does not exist");
        }
        artists.removeAll(toRemove);
    }

    @Override public IArtist update(IArtist updatedItem) throws RepositoryException {
        List<IArtist> toUpdate = artists.stream()
                        .filter(a -> a.getId().equals(updatedItem.getId()))
                        .collect(Collectors.toList());
        if (toUpdate.isEmpty()) {
            throw new RepositoryException("Artist with id '" + updatedItem.getId() + "' does not exist");
        }
        if (toUpdate.size() > 1) {
            throw new RepositoryException("Artist id '" + updatedItem.getId() + "' exists more than once");
        }

        IArtist artist = toUpdate.stream().findFirst().get();
        artist.setName(updatedItem.getName());
        return artist;
    }

    @Override public List<IArtist> getByName(String nameQuery) {
        return artists.stream()
                        .filter(a -> a.getName().equals(nameQuery))
                        .collect(Collectors.toList());
    }

}
