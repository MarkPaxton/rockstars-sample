package rockstars.api.repositories;

import java.util.List;

import rockstars.api.models.IArtist;
import rockstars.api.services.ICrudService;

public interface IArtistRepository extends ICrudRepository<IArtist> {
    List<IArtist> getByName(String nameQuery);
}
