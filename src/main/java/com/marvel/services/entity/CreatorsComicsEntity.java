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
@Table(name = "CreatorsComics")
public class CreatorsComicsEntity {

    private int idCreatorComic;
    private int idCreator;
    private int idComic;
    private String role;
    private boolean active;

    public CreatorsComicsEntity(){}

    public CreatorsComicsEntity(int idCreator, int idComic, String role, boolean active){
        this.idCreator=idCreator;
        this.idComic=idComic;
        this.role=role;
        this.active=active;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdCreatorComic")
    public int getIdCreatorComic() {
        return idCreatorComic;
    }

    public void setIdCreatorComic(int idCreatorComic) {
        this.idCreatorComic = idCreatorComic;
    }

    @Basic
    @Column(name = "IdCreator")
    public int getIdCreator() {
        return idCreator;
    }

    public void setIdCreator(int idCreator) {
        this.idCreator = idCreator;
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
    @Column(name = "Role")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
        CreatorsComicsEntity that = (CreatorsComicsEntity) o;
        return idCreatorComic == that.idCreatorComic && idCreator == that.idCreator && idComic == that.idComic && active == that.active && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCreatorComic, idCreator, idComic, role, active);
    }
}
