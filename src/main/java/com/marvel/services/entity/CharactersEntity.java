package com.marvel.services.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Characters")
public class CharactersEntity {

    private int idCharacter;
    private String name;

    List<String> comics;

    public CharactersEntity(){}

    public CharactersEntity(int idCharacter, String name){
        this.idCharacter=idCharacter;
        this.name=name;
    }

    @Id
    @Column(name = "IdCharacter")
    public int getIdCharacter() {
        return idCharacter;
    }

    public void setIdCharacter(int idCharacter) {
        this.idCharacter = idCharacter;
    }

    @Basic
    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharactersEntity that = (CharactersEntity) o;
        return idCharacter == that.idCharacter && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCharacter, name);
    }

    @Transient
    public List<String> getComics() {
        return comics;
    }

    public void setComics(List<String> comics) {
        this.comics = comics;
    }
}
