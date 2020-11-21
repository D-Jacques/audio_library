package com.myaudiolibrary.web.controller;

import com.myaudiolibrary.web.model.Artist;
import com.myaudiolibrary.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

//The request mapping is the route to access the controller
@RestController
@RequestMapping(value = "/artists")
public class ArtistController {

    /*@Autowired
    private ArtistRepository artistRepository;*/

    //GET Artists/1
/*    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, value = "/{idArtist}")
    public Artist getArtist(
            @PathVariable("idArtist") Integer idArtist
    ){
        Optional<Artist> artistOptional = artistRepository.findById(idArtist);
        return artistOptional.get();
    }*/
}
