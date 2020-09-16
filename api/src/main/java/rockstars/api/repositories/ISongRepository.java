package rockstars.api.repositories;

import java.util.List;

import rockstars.api.models.ISong;

public interface ISongRepository extends ICrudRepository<ISong> {
    List<ISong> getByGenre(String genre);

    List<ISong> getBeforeYear(Long year);
}
