package rockstars.core.models;

import org.springframework.stereotype.Component;

import rockstars.api.models.ISong;
import rockstars.api.models.ISongBuilder;

@Component("Song")
public class SongBuilder implements ISongBuilder {
    @Override public ISong Build() {
        return new Song();
    }

    @Override public ISong Build(ISong song) {
        return new Song(song.getId(), song.getName(), song.getYear(), song.getArtist(), song.getShortName(),
                        song.getBpm(), song.getDuration(), song.getGenre(), song.getSpotifyId(), song.getAlbum());
    }

    @Override public ISong Build(Long id, String name, Long year, String artist, String shortName, Long bpm, Long duration, String genre,
                    String spotifyId,
                    String album) {
        return new Song(id, name, year, artist, shortName, bpm, duration, genre, spotifyId, album);
    }

}
