package rockstars.core.models;

import org.springframework.stereotype.Component;

import rockstars.api.models.IArtist;
import rockstars.api.models.IArtistBuilder;

@Component
public class ArtistBuilder implements IArtistBuilder {

    @Override public IArtist Build() {
        return new Artist();
    }
}
