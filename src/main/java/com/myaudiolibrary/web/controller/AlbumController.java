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

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Album registerAlbum(
            @RequestBody Album album
    ){
        if(album.getArtist() == null){
            throw new EntityNotFoundException("Vous essayez d'ajouter un album sans artiste !");
        }

        return albumRepository.save(album);

    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{albumId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbum(
            @PathVariable("albumId") Integer albumId
    ){
        //if we try to delete an album that doesn't exists
        if(albumRepository.findById(albumId).isEmpty()){
            throw new EntityNotFoundException("Vous essayez de supprimer un album qui n'existe pas !");
        }
        albumRepository.deleteById(albumId);
    }

}
