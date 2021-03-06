package com.myaudiolibrary.web.controller;

import com.myaudiolibrary.web.model.Artist;
import com.myaudiolibrary.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

//The request mapping is the route to access the controller
@CrossOrigin
//we're in a RestController
@RestController
@RequestMapping(value = "/artists")
public class ArtistController {

    //AutoWireing the Repository so as to gather the data from our repository
    @Autowired
    private ArtistRepository artistRepository;

    //GET /artists/1
    //Get an artist with his id
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, value = "/{idArtist}")
    public Artist getArtist(
            @PathVariable("idArtist") Integer idArtist
    ){
        //Optionnal means that we can get one or not
        Optional<Artist> artistOptional = artistRepository.findById(idArtist);

        //If the artist has not been found we throw a 404 error
        if(artistOptional.isEmpty()){
            throw new EntityNotFoundException("L'artiste "+idArtist+" n'a pas été trouvé !");
        }
        //We use get as artist is a Optional<Artist> here
        return artistOptional.get();
    }

    //GET /artists?name=aerosmith (Sous forme de Page)
    //Get multiples artists with a name or the letters in his name
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, params = {"name"})
    public Page<Artist> getArtistByName(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size,
            @RequestParam(defaultValue = "name") String sortProperty,
            @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection
    ){
        //Many condition, if page is negative, if size is not between 1 and 49 or if sortDirection is differents from ASC or DESC we return a illegal argument exception
        //as the user would be trying to execute a request with false parameters
        if(page < 0){
            throw new IllegalArgumentException("La valeur Page ne peut pas être négative !");
        }
        if(size <= 0 || size >= 50){
            throw new IllegalArgumentException("La valeur de la taille ne peux pas être nulle ou négative ou supérieur à 50!");
        }
        if(!("ASC".equalsIgnoreCase(sortDirection)) && !("DESC".equalsIgnoreCase(sortDirection))){
            throw new IllegalArgumentException("Le paramètre sortDirection doit valoir soit ASC soit DESC");
        }

        //Creating a PageRequest to get and store our page Object next lines
        PageRequest pageRequest =  PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sortProperty);
        //findAllByNameContaining will look for all the artists with a name like the name value get in
        Page<Artist> artistList= artistRepository.findAllByNameContaining(name, pageRequest);
        return artistList;
    }

    //GET /artists?page=0&size=10&sortProperty=name&sortDirection=ASC
    //Get all the artists in the database
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Artist> getAllArtist(
            //We use some defaults values here is the user forgot to fill some
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

    //Register an artist with a POST method
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Artist registerArtist(
            @RequestBody Artist artist
    ){
        if(artistRepository.existsByName(artist.getName())){
            throw new EntityExistsException("L'artiste de nom "+artist.getName()+" existe déjà !");
        }
        //Here we only return our saved artist
        artist = artistRepository.save(artist);
        return artist;
    }

    //Modify an artists with a PUT method
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, value = "/{artistId}")
    public Artist modifyArtist(
            @PathVariable("artistId") Integer artistId,
            @RequestBody Artist artist
    ){
        if(artistRepository.findById(artistId).isEmpty()){
            throw new EntityNotFoundException("L'artiste d'id "+artistId+" que vous essayer de modifier n'existe pas ");
        }
        //We save our artists and returns it to our front view
        return artistRepository.save(artist);
    }

    //Delete an artists with a DELETE method
    //Note that we don't need to return anything here
    @RequestMapping(method = RequestMethod.DELETE, value = "/{artistId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtist(
            @PathVariable("artistId") Integer artistId
    ){
        //We first check if we find an artist with this id
        Optional<Artist> artist = artistRepository.findById(artistId);

        //If we don't we throw an exception
        if(artist.isEmpty()){
            throw new EntityNotFoundException("L'artiste d'id "+artistId+" que vous essayer de supprimer n'existe pas ");
        }

        //I added this condition here, if the artist still have albums, we prevent his supression
        if(artist.get().getAlbums() == null || !(artist.get().getAlbums().isEmpty())){
            throw new IllegalArgumentException("L'artiste possède encore des albums, vous ne pouvez pas le supprimer !");
        }
        //We delete the artists if there was no error
        artistRepository.deleteById(artistId);
    }
}
