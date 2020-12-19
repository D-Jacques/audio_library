package com.myaudiolibrary.web.controller;

import com.myaudiolibrary.web.model.Album;
import com.myaudiolibrary.web.model.Artist;
import com.myaudiolibrary.web.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.EntityNotFoundException;

//@CrossOrigin
//@RestController
@Controller
@RequestMapping(value = "/albums")
public class AlbumController {

    @Autowired
    private AlbumRepository albumRepository;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView registerAlbum(final ModelMap albumMap, Album album, Artist artist)
    {
        if(album.getTitle().isEmpty()){
            throw new IllegalArgumentException("Vous ne pouvez pas créer d'albums sans nom !");
        }

        if(album.getArtist() == null){
            throw new IllegalArgumentException("Vous ne pouvez pas ajouter un album sans artiste !");
        }

        albumRepository.save(album);
        return new RedirectView("/artists/"+artist.getId());

    }

    @RequestMapping(method = RequestMethod.GET, value = "/{albumId}")
    public RedirectView deleteAlbum(@PathVariable("albumId") Integer albumId, final ModelMap albumMap, Artist artist){
        if(!(albumRepository.existsById(albumId))){
            throw new EntityNotFoundException("L'album d'id "+albumId+" N'existe pas !");
        }
        albumRepository.deleteById(albumId);
        return new RedirectView("/artists/"+artist.getId());
    }

}
