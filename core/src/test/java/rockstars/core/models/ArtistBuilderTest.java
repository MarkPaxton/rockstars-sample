package rockstars.core.models;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import rockstars.api.models.IArtist;
import rockstars.api.models.IArtistBuilder;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArtistBuilderTest {

    @Test
    void artistBuilderConstructor_Should_CreateArtistBuilder() {
        ArtistBuilder ab = new ArtistBuilder();

        assertNotNull(ab);
        assertTrue(Arrays.asList(ab.getClass().getInterfaces()).contains(IArtistBuilder.class));
    }

    @Test
    void artistConstructor_Should_CreateArtistWithValues() {
        ArtistBuilder ab = new ArtistBuilder();

        IArtist a =  ab.Build();

        assertTrue(a instanceof Artist);
        assertTrue(Arrays.asList(a.getClass().getInterfaces()).contains(IArtist.class));

    }
}
