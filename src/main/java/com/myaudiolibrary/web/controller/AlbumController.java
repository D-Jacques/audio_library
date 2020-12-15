package com.myaudiolibrary.web.controller;

import com.myaudiolibrary.web.model.Album;
import com.myaudiolibrary.web.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

//@CrossOrigin
//@RestController
@Controller
@RequestMapping(value = "/albums")
public class AlbumController {

    @Autowired
    private AlbumRepository albumRepository;

    @RequestMapping(method = RequestMethod.POST, value = "/registerAlbum", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView registerAlbum(final ModelMap albumMap, Album album)
    {
        albumRepository.save(album);
        return new RedirectView("/artists/");

    }
    /*
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Album registerAlbum(
            @RequestBody Album album
    ){

        return albumRepository.save(album);

    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{albumId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbum(
            @PathVariable("albumId") Integer albumId
    ){
        albumRepository.deleteById(albumId);
    }*/

}
