package rockstars.api.models;

public interface ISongBuilder {
    ISong Build();

    ISong Build(ISong song);

    ISong Build(Long id, String name, Long year, String artist, String shortName, Long bpm, Long duration, String genre, String spotifyId,
                    String album);
}
