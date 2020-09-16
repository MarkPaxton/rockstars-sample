package rockstars.api.models;

public interface ISong {
    Long getId();

    String getName();

    Long getYear();

    String getArtist();

    String getShortName();

    Long getBpm();

    Long getDuration();

    String getGenre();
    
    String getSpotifyId();
    
    String getAlbum();

    void setId(Long id);

    void setName(String name);

    void setYear(Long year);

    void setArtist(String artist);

    void setShortName(String shortName);

    void setBpm(Long bpm);

    void setDuration(Long duration);

    void setGenre(String genre);

    void setSpotifyId(String spotifyId);

    void setAlbum(String album);
}
