package com.gaf.anagram.controllers;

import com.gaf.anagram.dao.UserWordsDao;
import com.gaf.anagram.entities.User;
import com.gaf.anagram.models.Response;
import com.gaf.anagram.services.*;
import net.bytebuddy.asm.Advice;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/anagram")
public class MainController {

    @Autowired
    IsGameTimeService isGameTimeService;
    @Autowired
    UserService userService;
    @Autowired
    AnagramService anagramService;
    @Autowired
    UserWordsDao userWordsDao;
@Autowired
    MyWordService myWordService;
    @Autowired
    WordValidatorService wordValidatorService;

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(MainController.class);
    // check if game time is ready
    @GetMapping("/isgametime")
    public Response isGameTime(){
        return isGameTimeService.isIt();
    }

     // fetch user info with current level
    @GetMapping("/fetchme")
    public Optional<User> fetchMe(){
       return userService.getSessionVariables();
    }
    // fetch word at current level
    @GetMapping("/getword")
    public Response fetchAnagramWord(){
        return anagramService.fetchAnagramWord();
    }

    // fetch all levels

    @GetMapping("/alllevels")
    public Response fetchAllLevels(){
        return anagramService.fetchAllLevels();
    }

    // display userwords at current level if any
    @GetMapping("/mywords")
    public Response fetchMyWords(){
       return myWordService.fetchMyWords();
    }

    // get level words
    @GetMapping("/getlevelwords/{level}")
    public Response getLevelWords(@PathVariable("level") Long level){
        return myWordService.getLevelWords(level);
    }

    // validate/process word entry
    @GetMapping("/validateword/{word}")
    public Response validateWord(@PathVariable("word") String word){
        return wordValidatorService.validateWord(word);
    }

    @GetMapping("/nextlevel")
    public Response nextLevel(){
        return userService.nextLevel();
    }

    @GetMapping("/updatecount/{rem}")
    public Response update(@PathVariable("rem") String rem){
        return userService.updateRemTime(rem);
    }
}
