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

//TP Audio Thymeleaf Jacques DUCROUX

//@CrossOrigin
//@RestController
@Controller
@RequestMapping(value = "/albums")
public class AlbumController {

    @Autowired
    private AlbumRepository albumRepository;

    //Quand on veut enregistrer un album à un artiste on passe par cette route, a noté que l'on passe aussi
    //par la vue qui permet de visualiser un artiste et ses albums
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView registerAlbum(final ModelMap albumMap, Album album, Artist artist)
    {
        //On retourne une exception si on créer un album sans nom et si on essaye d'ajouter un album sans artiste
        if(album.getTitle().isEmpty()){
            throw new IllegalArgumentException("Vous ne pouvez pas créer d'albums sans nom !");
        }

        if(album.getArtist() == null){
            throw new IllegalArgumentException("Vous ne pouvez pas ajouter un album sans artiste !");
        }

        albumRepository.save(album);
        return new RedirectView("/artists/"+artist.getId());

    }

    //Suppression de l'artiste
    @RequestMapping(method = RequestMethod.GET, value = "/{albumId}")
    public RedirectView deleteAlbum(@PathVariable("albumId") Integer albumId, final ModelMap albumMap, Artist artist){
        //Si on tente de supprimer un album qui n'existe pas, existById est présente nativement
        if(!(albumRepository.existsById(albumId))){
            throw new EntityNotFoundException("L'album d'id "+albumId+" N'existe pas !");
        }
        albumRepository.deleteById(albumId);
        //On redirige sur la route /artists/artistId
        return new RedirectView("/artists/"+artist.getId());
    }

}
