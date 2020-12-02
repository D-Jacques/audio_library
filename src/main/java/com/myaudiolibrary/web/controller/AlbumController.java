package com.myaudiolibrary.web.controller;

import com.myaudiolibrary.web.model.Album;
import com.myaudiolibrary.web.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@CrossOrigin
@RestController
@RequestMapping(value = "/albums")
public class AlbumController {

    @Autowired
    private AlbumRepository albumRepository;

    //Create an album for an arsist with a POST method
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Album registerAlbum(
            @RequestBody Album album
    ){
        if(album.getArtist() == null){
            throw new EntityNotFoundException("Vous essayez d'ajouter un album sans artiste !");
        }

        //We save the album and return its content to the fronEnd
        return albumRepository.save(album);

    }

    //Delete an album from a artist
    @RequestMapping(method = RequestMethod.DELETE, value = "/{albumId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbum(
            @PathVariable("albumId") Integer albumId
    ){
        //if we try to delete an album that doesn't exists
        if(albumRepository.findById(albumId).isEmpty()){
            throw new EntityNotFoundException("Vous essayez de supprimer un album qui n'existe pas !");
        }
        //We use the method deleteByID From our repository to delete the album
        albumRepository.deleteById(albumId);
    }

}
