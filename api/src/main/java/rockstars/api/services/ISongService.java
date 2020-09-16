package rockstars.api.services;

import java.util.List;

import rockstars.api.models.ISong;

public interface ISongService extends ICrudService<ISong> {

    List<ISong> getByGenre(String genre);

    List<ISong> getBeforeYear(Long year);
}
