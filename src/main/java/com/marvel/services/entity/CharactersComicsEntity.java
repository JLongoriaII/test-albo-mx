package com.marvel.services.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "CharactersComics")
public class CharactersComicsEntity {

    private int idCharacterComic;
    private int idCharacter;
    private int idComic;
    private boolean active;

    public CharactersComicsEntity(){}

    public CharactersComicsEntity(int idCharacter, int idComic, boolean active){
        this.idCharacter=idCharacter;
        this.idComic=idComic;
        this.active=active;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdCharacterComic")
    public int getIdCharacterComic() {
        return idCharacterComic;
    }

    public void setIdCharacterComic(int idCharacterComic) {
        this.idCharacterComic = idCharacterComic;
    }

    @Basic
    @Column(name = "IdCharacter")
    public int getIdCharacter() {
        return idCharacter;
    }

    public void setIdCharacter(int idCharacter) {
        this.idCharacter = idCharacter;
    }

    @Basic
    @Column(name = "IdComic")
    public int getIdComic() {
        return idComic;
    }

    public void setIdComic(int idComic) {
        this.idComic = idComic;
    }

    @Basic
    @Column(name = "Active")
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharactersComicsEntity that = (CharactersComicsEntity) o;
        return idCharacterComic == that.idCharacterComic && idCharacter == that.idCharacter && idComic == that.idComic && active == that.active;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCharacterComic, idCharacter, idComic, active);
    }
}
