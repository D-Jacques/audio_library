package com.myaudiolibrary.web;

import com.myaudiolibrary.web.model.Album;
import com.myaudiolibrary.web.model.Artist;
import com.myaudiolibrary.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MyRunner implements CommandLineRunner {

    @Autowired
    private ArtistRepository artistRepository;

    @Override
    public void run(String... args) throws Exception {

        /*PageRequest pageRequest =  PageRequest.of(0, 10, Sort.Direction.ASC, "Name");
        Page<Artist> artistList= artistRepository.findAllByName("aerosmith", pageRequest);
        for (Artist artist : artistList){
            System.out.println(artist.getName());
        }*/
    }
}
