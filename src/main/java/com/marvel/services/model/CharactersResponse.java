package com.marvel.services.model;

import com.marvel.services.entity.CharactersEntity;

import java.sql.Timestamp;
import java.util.List;

public class CharactersResponse {

    private Timestamp lastSync;
    private List<CharactersEntity> characters;

    public Timestamp getLastSync() {
        return lastSync;
    }

    public void setLastSync(Timestamp lastSync) {
        this.lastSync = lastSync;
    }

    public List<CharactersEntity> getCharacters() {
        return characters;
    }

    public void setCharacters(List<CharactersEntity> characters) {
        this.characters = characters;
    }
}
