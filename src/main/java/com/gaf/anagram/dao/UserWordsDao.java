package com.gaf.anagram.dao;

import com.gaf.anagram.entities.UserWords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserWordsDao extends JpaRepository<UserWords,Long> {
    List<UserWords> findByUserId(Long id);
    Optional<UserWords> findByUserIdAndWordAndAnagramId(Long id, String word, Long id1);

    Optional<UserWords> findByUserIdAndWordIgnoreCaseAndAnagramId(Long id, String word, Long id1);

    List<UserWords> findByUserIdAndAnagramId(Long id, Long level);
}
