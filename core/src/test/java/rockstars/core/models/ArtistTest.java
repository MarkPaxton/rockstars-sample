package rockstars.core.models;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import rockstars.api.models.IArtist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArtistTest {

    @Test
    void artistConstructor_Should_CreateArtist() {
        Artist a = new Artist();

        assertNotNull(a);
        assertTrue(Arrays.asList(a.getClass().getInterfaces()).contains(IArtist.class));

    }

    @Test
    void artistConstructor_Should_CreateArtistWithValues() {
        Artist a = new Artist(2L, "TestArt");

        assertEquals(2L, a.getId());
        assertEquals("TestArt", a.getName());
    }

    @Test
    void artistGettersAndSetters_Should_AssignValues() {
        Artist a = new Artist();

        a.setId(2L);
        a.setName("TestArt");

        assertEquals(2L, a.getId());
        assertEquals("TestArt", a.getName());
    }
}
