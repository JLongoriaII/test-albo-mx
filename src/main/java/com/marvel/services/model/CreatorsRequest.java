package com.marvel.services.model;

import com.marvel.services.entity.CreatorsEntity;

import java.util.List;

public class CreatorsRequest {

    private int available;
    private String collectionURI;
    private List<CreatorsModel> items;

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

    public List<CreatorsModel> getItems() {
        return items;
    }

    public void setItems(List<CreatorsModel> items) {
        this.items = items;
    }
}
