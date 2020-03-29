package com.gaf.anagram.services;

import com.gaf.anagram.dao.UserDao;
import com.gaf.anagram.dao.UserWordsDao;
import com.gaf.anagram.entities.Anagrams;
import com.gaf.anagram.entities.User;
import com.gaf.anagram.entities.UserWords;
import com.gaf.anagram.models.DefinitionResp;
import com.gaf.anagram.models.Response;
import com.gaf.anagram.utils.JSONUtils;
import com.gaf.anagram.utils.RestCallUtility;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class WordValidatorService {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(WordValidatorService.class);
    @Autowired
    RestCallUtility restCallUtility;
    @Autowired
    Environment environment;
    @Autowired
    JSONUtils jsonUtils;
    @Autowired
    UserService userService;
@Autowired
    UserDao userDao;
    @Autowired
    AnagramService anagramService;
    @Autowired
    UserWordsDao userWordsDao;

    public Response validateWord(String word){
        Response resp = new Response();
        try{
            word =word.trim().toLowerCase();
            Response response = anagramService.fetchAnagramWord();
            // check if i have word under this anagram already
            Optional<UserWords>  isExists = userWordsDao.findByUserIdAndWordIgnoreCaseAndAnagramId(userService.getSessionVariables().get().getId(),word,((Anagrams) response.getData()).getId());
            if(isExists.isPresent()){
                resp.setResponseCode("999");
                resp.setResponseMessage("Word Already Entered");
                return resp;
            }

            if(!isContains(word,((Anagrams) response.getData()).getWord().trim().toLowerCase())){
                resp.setResponseCode("999");
                resp.setResponseMessage("Invalid Word");
                return resp;
            }
            String url =  environment.getProperty("urbandictionapi")+environment.getProperty("urbandictionquery")+"="+word.toLowerCase();
            ResponseEntity<?> call = restCallUtility.getCall(url);
            logger.info("Response : "+ jsonUtils.toJson(jsonUtils.toObject(String.valueOf(call.getBody()), DefinitionResp.class)));
            DefinitionResp o = (DefinitionResp) jsonUtils.toObject(String.valueOf(call.getBody()), DefinitionResp.class);
            if(o != null && o.getList().size() > 0){
               // get word score
                Long score = getWordScore(word,((Anagrams) response.getData()).getWord());
               // insert into userword
                UserWords myWord = new UserWords();
                myWord.setAnagramId(((Anagrams) response.getData()).getId());
                myWord.setUserId(userService.getSessionVariables().get().getId());
                myWord.setWord(word.toUpperCase());
                myWord.setWordScore(score);
                myWord.setDateCreated(new Date());
                userWordsDao.save(myWord);
               // update user total score
                User user = userService.getSessionVariables().get();
                user.setTotalScore(user.getTotalScore()+score);
                userDao.save(user);
                resp.setResponseCode("000");
                resp.setResponseMessage("Word Validated Successfully");
            } else {
                resp.setResponseCode("999");
                resp.setResponseMessage("Could Not Validate Word");
            }

        }catch(Exception e){
            e.printStackTrace();
            resp.setResponseCode("999");
            resp.setResponseMessage("Could Not Validate Word");
        }
        return resp;
    }

    private boolean isContains(String word, String word1) {
        boolean isContain = true;
        boolean toBreak = true;
        do{
            char[] sub = word.toCharArray();
            List<Character> collect = word1.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
            for (int i=0;i<sub.length;i++){
               if(collect.contains(sub[i])){
                   int i1 = collect.indexOf(sub[i]);
                   collect.remove(i1);
                   logger.info("new main size is : "+ collect.size());
               } else {
                   isContain = false;
                   toBreak = false;
               }
            }
            toBreak = false;
        } while(toBreak);

        return isContain;
    }

    private Long getWordScore(String word, String data) {
        Long score = Long.valueOf("0");
        logger.info("Word: "+ word);
        logger.info("Anagram: "+ data);
        logger.info("Word Length : "+ word.length());
        logger.info("Anagram Length Length : "+ data.length());
        logger.info("Word Length Percentage : "+ (( (double)word.length() / data.length()) * 100) + "%");
        if(data.length() == word.length()){
            score = 10L;
        }
        else if((((double)word.length() / data.length()) * 100) >= 80){
            score = 8L;
        }
        else if((((double)word.length() / data.length()) * 100) >= 60){
            score = 6L;
        }
        else if((((double)word.length() / data.length()) * 100) >= 40){
            score = 4L;
        }
        else if((((double)word.length() / data.length()) * 100) >= 20){
            score = 2L;
        } else {
            score = 1L;
        }

        return score;
    }
}
