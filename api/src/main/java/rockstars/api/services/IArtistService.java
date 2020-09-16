package rockstars.api.services;

import java.util.List;

import rockstars.api.models.IArtist;

public interface IArtistService extends ICrudService<IArtist> {

    List<IArtist> getByName(String nameQuery);

}
