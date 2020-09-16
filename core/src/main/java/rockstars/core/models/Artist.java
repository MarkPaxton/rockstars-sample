package rockstars.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import rockstars.api.models.IArtist;

public class Artist implements IArtist {
    protected Long id;

    protected String name;

    public Artist() {
    }

    public Artist(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    @JsonProperty("Id")
    public Long getId() {
        return id;
    };

    @Override
    @JsonProperty("Name")
    public String getName() {
        return name;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
