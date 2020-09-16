package rockstars.core.repositories;


import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import rockstars.api.models.IArtist;
import rockstars.core.models.Artist;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StaticArtistRepositoryTest {

    @Test
    void staticArtistRepository_Should_CreateAndLoadArtistJson() {
        try {
            StaticArtistRepository r = new StaticArtistRepository();

            assertEquals(888, r.getAll().size());
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    void staticArtistRepository_Should_AddNewArtistAndAssignId() {
        try {
            StaticArtistRepository r = new StaticArtistRepository();

            IArtist newA = new Artist();
            newA.setName("Band Of Mark");

            IArtist addedA = r.add(newA);

            assertTrue(r.getAll().contains(addedA));

        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    void staticArtistRepository_Should_GetArtistById() {
        StaticArtistRepository r = null;
        try {
            r = new StaticArtistRepository();
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
        Optional<IArtist> acdc = r.get(7L);
        assertTrue(acdc.isPresent());
        assertEquals("AC/DC", acdc.get().getName());
        assertEquals(7, acdc.get().getId());
    }


    @Test
     void staticArtistRepository_Should_GetArtistByName() {
        StaticArtistRepository r = null;
        try {
            r = new StaticArtistRepository();
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
        List<IArtist> acdc = r.getByName("AC/DC");
        assertEquals(1, acdc.size());
        assertEquals("AC/DC", acdc.get(0).getName());
        assertEquals(7L, acdc.get(0).getId());
    }

    @Test
    void staticArtistRepository_ShouldNot_GetArtistWithUnknownName() {
        StaticArtistRepository r = null;
        try {
            r = new StaticArtistRepository();
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
        List<IArtist> unknown = r.getByName("unknown name");
        assertTrue(unknown.isEmpty());
    }

    @Test
    void staticArtistRepository_ShouldNot_GetArtistWithUnknownId() {
        StaticArtistRepository r = null;
        try {
            r = new StaticArtistRepository();
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
        Optional<IArtist> emptyArtist = r.get(999999999L);
        assertTrue(emptyArtist.isEmpty());
    }



    @Test
    void staticArtistRepository_Should_UpdateArtist() {
        StaticArtistRepository r = null;
        IArtist addedArtist = null;
        try {
            r = new StaticArtistRepository();

            IArtist newA = new Artist();
            newA.setName("Band Of Mark");

            addedArtist = r.add(newA);
        } catch (Exception ex) {
            fail(ex.getMessage());
        }

        IArtist updatedArtist = new Artist(addedArtist.getId(), "Updated name");
        try {
            r.update(updatedArtist);
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
        assertEquals("Updated name", updatedArtist.getName());
        assertEquals(addedArtist.getId(), updatedArtist.getId());

        IArtist fetchedArtist = r.get(addedArtist.getId()).get();
        assertEquals("Updated name", fetchedArtist.getName());
    }


    @Test
    void staticArtistRepository_Should_DeleteArtist() {
        StaticArtistRepository r = null;
        try {
            r = new StaticArtistRepository();
            r.delete(7L);
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
        Optional<IArtist> acdc = r.get(7L);
        assertTrue(acdc.isEmpty());

    }
}
