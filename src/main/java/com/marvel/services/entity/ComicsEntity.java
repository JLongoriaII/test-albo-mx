package com.marvel.services.entity;

import com.marvel.services.model.CharactersRequest;
import com.marvel.services.model.CreatorsRequest;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "Comics")
public class ComicsEntity {

    private int id;
    private String title;
    private String description;
    private Timestamp lastModified;
    private String format;

    private CreatorsRequest creators;
    private CharactersRequest characters;

    @Id
    @Column(name = "IdComic")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "Description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "LastModified")
    public Timestamp getLastModified() {
        return lastModified;
    }

    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }

    @Basic
    @Column(name = "Format")
    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComicsEntity that = (ComicsEntity) o;
        return id == that.id && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(lastModified, that.lastModified) && Objects.equals(format, that.format);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, lastModified, format);
    }

    @Transient
    public CreatorsRequest getCreators() {
        return creators;
    }

    public void setCreators(CreatorsRequest creators) {
        this.creators = creators;
    }

    @Transient
    public CharactersRequest getCharacters() {
        return characters;
    }

    public void setCharacters(CharactersRequest characters) {
        this.characters = characters;
    }
}
