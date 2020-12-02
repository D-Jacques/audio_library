package com.myaudiolibrary.web.controller;

import com.myaudiolibrary.web.model.Artist;
import com.myaudiolibrary.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.ServletContext;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

//The request mapping is the route to access the controller
//@CrossOrigin
//@RestController
@Controller
@RequestMapping(value = "/artists")
public class ArtistController {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    ServletContext context;

    //GET /artists/1
    @RequestMapping(method = RequestMethod.GET, value = "/{idArtist}")
    public String getArtist(
            final ModelMap modelArtist,
            @PathVariable("idArtist") Integer idArtist
    ){
        Optional<Artist> artistOptional = artistRepository.findById(idArtist);
        modelArtist.put("Artist", artistOptional.get());
        return "detailArtist";
    }

    //GET /artists?name=aerosmith (Sous forme de Page)
    @RequestMapping(method = RequestMethod.GET, params = {"name"})
    public String getArtistByName(
            final ModelMap artistMap,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sortProperty", defaultValue = "name") String sortProperty,
            @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection
    ){
        /*if(page < 0){
            throw new IllegalArgumentException("La valeur Page ne peut pas être négative !");
        }
        if(size <= 0 || size >= 50){
            throw new IllegalArgumentException("La valeur de la taille ne peux pas être nulle ou négative ou supérieur à 50!");
        }
        if(!("ASC".equalsIgnoreCase(sortDirection)) && !("DESC".equalsIgnoreCase(sortDirection))){
            throw new IllegalArgumentException("Le paramètre sortDirection doit valoir soit ASC soit DESC");
        }*/

        PageRequest pageRequest =  PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sortProperty);
        Page<Artist> artistList= artistRepository.findAllByNameContaining(name, pageRequest);

        artistMap.put("size", size);
        artistMap.put("sortProperty", sortProperty);
        artistMap.put("sortDirection", sortDirection);
        artistMap.put("pageNumber", page + 1);
        artistMap.put("previousPage", page - 1);
        artistMap.put("nextPage", page + 1);
        artistMap.put("start", page * size + 1);
        artistMap.put("end", (page)*size + artistList.getNumberOfElements());

        artistMap.put("artists", artistList);

        return "listeArtists";
    }

    //GET /artists?name=aerosmith (Sous forme de Page)
    @RequestMapping(method = RequestMethod.GET)
    public String getALlArtist(
            final ModelMap artistMap,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sortProperty", defaultValue = "name") String sortProperty,
            @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection
    ){
        /*if(page < 0){
            throw new IllegalArgumentException("La valeur Page ne peut pas être négative !");
        }
        if(size <= 0 || size >= 50){
            throw new IllegalArgumentException("La valeur de la taille ne peux pas être nulle ou négative ou supérieur à 50!");
        }
        if(!("ASC".equalsIgnoreCase(sortDirection)) && !("DESC".equalsIgnoreCase(sortDirection))){
            throw new IllegalArgumentException("Le paramètre sortDirection doit valoir soit ASC soit DESC");
        }*/

        PageRequest pageRequest =  PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sortProperty);
        Page<Artist> artistList= artistRepository.findAll( pageRequest);

        artistMap.put("size", size);
        artistMap.put("sortProperty", sortProperty);
        artistMap.put("sortDirection", sortDirection);
        artistMap.put("pageNumber", page + 1);
        artistMap.put("previousPage", page - 1);
        artistMap.put("nextPage", page + 1);
        artistMap.put("start", page * size + 1);
        artistMap.put("end", (page)*size + artistList.getNumberOfElements());

        artistMap.put("artists", artistList);

        return "listeArtists";
    }


    /*@Autowired
    private ArtistRepository artistRepository;

    //GET /artists/1
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, value = "/{idArtist}")
    public Artist getArtist(
            @PathVariable("idArtist") Integer idArtist
    ){
        Optional<Artist> artistOptional = artistRepository.findById(idArtist);
        if(artistOptional.isEmpty()){
            throw new EntityNotFoundException("L'artiste "+idArtist+" n'a pas été trouvé !");
        }
        return artistOptional.get();
    }

    //GET /artists?name=aerosmith (Sous forme de Page)
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, params = {"name"})
    public Page<Artist> getArtistByName(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size,
            @RequestParam(defaultValue = "name") String sortProperty,
            @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection
    ){
        if(page < 0){
            throw new IllegalArgumentException("La valeur Page ne peut pas être négative !");
        }
        if(size <= 0 || size >= 50){
            throw new IllegalArgumentException("La valeur de la taille ne peux pas être nulle ou négative ou supérieur à 50!");
        }
        if(!("ASC".equalsIgnoreCase(sortDirection)) && !("DESC".equalsIgnoreCase(sortDirection))){
            throw new IllegalArgumentException("Le paramètre sortDirection doit valoir soit ASC soit DESC");
        }

        PageRequest pageRequest =  PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sortProperty);
        Page<Artist> artistList= artistRepository.findAllByNameContaining(name, pageRequest);
        return artistList;
    }

    //GET /artists?page=0&size=10&sortProperty=name&sortDirection=ASC
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Artist> getAllArtist(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "name") String sortProperty,
            @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection
    ){
        if(page < 0){
            throw new IllegalArgumentException("La valeur Page ne peut pas être négative !");
        }
        if(size <= 0 || size >= 50){
            throw new IllegalArgumentException("La valeur de la taille ne peux pas être nulle ou négative ou supérieur à 50!");
        }
        if(!("ASC".equalsIgnoreCase(sortDirection)) && !("DESC".equalsIgnoreCase(sortDirection))){
            throw new IllegalArgumentException("Le paramètre sortDirection doit valoir soit ASC soit DESC");
        }
        PageRequest pageRequest =  PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sortProperty);
        Page<Artist> artistList= artistRepository.findAll(pageRequest);
        return artistList;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Artist registerArtist(
            @RequestBody Artist artist
    ){
        if(artistRepository.existsByName(artist.getName())){
            throw new EntityExistsException("L'artiste de nom "+artist.getName()+" existe déjà !");
        }
        artist = artistRepository.save(artist);
        return artist;
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, value = "/{artistId}")
    public Artist modifyArtist(
            @PathVariable("artistId") Integer artistId,
            @RequestBody Artist artist
    ){
        return artistRepository.save(artist);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{artistId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtist(
            @PathVariable("artistId") Integer artistId
    ){
        artistRepository.deleteById(artistId);
    }*/
}
