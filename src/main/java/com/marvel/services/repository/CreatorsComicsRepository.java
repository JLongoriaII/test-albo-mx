package com.marvel.services.repository;

import com.marvel.services.entity.CreatorsComicsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreatorsComicsRepository extends CrudRepository<CreatorsComicsEntity, Long> {

    @Query("SELECT creco FROM CreatorsComicsEntity creco WHERE creco.idCreator=:idCreator AND creco.idComic=:idComic ")
    List<CreatorsComicsEntity> findAllByIdCreatorAndIdComic(@Param("idCreator") int idCreator, @Param("idComic") int idComic);
}
