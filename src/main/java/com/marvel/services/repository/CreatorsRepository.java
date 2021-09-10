package com.marvel.services.repository;

import com.marvel.services.entity.CreatorsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreatorsRepository extends CrudRepository<CreatorsEntity, Long> {

    @Query("SELECT cre FROM CreatorsEntity cre WHERE cre.idCreator=:idCreator ")
    List<CreatorsEntity> findAllByIdCreator(@Param("idCreator") int idCreator);

    @Query(nativeQuery = true, value = "SELECT DISTINCT cre.Name FROM Creators cre LEFT JOIN CreatorsComics creco ON cre.IdCreator=creco.IdCreator LEFT JOIN Comics co ON creco.IdComic=co.IdComic LEFT JOIN CharactersComics chaco ON co.IdComic=chaco.IdComic LEFT JOIN Characters cha ON cha.IdCharacter=chaco.IdCharacter WHERE chaco.Active=true AND creco.Active=true AND replace(cha.Name, ' ', '') LIKE :character AND creco.Role LIKE :role ")
    List<String> findAllCreatorsByCharacterAndRole(@Param("character") String character, @Param("role") String role);
}
