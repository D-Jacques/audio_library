package com.myaudiolibrary.web.repository;

import com.myaudiolibrary.web.model.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Integer> {
    Artist findByName(String name);
    boolean existsByName(String name);
    Page<Artist> findAllByNameContaining(String name, Pageable pageable);
}
