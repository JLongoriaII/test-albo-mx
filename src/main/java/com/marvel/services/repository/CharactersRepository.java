package com.marvel.services.repository;

import com.marvel.services.entity.CharactersEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharactersRepository extends CrudRepository<CharactersEntity, Long> {

    @Query("SELECT ce FROM CharactersEntity ce WHERE ce.idCharacter=:idCharacter ")
    List<CharactersEntity> findAllByIdCharacter(@Param("idCharacter") int idCharacter);

    @Query(nativeQuery = true, value = "SELECT DISTINCT cha.* FROM Characters cha LEFT JOIN CharactersComics chaco ON cha.IdCharacter=chaco.IdCharacter WHERE chaco.Active=true AND chaco.IdComic IN (SELECT DISTINCT co.IdComic FROM Comics co LEFT JOIN CharactersComics chaco ON co.IdComic=chaco.IdComic LEFT JOIN Characters cha ON cha.IdCharacter=chaco.IdCharacter WHERE chaco.Active=true AND replace(cha.Name, ' ', '') LIKE :character )")
    List<CharactersEntity> findAllPartnerCharacterByCharacter(@Param("character") String character);
}
