package com.marvel.services.model;

import java.sql.Timestamp;
import java.util.List;

public class ColaboratorsResponse {

    private Timestamp lastSync;
    private List<String> editors;
    private List<String> writers;
    private List<String> colorists;
    private List<String> pencilers;

    public Timestamp getLastSync() {
        return lastSync;
    }

    public void setLastSync(Timestamp lastSync) {
        this.lastSync = lastSync;
    }

    public List<String> getEditors() {
        return editors;
    }

    public void setEditors(List<String> editors) {
        this.editors = editors;
    }

    public List<String> getWriters() {
        return writers;
    }

    public void setWriters(List<String> writers) {
        this.writers = writers;
    }

    public List<String> getColorists() {
        return colorists;
    }

    public void setColorists(List<String> colorists) {
        this.colorists = colorists;
    }

    public List<String> getPencilers() {
        return pencilers;
    }

    public void setPencilers(List<String> pencilers) {
        this.pencilers = pencilers;
    }
}
