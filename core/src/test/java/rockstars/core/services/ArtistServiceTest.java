package rockstars.core.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import rockstars.api.models.IArtist;
import rockstars.api.repositories.IArtistRepository;
import rockstars.api.services.IArtistService;
import rockstars.api.services.ServiceException;
import rockstars.core.models.Artist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ArtistServiceTest {

    private IArtistRepository getMockArtistRepository() {
        return mock(IArtistRepository.class);
    }

    @Test
    void artistService_Should_BeCreated() {
            IArtistService service = new ArtistService(getMockArtistRepository());

            assertTrue(Arrays.asList(service.getClass().getInterfaces()).contains(IArtistService.class));
    }

    @Test
    void artistService_Should_CallAddNewArtist() {
        try {
            IArtistRepository r = getMockArtistRepository();
            IArtistService service = new ArtistService(r);
            when(r.getByName(anyString())).thenReturn(new ArrayList<>());

            IArtist newA = mock(IArtist.class);

            service.add(newA);

            verify(r).add(newA);
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    void artistService_ShouldNot_CallAddNewArtist_With_ExistingName() {
        IArtistRepository r = getMockArtistRepository();
        IArtistService service = new ArtistService(r);

        List<IArtist> existingArtists = new ArrayList<>();
        IArtist existingArtist = mock(IArtist.class);
        existingArtists.add(existingArtist);

        when(r.getByName(anyString())).thenReturn(existingArtists);

        IArtist newA = mock(IArtist.class);
        when(newA.getName()).thenReturn("existingName");

        assertThrows(ServiceException.class, () -> service.add(newA));
    }

    @Test
    void artistService_Should_CallGetArtistById_And_ReturnArtist() {
        IArtistRepository r = getMockArtistRepository();
        IArtistService service = new ArtistService(r);

        IArtist existingArtist = mock(IArtist.class);
        when(existingArtist.getId()).thenReturn(7L);

        when(r.get(7L)).thenReturn(Optional.of(existingArtist));

        Optional<IArtist> result = service.get(7L);
        verify(r).get(7L);
        assertEquals(existingArtist, result.get());
    }

    @Test
    void artistService_Should_CallGetArtistById_WithUnknownId_AndReturnEmpty() {
        IArtistRepository r = getMockArtistRepository();
        IArtistService service = new ArtistService(r);

        when(r.get(7L)).thenReturn(Optional.empty());

        Optional<IArtist> result = service.get(7L);
        verify(r).get(7L);
        assertFalse(result.isPresent());
    }

    @Test
    void artistService_Should_CallUpdateArtist() {
        try {
            IArtistRepository r = getMockArtistRepository();
            IArtistService service = new ArtistService(r);

            IArtist existingArtist = mock(IArtist.class);
            when(existingArtist.getName()).thenReturn("existingName");
            when(existingArtist.getId()).thenReturn(20L);

            when(r.get(20L)).thenReturn(Optional.of(existingArtist));

            IArtist newA = mock(IArtist.class);
            when(newA.getId()).thenReturn(20L);
            when(newA.getName()).thenReturn("newName");

            when(r.getByName("newName")).thenReturn(new ArrayList<>());

            service.update(newA);

            verify(r).update(newA);

        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    void artistService_ShouldNot_CallUpdateArtist_With_ExistingName() {
        IArtistRepository r = getMockArtistRepository();
        IArtistService service = new ArtistService(r);

        IArtist existingArtist1 = mock(IArtist.class);
        when(existingArtist1.getName()).thenReturn("currentName");
        when(existingArtist1.getId()).thenReturn(20L);

        when(r.get(20L)).thenReturn(Optional.of(existingArtist1));

        IArtist existingArtist2 = mock(IArtist.class);

        when(r.getByName("newName")).thenReturn(Collections.singletonList(existingArtist2));

        IArtist updatedArtist = new Artist(existingArtist1.getId(), "newName");
        assertThrows(ServiceException.class, () -> service.update(updatedArtist));
    }

    @Test
    void artistService_ShouldNot_UpdateArtist_With_UnknownId() {
        IArtistRepository r = getMockArtistRepository();
        IArtistService service = new ArtistService(r);

        IArtist existingArtist1 = mock(IArtist.class);
        when(existingArtist1.getName()).thenReturn("currentName");
        when(existingArtist1.getId()).thenReturn(20L);

        when(r.get(20L)).thenReturn(Optional.empty());

        IArtist existingArtist2 = mock(IArtist.class);

        when(r.getByName("newName")).thenReturn(Collections.singletonList(existingArtist2));

        IArtist updatedArtist = new Artist(existingArtist1.getId(), "newName");
        assertThrows(ServiceException.class, () -> service.update(updatedArtist));
    }

    @Test
    void artistService_Should_CallDeleteArtist() {
        try {
            IArtistRepository r = getMockArtistRepository();
            IArtistService service = new ArtistService(r);

            service.delete(20L);

            verify(r).delete(20L);

        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    void artistService_Should_CallGetByName_And_ReturnArtistList() {
        IArtistRepository r = getMockArtistRepository();
        IArtistService service = new ArtistService(r);

        IArtist existingArtist1 = mock(IArtist.class);
        when(existingArtist1.getName()).thenReturn("currentName");
        when(existingArtist1.getId()).thenReturn(20L);

        when(r.getByName("existingName")).thenReturn(Collections.singletonList(existingArtist1));

        List<IArtist> actualArtist = service.getByName("existingName");

        verify(r).getByName("existingName");
        assertEquals(1, actualArtist.size());
        assertEquals(existingArtist1, actualArtist.get(0));
    }
}
