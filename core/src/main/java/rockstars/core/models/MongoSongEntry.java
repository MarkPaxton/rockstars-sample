package rockstars.core.models;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import rockstars.api.models.ISong;

public class MongoSongEntry extends Song implements ISong {

    @BsonId()
    private ObjectId _id;

    public MongoSongEntry() {
        super();
    }

    @BsonCreator
    public MongoSongEntry(@BsonProperty("_id") ObjectId _id,
                    @BsonProperty("Id") Long id,
                    @BsonProperty("Name") String name,
                    @BsonProperty("Year") Long year,
                    @BsonProperty("Artist") String artist,
                    @BsonProperty("ShortName") String shortName,
                    @BsonProperty("Bpm") Long bpm,
                    @BsonProperty("Duration")Long duration,
                    @BsonProperty("Genre") String genre,
                    @BsonProperty("SpotifyId")String spotifyId,
                    @BsonProperty("Album") String album) {
        super(id, name, year, artist, shortName, bpm, duration, genre, spotifyId, album);
        this._id = _id;
    }


    public MongoSongEntry(Long id, String name, Long year, String artist, String shortName, Long bpm, Long duration, String genre, String spotifyId,
                    String album) {
        super(id, name, year, artist, shortName, bpm, duration, genre, spotifyId, album);
    }

    public MongoSongEntry updateFrom(ISong song) {
            this.Id = song.getId();
            Name = song.getName();
            Year = song.getYear();
            Artist = song.getArtist();
            ShortName = song.getShortName();
            Bpm = song.getBpm();
            Duration = song.getDuration();
            Genre = song.getGenre();
            SpotifyId = song.getSpotifyId();
            Album = song.getAlbum();
            return this;
    }

    @BsonProperty("_id")
    public ObjectId getiD() {
        return _id;
    }

    public void setiD(ObjectId value) {
        _id = value;
    }

    @BsonProperty("Id")
    @Override public Long getId() {
        return super.getId();
    }

    @BsonProperty("Name")
    @Override public String getName() {
        return super.getName();
    }

    @BsonProperty("Year")
    @Override public Long getYear() {
        return super.getYear();
    }

    @BsonProperty("Artist")
    @Override public String getArtist() {
        return super.getArtist();
    }

    @BsonProperty("ShortName")
    @Override public String getShortName() {
        return super.getShortName();
    }

    @BsonProperty("Bpm")
    @Override public Long getBpm() {
        return super.getBpm();
    }

    @BsonProperty("Duration")
    @Override public Long getDuration() {
        return super.getDuration();
    }

    @BsonProperty("Genre")
    @Override public String getGenre() {
        return super.getGenre();
    }

    @BsonProperty("SpotifyId")
    @Override public String getSpotifyId() {
        return super.getSpotifyId();
    }

    @BsonProperty("Album")
    @Override public String getAlbum() {
        return super.getAlbum();
    }
}
