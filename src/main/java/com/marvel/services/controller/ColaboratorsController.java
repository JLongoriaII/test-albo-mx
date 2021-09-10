package com.marvel.services.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marvel.services.model.ColaboratorsResponse;
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
@RequestMapping("/marvel/colaborators")
public class ColaboratorsController {

    @Autowired
    private ComicsRepository comicsRepository;

    @Autowired
    private CreatorsRepository creatorsRepository;

    private final Logger log = LoggerFactory.getLogger(ColaboratorsController.class.getName());

    private static final String WRITER_ROLE = "%writ%";
    private static final String EDITOR_ROLE = "%edit%";
    private static final String COLORIST_ROLE = "%color%";
    private static final String PENCILER_ROLE = "%pencil%";

    @GetMapping("/{character}")
    public ResponseEntity<Object> findAllColaboratorsByCharacter(@PathVariable("character") String character) throws JsonProcessingException {

        JsonResponse response = new JsonResponse();
        ObjectMapper mapper = new ObjectMapper();
        try {
            ColaboratorsResponse colaboratorsResponse = new ColaboratorsResponse();
            String characterParam = '%' + character.trim().replace(" ", "") + '%';

            List<Timestamp> lastSync = comicsRepository.findLastModified(PageRequest.of(0, 1, Sort.Direction.DESC, "lastModified"));
            if (lastSync != null && !lastSync.isEmpty()) {
                colaboratorsResponse.setLastSync(lastSync.get(0));
            }

            List<String> writers = creatorsRepository.findAllCreatorsByCharacterAndRole(characterParam, WRITER_ROLE);
            if (writers != null && !writers.isEmpty()) {
                colaboratorsResponse.setWriters(writers);
            }

            List<String> colorists = creatorsRepository.findAllCreatorsByCharacterAndRole(characterParam, COLORIST_ROLE);
            if (colorists != null && !colorists.isEmpty()) {
                colaboratorsResponse.setColorists(colorists);
            }

            List<String> editors = creatorsRepository.findAllCreatorsByCharacterAndRole(characterParam, EDITOR_ROLE);
            if (editors != null && !editors.isEmpty()) {
                colaboratorsResponse.setEditors(editors);
            }

            List<String> pencilers = creatorsRepository.findAllCreatorsByCharacterAndRole(characterParam, PENCILER_ROLE);
            if (pencilers != null && !pencilers.isEmpty()) {
                colaboratorsResponse.setPencilers(pencilers);
            }

            return new ResponseEntity<>(colaboratorsResponse, HttpStatus.OK);

        } catch (Exception e) {
            log.error("**** Error getting collaborators by character **** " + e.getMessage() + " line: " + e.getStackTrace()[0].getLineNumber());
            response.setMessage("Error getting collaborators by character");
            response.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));

            return new ResponseEntity<>(mapper.writeValueAsString(response), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
