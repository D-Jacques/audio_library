package com.myaudiolibrary.web.controller;

import com.myaudiolibrary.web.model.Album;
import com.myaudiolibrary.web.model.Artist;
import com.myaudiolibrary.web.repository.AlbumRepository;
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
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.ServletContext;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

//The request mapping is the route to access the controller
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
        if(idArtist <= 0){
            throw new IllegalArgumentException("L'id de l'artiste doit être un chiffre non nul");
        }

        Optional<Artist> artistOptional = artistRepository.findById(idArtist);
        if(artistOptional.isEmpty()){
            throw new EntityNotFoundException("L'artiste d'id "+idArtist+" n'existe pas !");
        }
        modelArtist.put("Artist", artistOptional.get());
        modelArtist.put("albumToCreate", new Album());
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

    @RequestMapping(method = RequestMethod.GET, value = "/new")
    public String createArtist(final ModelMap artistMap){
        artistMap.put("Artist", new Artist());
        artistMap.put("albumToCreate", new Album());
        return "detailArtist";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/registerArtist", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView registerArtist(Artist artist, final ModelMap artistMap){
        if(artistRepository.existsByName(artist.getName())){
            throw new EntityExistsException("L'artiste que vous essayer d'ajouter existe déjà !");
        }
        return saveArtist(artist, artistMap);
    }

    private RedirectView saveArtist(Artist artist, ModelMap artistMap){
        if(artistRepository.existsByName(artist.getName())){
            throw new EntityExistsException("L'artiste que vous essayer d'ajouter existe déjà !");
        }
        artist = artistRepository.save(artist);
        artistMap.put("Artist", artist);
        return new RedirectView("/artists/"+ artist.getId());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{idArtist}/delete")
    public RedirectView deleteArtist(@PathVariable("idArtist") Integer idArtist, final ModelMap artistMap){

        Optional<Artist> artistCheck = artistRepository.findById(idArtist);
        if (artistCheck.isEmpty()){
            throw new EntityNotFoundException("L'artiste que vous essayer de supprimer n'existe pas !");
        }

        if(artistCheck.get().getAlbums() == null || !(artistCheck.get().getAlbums().isEmpty())){
            throw new IllegalArgumentException("L'artiste possède encore des albums, vous ne pouvez pas le supprimer !");
        }

        artistRepository.deleteById(idArtist);
        return new RedirectView("/artists");
    }
}
