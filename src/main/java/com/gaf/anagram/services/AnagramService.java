package com.gaf.anagram.services;

import com.gaf.anagram.dao.AnagramsDao;
import com.gaf.anagram.entities.Anagrams;
import com.gaf.anagram.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnagramService {

    @Autowired
    AnagramsDao anagramsDao;

    @Autowired
    UserService userService;

    public Response fetchAnagramWord() {
        Response resp = new Response();
        try{
            Long aLong = userService.getSessionVariables().isPresent() ? userService.getSessionVariables().get().getLevel():Long.valueOf("0");
            Optional<Anagrams> fAnaGram = anagramsDao.findByLevel(aLong);
            if(!fAnaGram.isPresent()){
               throw new RuntimeException();
            }
            resp.setData(fAnaGram.get());
            resp.setResponseCode("000");
            resp.setResponseMessage("Word Fetched");
        } catch(Exception e){
            resp.setResponseMessage("Error Fetching Word");
            resp.setResponseCode("999");
            e.printStackTrace();
        }
        return resp;
    }

    public Response fetchAllLevels() {
        Response resp = new Response();
        try{
            resp.setData(anagramsDao.findAll());
            resp.setResponseCode("000");
            resp.setResponseMessage("Levels Fetched");
        } catch(Exception e){
            resp.setResponseMessage("Error Fetching Levels");
            resp.setResponseCode("999");
            e.printStackTrace();
        }
        return resp;
    }
}
