package rockstars.core.models;

import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonProperty;

import rockstars.api.models.ISong;

public class Song implements ISong {
    protected Long Id;
    protected String Name;
    protected Long Year;
    protected String Artist;
    protected String ShortName;
    protected Long Bpm;
    protected Long Duration;
    protected String Genre;
    protected String SpotifyId;
    protected String Album;

    public Song() {
    }

    public Song(Long id, String name, Long year, String artist, String shortName, Long bpm, Long duration, String genre, String spotifyId,
                    String album) {
        Id = id;
        Name = name;
        Year = year;
        Artist = artist;
        ShortName = shortName;
        Bpm = bpm;
        Duration = duration;
        Genre = genre;
        SpotifyId = spotifyId;
        Album = album;
    }

    @JsonProperty("Id")
    @Override public Long getId() {
        return Id;
    }

    @Override public void setId(Long id) {
        Id = id;
    }

    @JsonProperty("Name")
    @Override public String getName() {
        return Name;
    }

    @Override public void setName(String name) {
        Name = name;
    }

    @JsonProperty("Year")
    @Override public Long getYear() {
        return Year;
    }

    @Override public void setYear(Long year) {
        Year = year;
    }

    @JsonProperty("Artist")
    @Override public String getArtist() {
        return Artist;
    }

    @Override public void setArtist(String artist) {
        Artist = artist;
    }

    @JsonProperty("ShortName")
    @Override public String getShortName() {
        return ShortName;
    }

    @Override public void setShortName(String shortName) {
        ShortName = shortName;
    }

    @JsonProperty("Bpm")
    @Override public Long getBpm() {
        return Bpm;
    }

    @Override public void setBpm(Long bpm) {
        Bpm = bpm;
    }

    @JsonProperty("Duration")
    @Override public Long getDuration() {
        return Duration;
    }

    @Override public void setDuration(Long duration) {
        Duration = duration;
    }

    @JsonProperty("Genre")
    @Override public String getGenre() {
        return Genre;
    }

    @Override public void setGenre(String genre) {
        Genre = genre;
    }

    @JsonProperty("SpotifyId")
    @Override public String getSpotifyId() {
        return SpotifyId;
    }

    @Override public void setSpotifyId(String spotifyId) {
        SpotifyId = spotifyId;
    }

    @JsonProperty("Album")
    @Override public String getAlbum() {
        return Album;
    }

    @Override public void setAlbum(String album) {
        Album = album;
    }
}
