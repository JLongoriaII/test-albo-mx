package com.marvel.services.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marvel.services.entity.CharactersComicsEntity;
import com.marvel.services.entity.CharactersEntity;
import com.marvel.services.entity.ComicsEntity;
import com.marvel.services.entity.CreatorsComicsEntity;
import com.marvel.services.entity.CreatorsEntity;
import com.marvel.services.model.BasedRequest;
import com.marvel.services.model.CharactersModel;
import com.marvel.services.model.CreatorsModel;
import com.marvel.services.model.GenericRequest;
import com.marvel.services.repository.CharactersComicsRepository;
import com.marvel.services.repository.CharactersRepository;
import com.marvel.services.repository.ComicsRepository;
import com.marvel.services.repository.CreatorsComicsRepository;
import com.marvel.services.repository.CreatorsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/marvel")
public class MarvelController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ComicsRepository comicsRepository;

    @Autowired
    private CreatorsRepository creatorsRepository;

    @Autowired
    private CharactersRepository charactersRepository;

    @Autowired
    private CreatorsComicsRepository creatorsComicsRepository;

    @Autowired
    private CharactersComicsRepository charactersComicsRepository;

    @Value("${marvel-private-key}")
    private String privateKey;

    @Value("${marvel-public-key}")
    private String publicKey;

    @Value("${marvel-url-request}")
    private String urlRequest;

    private static final int TIMESTAMP = 1;

    private final Logger log = LoggerFactory.getLogger(MarvelController.class.getName());


    @GetMapping("")
    @Scheduled(cron = "0/15 0 0,1 * * ?", zone = "America/Mexico_City")
    public void getAllDataMarvel() {

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            Timestamp fecha = new Timestamp(new Date().getTime());

            String beforeHash = TIMESTAMP + privateKey + publicKey;
            String hash = DigestUtils.md5DigestAsHex(beforeHash.getBytes());

            String url = urlRequest + "/comics?limit=50&ts=" + TIMESTAMP + "&apikey=" + publicKey + "&hash=" + hash;

            ResponseEntity<BasedRequest> requestApi = restTemplate.getForEntity(new URI(url), BasedRequest.class);
            if (requestApi.getStatusCodeValue() == 200) {

                BasedRequest firstRequest = requestApi.getBody();
                if (firstRequest != null) {

                    GenericRequest genericRequest = firstRequest.getData();
                    if (genericRequest.getResults() != null && !genericRequest.getResults().isEmpty()) {

                        List<ComicsEntity> comicsList = new ArrayList<>();
                        List<CreatorsEntity> creatorsList = new ArrayList<>();
                        List<CharactersEntity> charactersList = new ArrayList<>();
                        List<CreatorsComicsEntity> creatorsComicsList = new ArrayList<>();
                        List<CharactersComicsEntity> charactersComicsList = new ArrayList<>();

                        for (Object comicObject : genericRequest.getResults()) {
                            ComicsEntity comic = mapper.convertValue(comicObject, ComicsEntity.class);

                            List<ComicsEntity> comicFound = comicsRepository.findAllByIdComic(comic.getId());
                            if (comicFound != null && !comicFound.isEmpty()) {

                                if (!comic.equals(comicFound.get(0))) {

                                    comicFound.get(0).setTitle(comic.getTitle());
                                    comicFound.get(0).setFormat(comic.getFormat());
                                    comicFound.get(0).setDescription(comic.getDescription());
                                    comicFound.get(0).setLastModified(fecha);
                                    comicsList.add(comicFound.get(0));
                                }
                            } else {
                                comic.setLastModified(fecha);
                                comicsList.add(comic);
                            }

                            if (comic.getCreators().getItems() != null && !comic.getCreators().getItems().isEmpty()) {

                                for (CreatorsModel creatorsModel : comic.getCreators().getItems()) {

                                    String[] urlID = creatorsModel.getResourceURI().split("/");
                                    int idCreator = Integer.parseInt(urlID[urlID.length - 1]);

                                    List<CreatorsEntity> creatorsFound = creatorsRepository.findAllByIdCreator(idCreator);
                                    if (creatorsFound != null && !creatorsFound.isEmpty()) {

                                        if (!creatorsModel.getName().equals(creatorsFound.get(0).getName())) {
                                            creatorsFound.get(0).setName(creatorsModel.getName());
                                            creatorsList.add(creatorsFound.get(0));
                                        }

                                        List<CreatorsComicsEntity> creatorsComicsFound = creatorsComicsRepository.findAllByIdCreatorAndIdComic(idCreator, comic.getId());
                                        if (creatorsComicsFound != null && !creatorsComicsFound.isEmpty()) {

                                            if (!creatorsComicsFound.get(0).isActive()) {
                                                creatorsComicsFound.get(0).setActive(true);
                                                creatorsComicsList.add(creatorsComicsFound.get(0));
                                            }
                                        } else {
                                            creatorsComicsList.add(new CreatorsComicsEntity(idCreator, comic.getId(), creatorsModel.getRole(), true));
                                        }

                                    } else {
                                        creatorsList.add(new CreatorsEntity(idCreator, creatorsModel.getName()));

                                        List<CreatorsComicsEntity> creatorsComicsFound = creatorsComicsRepository.findAllByIdCreatorAndIdComic(idCreator, comic.getId());
                                        if (creatorsComicsFound != null && !creatorsComicsFound.isEmpty()) {

                                            if (!creatorsComicsFound.get(0).isActive()) {
                                                creatorsComicsFound.get(0).setActive(true);
                                                creatorsComicsList.add(creatorsComicsFound.get(0));
                                            }
                                        } else {
                                            creatorsComicsList.add(new CreatorsComicsEntity(idCreator, comic.getId(), creatorsModel.getRole(), true));
                                        }
                                    }
                                }
                            }

                            if (comic.getCharacters().getItems() != null && !comic.getCharacters().getItems().isEmpty()) {

                                for (CharactersModel charactersModel : comic.getCharacters().getItems()) {

                                    String[] urlID = charactersModel.getResourceURI().split("/");
                                    int idCharacter = Integer.parseInt(urlID[urlID.length - 1]);

                                    List<CharactersEntity> charactersFound = charactersRepository.findAllByIdCharacter(idCharacter);
                                    if (charactersFound != null && !charactersFound.isEmpty()) {

                                        if (!charactersModel.getName().equals(charactersFound.get(0).getName())) {
                                            charactersFound.get(0).setName(charactersModel.getName());
                                            charactersList.add(charactersFound.get(0));
                                        }

                                        List<CharactersComicsEntity> charactersComics = charactersComicsRepository.findAllByIdCharacterAndIdComic(idCharacter, comic.getId());
                                        if (charactersComics != null && !charactersComics.isEmpty()) {

                                            if (!charactersComics.get(0).isActive()) {
                                                charactersComics.get(0).setActive(true);
                                                charactersComicsList.add(charactersComics.get(0));
                                            }
                                        } else {
                                            charactersComicsList.add(new CharactersComicsEntity(idCharacter, comic.getId(), true));
                                        }

                                    } else {
                                        charactersList.add(new CharactersEntity(idCharacter, charactersModel.getName()));

                                        List<CharactersComicsEntity> charactersComics = charactersComicsRepository.findAllByIdCharacterAndIdComic(idCharacter, comic.getId());
                                        if (charactersComics != null && !charactersComics.isEmpty()) {

                                            if (!charactersComics.get(0).isActive()) {
                                                charactersComics.get(0).setActive(true);
                                                charactersComicsList.add(charactersComics.get(0));
                                            }
                                        } else {
                                            charactersComicsList.add(new CharactersComicsEntity(idCharacter, comic.getId(), true));
                                        }
                                    }
                                }
                            }
                        }

                        comicsRepository.saveAll(comicsList);
                        creatorsRepository.saveAll(creatorsList);
                        charactersRepository.saveAll(charactersList);
                        creatorsComicsRepository.saveAll(creatorsComicsList);
                        charactersComicsRepository.saveAll(charactersComicsList);

                    } else {
                        log.info("*** Without data from Maven API (comics) ***");
                    }
                } else {
                    log.error("**** Error getting data from Marvel API 3 (comics) ****");
                }
            } else {
                log.error("**** Error getting data from Marvel API 2 (comics) ****");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("**** Error getting data from Marvel API **** " + e.getMessage() + " line: " + e.getStackTrace()[0].getLineNumber());
        }
    }

}
