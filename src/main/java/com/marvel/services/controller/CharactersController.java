package com.marvel.services.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marvel.services.entity.CharactersEntity;
import com.marvel.services.model.CharactersResponse;
import com.marvel.services.model.JsonResponse;
import com.marvel.services.repository.CharactersRepository;
import com.marvel.services.repository.ComicsRepository;
import com.marvel.services.repository.CreatorsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/marvel/characters")
public class CharactersController {

    @Autowired
    ComicsRepository comicsRepository;

    @Autowired
    CreatorsRepository creatorsRepository;

    @Autowired
    CharactersRepository charactersRepository;

    private final Logger log = LoggerFactory.getLogger(CharactersController.class.getName());

    @GetMapping("/{character}")
    public ResponseEntity<Object> findAllPartnerCharactersByCharacter(@PathVariable("character") String character) throws JsonProcessingException {

        JsonResponse response = new JsonResponse();
        ObjectMapper mapper = new ObjectMapper();

        try {
            CharactersResponse charactersResponse = new CharactersResponse();
            String characterParam = '%' + character.trim().replace(" ", "") + '%';

            List<Timestamp> lastSync = comicsRepository.findLastModified(PageRequest.of(0, 1, Sort.Direction.DESC, "lastModified"));
            if (lastSync != null && !lastSync.isEmpty()) {
                charactersResponse.setLastSync(lastSync.get(0));
            }

            List<CharactersEntity> charactersFound = charactersRepository.findAllPartnerCharacterByCharacter(characterParam);
            if (charactersFound != null && !charactersFound.isEmpty()) {

                for (CharactersEntity characterFound : charactersFound) {

                    List<String> comics = comicsRepository.findAllTitlesByIdCharacter(characterFound.getIdCharacter());
                    if (comics != null && !comics.isEmpty()) {
                        characterFound.setComics(comics);
                    }
                }
                charactersResponse.setCharacters(charactersFound);
            }

            return new ResponseEntity<>(charactersResponse, HttpStatus.OK);

        } catch (Exception e) {
            log.error("**** Error getting partner characters by character **** " + e.getMessage() + " line: " + e.getStackTrace()[0].getLineNumber());
            response.setMessage("Error getting partner characters by character");
            response.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));

            return new ResponseEntity<>(mapper.writeValueAsString(response), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
