package com.gaf.anagram.services;


import com.gaf.anagram.dao.AnagramsDao;
import com.gaf.anagram.dao.UserDao;
import com.gaf.anagram.entities.Anagrams;
import com.gaf.anagram.entities.User;
import com.gaf.anagram.models.Response;
import com.gaf.anagram.utils.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


@Service
public class UserService {

    @Autowired
    MessageSource messageSource;
@Autowired
    AnagramsDao anagramsDao;
    @Autowired
    UserDao userDao;
    private static final Logger logger = Logger.getLogger(UserService.class.getName());
    @Autowired
    UserSessionService userSessionService;
    @Autowired
    JSONUtils jsonUtils;

    public Optional<User> setSessionValues() {
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.info("Getting Sess Variables : " + getSessionVariables());
        return Optional.of(new User());
    }

    public Optional<User> getSessionVariables() {
        User sessions = (User) userSessionService.getSessions((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        logger.info("Principal : " +SecurityContextHolder.getContext().getAuthentication().getPrincipal());
       return  userDao.findByEmail((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    public List<User> fetchOtherUsers() {
        return new ArrayList<>();
    }

    public List<User> fetchUsers() {
        return new ArrayList<>();
    }

    public Response nextLevel() {
    Response resp = new Response();
    try{
        User user = getSessionVariables().get();
        Long l = user.getLevel() + 1L;
        List<Anagrams> all = anagramsDao.findAll();
        if(l <= all.size()){
            user.setLevel(l);
            user.setTimeRemaining("120");
        } else {
            user.setCompleted("Y");
        }
        User save = userDao.save(user);
        resp.setData(save);
        resp.setResponseCode("000");
        resp.setResponseMessage("Success");
    }catch(Exception e){
        e.printStackTrace();
        resp.setResponseCode("999");
        resp.setResponseMessage("Error going to the next level");
    }
    return resp;
    }

    public Response updateRemTime(String rem) {
        Response resp = new Response();
        try{
            User user = getSessionVariables().get();
            user.setTimeRemaining(rem);
            User save = userDao.save(user);
            resp.setData(save);
            resp.setResponseCode("000");
            resp.setResponseMessage("Success");
        }catch(Exception e){
            e.printStackTrace();
            resp.setResponseCode("999");
            resp.setResponseMessage("Error updating timer");
        }
        return resp;
    }
}
