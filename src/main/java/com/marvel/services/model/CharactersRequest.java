package com.marvel.services.model;

import java.util.List;

public class CharactersRequest {

    private int available;
    private String collectionURI;
    private List<CharactersModel> items;

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public String getCollectionURI() {
        return collectionURI;
    }

    public void setCollectionURI(String collectionURI) {
        this.collectionURI = collectionURI;
    }

    public List<CharactersModel> getItems() {
        return items;
    }

    public void setItems(List<CharactersModel> items) {
        this.items = items;
    }
}
