package rockstars.core.models;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import rockstars.api.models.ISong;
import rockstars.api.models.ISongBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SongBuilderTest {

    @Test
    void songBuilderConstructor_Should_CreateSongBuilder() {
        SongBuilder sb = new SongBuilder();

        assertNotNull(sb);
        assertTrue(Arrays.asList(sb.getClass().getInterfaces()).contains(ISongBuilder.class));
    }

    @Test
    void songConstructorShould_Create_Song() {
        SongBuilder sb = new SongBuilder();
        ISong s = sb.Build();
        assertTrue(s instanceof Song);
    }

    @Test
    void songConstructorShould_Create_SongWithValues() {
        SongBuilder sb = new SongBuilder();
        ISong s = sb.Build(2L, "AlbumName", 1990L, "ArtistRef", "sName", 120L, 300L, "Jazz", "SpotifyId123", "AlbumNAme"); // MyClass is tested

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
    void songSettersAndGetters_Should_CreateSongFromExistingSong() {
        ISong other = new Song(2L, "AlbumName", 1990L, "ArtistRef", "sName", 120L, 300L, "Jazz", "SpotifyId123", "AlbumNAme");

        SongBuilder sb = new SongBuilder();
        ISong s = sb.Build(other);

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
}
