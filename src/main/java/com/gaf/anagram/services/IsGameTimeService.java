package com.gaf.anagram.services;

import com.gaf.anagram.models.Response;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class IsGameTimeService {
    @Autowired
    Environment environment;


    private final org.slf4j.Logger logger = LoggerFactory.getLogger(IsGameTimeService.class);
    final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
    public Response isIt() {
        Response resp = new Response();
        try {
            Date startDate = dateFormat.parse(environment.getProperty("startdate"));
            Date endDate = dateFormat.parse(environment.getProperty("enddate"));
            if(new Date().after(startDate) && new Date().before(endDate)){
                resp.setResponseCode("000");
                resp.setResponseMessage("Valid Game Time");
            } else {
                resp.setResponseCode("999");
                resp.setResponseMessage("Invalid Game Time");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return resp;
    }
}
