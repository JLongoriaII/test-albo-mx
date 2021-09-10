package com.marvel.services.repository;

import com.marvel.services.entity.ComicsEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface ComicsRepository extends CrudRepository<ComicsEntity, Long> {

    @Query("SELECT co FROM ComicsEntity co WHERE co.id=:id ")
    List<ComicsEntity> findAllByIdComic(@Param("id") int id);

    @Query("SELECT co.lastModified FROM ComicsEntity co ")
    List<Timestamp> findLastModified(Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT DISTINCT com.Title FROM Comics com LEFT JOIN CharactersComics chaco ON com.IdComic=chaco.IdComic WHERE chaco.Active=true AND chaco.IdComic IN (SELECT DISTINCT co.IdComic FROM Comics co LEFT JOIN CharactersComics chaco ON co.IdComic=chaco.IdComic LEFT JOIN Characters cha ON cha.IdCharacter=chaco.IdCharacter WHERE chaco.Active=true AND cha.Name='Iron Man') AND chaco.IdCharacter=:idCharacter ")
    List<String> findAllTitlesByIdCharacter(@Param("idCharacter") int idCharacter);
}
