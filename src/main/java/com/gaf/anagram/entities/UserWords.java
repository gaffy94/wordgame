package com.gaf.anagram.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "userwords")
public class UserWords implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "userid")
    private UUID userId;

    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @Column(name = "wordscore")
    private Long wordScore;

    @Column(name = "word")
    private String word;


    @Column(name = "anagramid")
    private UUID anagramId;

    @Column(name = "datecreated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @ManyToOne
    @JoinColumn(name = "anagramid", referencedColumnName = "id", insertable = false, updatable = false)
    private Anagrams anagrams;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getWordScore() {
        return wordScore;
    }

    public void setWordScore(Long wordScore) {
        this.wordScore = wordScore;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getAnagramId() {
        return anagramId;
    }

    public void setAnagramId(UUID anagramId) {
        this.anagramId = anagramId;
    }

    public Anagrams getAnagrams() {
        return anagrams;
    }

    public void setAnagrams(Anagrams anagrams) {
        this.anagrams = anagrams;
    }
}
