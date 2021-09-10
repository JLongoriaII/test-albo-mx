package com.marvel.services.repository;

import com.marvel.services.entity.CharactersComicsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharactersComicsRepository extends CrudRepository<CharactersComicsEntity, Long> {

    @Query("SELECT cce FROM CharactersComicsEntity cce WHERE cce.idCharacter=:idCharacter AND cce.idComic=:idComic ")
    List<CharactersComicsEntity> findAllByIdCharacterAndIdComic(@Param("idCharacter") int idCharacter, @Param("idComic") int idComic);
}
