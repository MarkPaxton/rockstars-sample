package rockstars.core.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;

import rockstars.api.models.ISong;
import rockstars.api.repositories.ISongRepository;
import rockstars.api.repositories.RepositoryException;
import rockstars.core.models.MongoSongEntry;
import rockstars.core.models.MongoSongEntryBuilder;

import static com.mongodb.client.model.Filters.eq;

@Component
public class MongoSongRepository implements ISongRepository {
    @Autowired
    private final MongoClient client;

    @Autowired
    private final MongoSongEntryBuilder mongoSongBuilder;

    private final MongoCollection<MongoSongEntry> songsCollection;

    public MongoSongRepository(MongoClient client, MongoSongEntryBuilder mongoSongBuilder) {
        this.client = client;
        this.mongoSongBuilder = mongoSongBuilder;
        songsCollection = client.getDatabase("rockstars").getCollection("songs", MongoSongEntry.class);
    }

    @Override public List<ISong> getAll() {
        return songsCollection.find().into(new ArrayList<>());
    }

    @Override public Optional<ISong> get(Long id) {
        List<ISong> idSongs = songsCollection
                        .find(eq("Id", id))
                        .into(new ArrayList<>());
        return idSongs.stream().findFirst();
    }

    private Optional<ISong> getByObjectId(ObjectId _id) {
        List<ISong> idSongs = songsCollection
                        .find(eq("_id", _id))
                        .into(new ArrayList<>());
        return idSongs.stream().findFirst();
    }

    @Override public ISong add(ISong newItem) throws RepositoryException {
        MongoSongEntry mongoSong = (MongoSongEntry) mongoSongBuilder.Build(newItem);
        if(newItem.getId() == null) {
            Optional<MongoSongEntry> maxIdRecord = Optional.ofNullable(songsCollection.find().sort(new BasicDBObject("Id", -1)).limit(1).first());
            Long newId = maxIdRecord.map(mongoSongEntry -> mongoSongEntry.getId() + 1).orElse(1L);
            mongoSong.setId(newId);
        } else {
            if (get(newItem.getId()).isPresent()) {
                throw new RepositoryException("Item with ID " + newItem.getId() + " exists");
            }
        }
        InsertOneResult result = songsCollection.insertOne(mongoSong);
        if (result.wasAcknowledged()) {
            Optional<ISong> added = getByObjectId(Objects.requireNonNull(result.getInsertedId()).asObjectId().getValue());
            if(added.isPresent()) {
                return added.get();
            }
        }
        throw new RepositoryException("Could not add entry: " + result.toString());
    }

    @Override public void delete(Long id) throws RepositoryException {
        if(get(id).isEmpty()) {
            throw new RepositoryException("No song found with Id " + id);
        }
        DeleteResult result = songsCollection.deleteOne(eq("Id", id));
    }

    @Override public ISong update(ISong updatedItem) throws RepositoryException {
        Optional<ISong> songToUpdateOpt = get(updatedItem.getId());
        if (songToUpdateOpt.isEmpty()) {
            throw new RepositoryException("Item with ID " + updatedItem.getId() + " does not exist");
        }
        MongoSongEntry songToUpdate = ((MongoSongEntry) songToUpdateOpt.get()).updateFrom(updatedItem);
        return songsCollection.findOneAndReplace(eq("Id", songToUpdate.getId()), songToUpdate);
    }

    @Override public List<ISong> getByGenre(String genre) {
        return songsCollection
                        .find(eq("Genre", genre))
                        .into(new ArrayList<>());
    }

    @Override public List<ISong> getBeforeYear(Long year) {
        List<ISong> songs = songsCollection
                .find(Filters.lt("Year", year))
                .into(new ArrayList<>());
        return songs;
    }
}
