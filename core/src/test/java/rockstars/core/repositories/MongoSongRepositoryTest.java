package rockstars.core.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.junit.jupiter.api.Test;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import rockstars.api.models.ISong;
import rockstars.core.models.MongoSongEntryBuilder;
import rockstars.core.models.MongoSongEntry;
import rockstars.core.models.Song;

import static java.util.Arrays.asList;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import static org.bson.codecs.pojo.Conventions.ANNOTATION_CONVENTION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class MongoSongRepositoryTest {

    private static final String TEST_DB =
                    "mongodb://rockstars-test:secureTestPassword@localhost:27017/?readPreference=primary&authSource=admin&ssl=false";

    private MongoSongRepository createInstance() {
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder()
                        .conventions(asList(ANNOTATION_CONVENTION))
                        .automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);


        MongoClient client = MongoClients.create(MongoClientSettings.builder()
                        .applyConnectionString(new ConnectionString(TEST_DB))
                        .codecRegistry(codecRegistry)
                        .build());
        MongoSongEntryBuilder mongoSongBuilder = new MongoSongEntryBuilder();
        return new MongoSongRepository(client, mongoSongBuilder);
    }

    @Test
    void mongoSongRepository_Should_HavePreLoadedSongJson() {
        try {
            MongoSongRepository r = createInstance();
            assertTrue(r.getAll().size()>2500);
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    void mongoSongRepository_Should_FindSongsBeforeYear() {
        try {
            MongoSongRepository r = createInstance();
            assertEquals(9, r.getBeforeYear(1960L).size());
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }


    @Test
    void mongoSongRepository_Should_AddNewSongAndAssignId() {
        MongoSongRepository r = null;
        List<Long> allSongIdsBefore = new ArrayList<>();
        ISong addedSong = null;
        try {
            r = createInstance();
            allSongIdsBefore.addAll(r.getAll().stream()
                            .map(song -> song.getId())
                            .collect(Collectors.toList()));

            ISong newA = new Song();
            newA.setName("Band of Mark");
            newA.setAlbum("Album test");
            newA.setArtist("Mark");
            newA.setBpm(60L);
            newA.setDuration(120L);
            newA.setGenre("Rock");
            newA.setShortName("sName");
            newA.setSpotifyId("abcd");
            newA.setYear(1969L);

            addedSong = r.add(newA);

                  } catch (Exception ex) {
            fail(ex.getMessage());
        }

        //Test the added song has correct data
        assertNotNull(addedSong.getId());
        assertEquals("Band of Mark", addedSong.getName());
        assertEquals("Album test", addedSong.getAlbum());
        assertEquals("Mark", addedSong.getArtist());
        assertEquals("Rock", addedSong.getGenre());
        assertEquals("sName", addedSong.getShortName());
        assertEquals("abcd", addedSong.getSpotifyId());
        assertEquals(60L, addedSong.getBpm());
        assertEquals(120L, addedSong.getDuration());
        assertEquals(1969L, addedSong.getYear());


        //Also test that only one song is added and the data round trip values are correct
        List<ISong> songsAfter = r.getAll();
            
            List<ISong> addedSongs = songsAfter.stream()
                            .filter(song -> !allSongIdsBefore.contains(song.getId()))
                            .collect(Collectors.toList());

            assertEquals(1, addedSongs.size());
            addedSong = addedSongs.get(0);
           
            assertEquals("Band of Mark", addedSong.getName());
            assertEquals("Album test", addedSong.getAlbum());
            assertEquals("Mark", addedSong.getArtist());
            assertEquals("Rock", addedSong.getGenre());
            assertEquals("sName", addedSong.getShortName());
            assertEquals("abcd", addedSong.getSpotifyId());
            assertEquals(60L, addedSong.getBpm());
            assertEquals(120L, addedSong.getDuration());
            assertEquals(1969L, addedSong.getYear());
    }

    @Test
    void mongoSongRepository_Should_GetSongById() {
        MongoSongRepository r = null;
        try {
            r = createInstance();
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
        Optional<ISong> s = r.get(769L);
        assertTrue(s.isPresent());
        assertEquals("(Take These) Chains", s.get().getName());
        assertEquals(769L, s.get().getId());
    }

    @Test
    void mongoSongRepository_Should_GetSongByGenre() {
        MongoSongRepository r = null;
        try {
            r = createInstance();
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
        List<ISong> metalSongs = r.getByGenre("Metal");
        assertEquals(259, metalSongs.size());
    }

    @Test
    void mongoSongRepository_ShouldNot_GetSongWithUnknownGenre() {
        MongoSongRepository r = null;
        try {
            r = createInstance();
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
        List<ISong> unknown = r.getByGenre("unknown genre***");
        assertTrue(unknown.isEmpty());
    }

    @Test
    void mongoSongRepository_ShouldNot_GetSongWithUnknownId() {
        MongoSongRepository r = null;
        try {
            r = createInstance();
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
        Optional<ISong> emptySong = r.get(777777777L);
        assertTrue(emptySong.isEmpty());
    }



    @Test
    void mongoSongRepository_Should_UpdateSong() {
        MongoSongRepository r = null;
        ISong addedSong = null;
        try {
            r = createInstance();

            ISong newA = new Song();
            newA.setName("Band Of Mark");
            addedSong = r.add(newA);

            addedSong.setName("New name");
            r.update(addedSong);

            ISong newAddedSong = r.get(addedSong.getId()).get();
            assertEquals("New name", newAddedSong.getName());
        } catch (Exception ex) {
            fail(ex.getMessage());
        }

        ISong updatedSong = new MongoSongEntry();
        updatedSong.setId(addedSong.getId());
        updatedSong.setName("Updated name");

        try {
            r.update(updatedSong);
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
        assertEquals("Updated name", updatedSong.getName());
        assertEquals(addedSong.getId(), updatedSong.getId());

        ISong fetchedSong = r.get(addedSong.getId()).get();
        assertEquals("Updated name", fetchedSong.getName());
    }


    @Test
    void mongoSongRepository_Should_DeleteSong() {
        MongoSongRepository r = null;
        ISong toDelete = null;
        Optional<ISong> testSong = null;
        try {
            r = createInstance();
            toDelete = r.getAll().stream().filter(s -> !Objects.equals(s.getGenre(), "Metal")).findFirst().orElseThrow();
            r.delete(toDelete.getId());
            testSong = r.get(toDelete.getId());
            assertTrue(testSong.isEmpty());
        } catch (Exception ex) {
            fail(ex.getMessage());
        }

    }


}
