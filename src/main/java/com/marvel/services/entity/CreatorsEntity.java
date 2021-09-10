package com.marvel.services.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "Creators")
public class CreatorsEntity {

    private int idCreator;
    private String name;

    public CreatorsEntity(){}

    public CreatorsEntity(int idCreator, String name){
        this.idCreator=idCreator;
        this.name=name;
    }

    @Id
    @Column(name = "IdCreator")
    public int getIdCreator() {
        return idCreator;
    }

    public void setIdCreator(int idCreator) {
        this.idCreator = idCreator;
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
        CreatorsEntity that = (CreatorsEntity) o;
        return idCreator == that.idCreator && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCreator, name);
    }
}
