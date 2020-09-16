package rockstars.core.models;

import org.springframework.stereotype.Component;

import rockstars.api.models.ISong;
import rockstars.api.models.ISongBuilder;

@Component("MongoSongEntry")
public class MongoSongEntryBuilder implements ISongBuilder {

    @Override public ISong Build() {
        return new MongoSongEntry();
    }

    @Override public ISong Build(ISong song) {
        return new MongoSongEntry(song.getId(), song.getName(), song.getYear(), song.getArtist(), song.getShortName(),
                        song.getBpm(), song.getDuration(), song.getGenre(), song.getSpotifyId(), song.getAlbum());
    }

    @Override public ISong Build(Long id, String name, Long year, String artist, String shortName, Long bpm, Long duration, String genre,
                    String spotifyId, String album) {
        return new MongoSongEntry(id, name, year, artist, shortName, bpm, duration, genre, spotifyId, album);
    }
}
