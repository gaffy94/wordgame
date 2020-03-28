package com.gaf.anagram.dao;

import com.gaf.anagram.entities.Anagrams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnagramsDao extends JpaRepository<Anagrams,Long> {

    Optional<Anagrams> findByLevel(Long aLong);
}
