package com.gaf.anagram.utils;


import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class RestCallUtility {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(RestCallUtility.class);

    @Autowired
    JEncrypt jEncrypt;


    @Autowired
    RestTemplate myTemplate;


    @Autowired
    Environment environment;

    public ResponseEntity<?> objectRestCall(String url, Object obj) {
        try {
            logger.info("URL iS >> " + url);
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);
            requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//            requestHeaders.set("x-client-id",Utility.getProperty("clientid"));
//            requestHeaders.set("x-client-secret",PropsReader.getProperty("clientsecret"));
            logger.info("Request OBJ : >> " + obj);
            HttpEntity<?> requestEntity = new HttpEntity<>(obj, requestHeaders);
            ResponseEntity<?> responseEntity = myTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            if (responseEntity.hasBody()) {
                return responseEntity;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            ResponseEntity.BodyBuilder builder;
            builder = ResponseEntity.status(200);
            return builder.body(jEncrypt.encryptV2("{\n\"responseMessage\":\"" + StringEscapeUtils.escapeJava(e.getLocalizedMessage()) + "\",\n\"responseCode\":\"" + "99" + "\"}"));
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<?> getCall(String url){
        try {
            logger.info("URL iS >> " + url);
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);
            requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            requestHeaders.set("x-rapidapi-host",environment.getProperty("rapidapihost"));
            requestHeaders.set("x-rapidapi-key",environment.getProperty("rapidapikey"));
//            requestHeaders.set("x-client-id",Utility.getProperty("clientid"));
//            requestHeaders.set("x-client-secret",PropsReader.getProperty("clientsecret"));
            HttpEntity<?> requestEntity = new HttpEntity<>(null, requestHeaders);
            ResponseEntity<?> responseEntity = myTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
            if (responseEntity.hasBody()) {
                return responseEntity;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            ResponseEntity.BodyBuilder builder;
            builder = ResponseEntity.status(200);
            return builder.body(jEncrypt.encryptV2("{\n\"responseMessage\":\"" + StringEscapeUtils.escapeJava(e.getLocalizedMessage()) + "\",\n\"responseCode\":\"" + "99" + "\"}"));
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
