package com.myaudiolibrary.web.repository;

import com.myaudiolibrary.web.model.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//We extends JpaRepository to use the natives method to get Entities datas
public interface ArtistRepository extends JpaRepository<Artist, Integer> {
    //Method that finds an artist with his name
    Artist findByName(String name);
    //Method to check if an artist exists by typing his name
    boolean existsByName(String name);
    //Method that return names that looks like the name in parameters
    Page<Artist> findAllByNameContaining(String name, Pageable pageable);
}
