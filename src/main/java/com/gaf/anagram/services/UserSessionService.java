package com.gaf.anagram.services;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserSessionService {
    private static UserSessionService ourInstance = new UserSessionService();
    public Map<String, Object> sessions = new HashMap<>();

    public static UserSessionService getInstance() {
        return ourInstance;
    }

    private UserSessionService() {

    }

    public Object getSessions(String user) {
        return sessions.get(user);
    }

    public void  setSessions(String user, Object obj) {
        this.sessions.put(user, obj);
    }
}
