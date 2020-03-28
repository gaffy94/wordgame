package com.gaf.anagram.services;

import com.gaf.anagram.controllers.MainController;
import com.gaf.anagram.dao.AnagramsDao;
import com.gaf.anagram.dao.UserWordsDao;
import com.gaf.anagram.entities.Anagrams;
import com.gaf.anagram.entities.UserWords;
import com.gaf.anagram.models.Response;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MyWordService {

    @Autowired
    UserService userService;
    @Autowired
    UserWordsDao userWordsDao;
    @Autowired
    WordValidatorService wordValidatorService;
    @Autowired
    AnagramsDao anagramsDao;

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(MyWordService.class);
    public Response fetchMyWords(){
        Response resp = new Response();
        try{
            Long id = userService.getSessionVariables().get().getId();
            Long level = userService.getSessionVariables().get().getLevel();
            Optional<Anagrams> byLevel = anagramsDao.findByLevel(level);
            logger.info("id to check : "+ id);
            List<UserWords> byUserId = userWordsDao.findByUserIdAndAnagramId(id,byLevel.get().getId());
            resp.setResponseCode("000");
            resp.setResponseMessage("Fetched Successfully");
            resp.setData(byUserId);
        } catch(Exception e){
            e.printStackTrace();
            resp.setResponseMessage("Error Fetching");
            resp.setResponseCode("999");
        }
        return resp;
    }

    public Response getLevelWords(Long level) {
        Response resp = new Response();
        try{
            Long id = userService.getSessionVariables().get().getId();
            Optional<Anagrams> byLevel = anagramsDao.findByLevel(level);
            logger.info("id to check : "+ id);
            List<UserWords> byUserId = userWordsDao.findByUserIdAndAnagramId(id,byLevel.get().getId());
            resp.setResponseCode("000");
            resp.setResponseMessage("Fetched Successfully");
            resp.setData(byUserId);
        } catch(Exception e){
            e.printStackTrace();
            resp.setResponseMessage("Error Fetching");
            resp.setResponseCode("999");
        }
        return resp;
    }
}
