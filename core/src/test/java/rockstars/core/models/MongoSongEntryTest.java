package rockstars.core.models;

import java.util.Arrays;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import rockstars.api.models.ISong;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MongoSongEntryTest {

    @Test
    void songConstructor_Should_CreateSong() {
        MongoSongEntry s = new MongoSongEntry();

        assertNotNull(s);
        assertTrue(Arrays.asList(s.getClass().getInterfaces()).contains(ISong.class));

    }

    @Test
    void songConstructor_Should_CreateMongoSongEntryWithValues() {
        ObjectId obId = new ObjectId();
        MongoSongEntry s = new MongoSongEntry(obId, 2L, "AlbumName", 1990L, "ArtistRef", "sName", 120L, 300L, "Jazz", "SpotifyId123", "AlbumNAme"); // MyClass is tested

        assertEquals(obId, s.getiD());
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
        ObjectId obId = new ObjectId();
        MongoSongEntry s = new MongoSongEntry(obId,2L, "TestMongoSongEntry", 1990L, "ArtistRef", "sName", 120L, 300L, "Jazz", "SpotifyId123", "AlbumName");

        ObjectId obId2 = new ObjectId();

        s.setiD(obId2);
        s.setId(3L);
        s.setAlbum("SetAlbum");
        s.setArtist("SetArtist");
        s.setBpm(10L);
        s.setDuration(30L);
        s.setGenre("SetGenre");
        s.setName("SetName");
        s.setShortName("SetShortName");
        s.setSpotifyId("SetSpotifyId");
        s.setYear(2020L);

        assertEquals(obId2, s.getiD());
        assertEquals(3L, s.getId());
        assertEquals("SetAlbum", s.getAlbum());
        assertEquals("SetArtist", s.getArtist());
        assertEquals(10L, s.getBpm());
        assertEquals(30L, s.getDuration());
        assertEquals("SetGenre", s.getGenre());
        assertEquals("SetName", s.getName());
        assertEquals("SetShortName", s.getShortName());
        assertEquals("SetSpotifyId", s.getSpotifyId());
        assertEquals(2020L, s.getYear());
    }

    @Test
    void songSettersAndGetters_Should_UpdateFromAnotherSong() {
        ObjectId obId = new ObjectId();
        MongoSongEntry s = new MongoSongEntry(obId,2L, "TestMongoSongEntry", 1990L, "ArtistRef", "sName", 120L, 300L, "Jazz", "SpotifyId123", "AlbumName");

        ISong s2 = new MongoSongEntry();
        s2.setId(3L);
        s2.setAlbum("SetAlbum");
        s2.setArtist("SetArtist");
        s2.setBpm(10L);
        s2.setDuration(30L);
        s2.setGenre("SetGenre");
        s2.setName("SetName");
        s2.setShortName("SetShortName");
        s2.setSpotifyId("SetSpotifyId");
        s2.setYear(2020L);

        s.updateFrom(s2);

        assertEquals(obId, s.getiD());
        assertEquals(3L, s.getId());
        assertEquals("SetAlbum", s.getAlbum());
        assertEquals("SetArtist", s.getArtist());
        assertEquals(10L, s.getBpm());
        assertEquals(30L, s.getDuration());
        assertEquals("SetGenre", s.getGenre());
        assertEquals("SetName", s.getName());
        assertEquals("SetShortName", s.getShortName());
        assertEquals("SetSpotifyId", s.getSpotifyId());
        assertEquals(2020L, s.getYear());
    }
}
