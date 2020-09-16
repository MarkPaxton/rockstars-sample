package rockstars.core.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import rockstars.api.models.ISong;
import rockstars.api.repositories.ISongRepository;
import rockstars.api.repositories.RepositoryException;
import rockstars.api.services.ISongService;
import rockstars.api.services.ServiceException;
import rockstars.core.models.Song;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SongServiceTest {

    private ISongRepository getMockSongRepository() {
        return mock(ISongRepository.class);
    }

    @Test
    void songService_Should_BeCreated() {
            ISongService service = new SongService(getMockSongRepository());

            assertTrue(Arrays.asList(service.getClass().getInterfaces()).contains(ISongService.class));
    }

    @Test
    void songService_Should_CallAddNewSong() {
        try {
            ISongRepository r = getMockSongRepository();
            ISongService service = new SongService(r);
           
            ISong newA = mock(ISong.class);

            service.add(newA);

            verify(r).add(newA);
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    void songService_Should_CallGetSongById_And_ReturnSong() {
        ISongRepository r = getMockSongRepository();
        ISongService service = new SongService(r);

        ISong existingSong = mock(ISong.class);
        when(existingSong.getId()).thenReturn(7L);

        when(r.get(7L)).thenReturn(Optional.of(existingSong));

        Optional<ISong> result = service.get(7L);
        verify(r).get(7L);
        assertEquals(existingSong, result.get());
    }

    @Test
    void songService_Should_CallGetSongById_WithUnknownId_AndReturnEmpty() {
        ISongRepository r = getMockSongRepository();
        ISongService service = new SongService(r);

        when(r.get(7L)).thenReturn(Optional.empty());

        Optional<ISong> result = service.get(7L);
        verify(r).get(7L);
        assertFalse(result.isPresent());
    }

    @Test
    void songService_Should_CallUpdateSong() {
        try {
            ISongRepository r = getMockSongRepository();
            ISongService service = new SongService(r);

            ISong existingSong = mock(ISong.class);
            when(existingSong.getName()).thenReturn("existingName");
            when(existingSong.getId()).thenReturn(20L);

            when(r.get(20L)).thenReturn(Optional.of(existingSong));

            ISong newA = mock(ISong.class);
            when(newA.getId()).thenReturn(20L);
            when(newA.getName()).thenReturn("newName");

            service.update(newA);

            verify(r).update(newA);

        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    void songService_ShouldNot_UpdateSong_With_UnknownId()  {
        ISongRepository r = getMockSongRepository();
        ISongService service = new SongService(r);

        ISong existingSong1 = mock(ISong.class);
        when(existingSong1.getName()).thenReturn("currentName");
        when(existingSong1.getId()).thenReturn(20L);

        ISong updatedSong = new Song(existingSong1.getId(), "TestSong", 1990L, "ArtistRef", "sName", 120L, 300L, "Jazz", "SpotifyId123", "AlbumName");


        assertThrows(ServiceException.class, () -> {
            when(r.update(updatedSong)).thenThrow(new RepositoryException("repository exception"));
            service.update(updatedSong);
        });
    }

    @Test
    void songService_Should_CallDeleteSong() {
        try {
            ISongRepository r = getMockSongRepository();
            ISongService service = new SongService(r);

            service.delete(20L);

            verify(r).delete(20L);

        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    void songService_Should_CallGetByGenre_And_ReturnSongList() {
        ISongRepository r = getMockSongRepository();
        ISongService service = new SongService(r);

        ISong existingSong1 = mock(ISong.class);
        when(existingSong1.getGenre()).thenReturn("genre");
        when(existingSong1.getId()).thenReturn(20L);

        when(r.getByGenre("genre")).thenReturn(Collections.singletonList(existingSong1));


        List<ISong> actualSongs = service.getByGenre("genre");

        verify(r).getByGenre("genre");
        assertEquals(1, actualSongs.size());
        assertEquals(existingSong1, actualSongs.get(0));
    }

    @Test
    void songService_Should_CallGetBeforeYear_And_ReturnSongList() {
        ISongRepository r = getMockSongRepository();
        ISongService service = new SongService(r);

        ISong existingSong1 = mock(ISong.class);
        when(existingSong1.getYear()).thenReturn(1910L);
        when(existingSong1.getId()).thenReturn(20L);

        when(r.getBeforeYear(anyLong())).thenReturn(Collections.singletonList(existingSong1));


        List<ISong> actualSongs = service.getBeforeYear(1980L);

        verify(r).getBeforeYear(1980L);
        assertEquals(1, actualSongs.size());
        assertEquals(existingSong1, actualSongs.get(0));
    }
}
