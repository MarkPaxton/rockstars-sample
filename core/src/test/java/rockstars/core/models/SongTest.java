package rockstars.core.models;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import rockstars.api.models.ISong;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SongTest {

    @Test
    void songConstructor_Should_CreateSong() {
        Song s = new Song();

        assertNotNull(s);
        assertTrue(Arrays.asList(s.getClass().getInterfaces()).contains(ISong.class));

    }

    @Test
    void songConstructorShould_Create_SongWithValues() {
        Song s = new Song(2L, "AlbumName", 1990L, "ArtistRef", "sName", 120L, 300L, "Jazz", "SpotifyId123", "AlbumNAme");

        assertEquals(2L, s.getId());
        assertEquals("AlbumName", s.getName());
        assertEquals(1990L, s.getYear());
        assertEquals("ArtistRef", s.getArtist());
        assertEquals("sName", s.getShortName());
        assertEquals(120L, s.getBpm());
        assertEquals(300L, s.getDuration());
        assertEquals("Jazz", s.getGenre());
        assertEquals("SpotifyId123", s.getSpotifyId());
    }

    @Test
    void songSettersAndGetters_Should_SetAndGetValues() {
        Song a = new Song(2L, "TestSong", 1990L, "ArtistRef", "sName", 120L, 300L, "Jazz", "SpotifyId123", "AlbumName");

        a.setId(3L);
        a.setAlbum("SetAlbum");
        a.setArtist("SetArtist");
        a.setBpm(10L);
        a.setDuration(30L);
        a.setGenre("SetGenre");
        a.setName("SetName");
        a.setShortName("SetShortName");
        a.setSpotifyId("SetSpotifyId");
        a.setYear(2020L);
        
        assertEquals(3L, a.getId());
        assertEquals("SetAlbum", a.getAlbum());
        assertEquals("SetArtist", a.getArtist());
        assertEquals(10L, a.getBpm());
        assertEquals(30L, a.getDuration());
        assertEquals("SetGenre", a.getGenre());
        assertEquals("SetName", a.getName());
        assertEquals("SetShortName", a.getShortName());
        assertEquals("SetSpotifyId", a.getSpotifyId());
        assertEquals(2020L, a.getYear());
    }
}
