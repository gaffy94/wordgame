package com.gaf.anagram.services;

import com.gaf.anagram.dao.UserDao;
import com.gaf.anagram.entities.User;
import com.gaf.anagram.models.Login;
import com.gaf.anagram.utils.JEncrypt;
import com.gaf.anagram.utils.JSONUtils;
import com.gaf.anagram.utils.RestCallUtility;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Authenticate {
    @Autowired
    RestCallUtility restCallUtility;
    @Autowired
    JSONUtils jsonUtils;
    @Autowired
    JEncrypt jEncrypt;
    @Autowired
    Environment environment;
    @Autowired
    UserSessionService userSessionService;

    @Autowired
    UserDao userDao;

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(Authenticate.class);

    public boolean doAuthenticate(Login login) {
        boolean isLogin = true;
        try {

            Optional<User> ifUser = userDao.findByEmail(login.getUsername());
            User save = null;
            if(!ifUser.isPresent()){
                User user = new User();
                user.setEmail(login.getUsername());
                user.setUserName(login.getUsername());
                user.setLevel(Long.valueOf("1"));
                user.setTotalScore(Long.valueOf("0"));
                save = userDao.save(user);
            }
            userSessionService.setSessions(login.getUsername(), ifUser.orElse(save));
        } catch (Exception e) {
            e.printStackTrace();
            isLogin = false;
        }
        return isLogin;
    }
}
