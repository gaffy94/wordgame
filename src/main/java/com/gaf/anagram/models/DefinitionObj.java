package com.gaf.anagram.models;

import java.util.ArrayList;
import java.util.List;

public class DefinitionObj {

    private String definition;
    private String permalink;
    private String thumbs_up;
    private List<String> sound_urls = new ArrayList<>();
    private String author;
    private String word;
    private String defid;
    private String current_vote;
    private String written_on;
    private String example;
    private String thumbs_down;

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getThumbs_up() {
        return thumbs_up;
    }

    public void setThumbs_up(String thumbs_up) {
        this.thumbs_up = thumbs_up;
    }

    public List<String> getSound_urls() {
        return sound_urls;
    }

    public void setSound_urls(List<String> sound_urls) {
        this.sound_urls = sound_urls;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDefid() {
        return defid;
    }

    public void setDefid(String defid) {
        this.defid = defid;
    }

    public String getCurrent_vote() {
        return current_vote;
    }

    public void setCurrent_vote(String current_vote) {
        this.current_vote = current_vote;
    }

    public String getWritten_on() {
        return written_on;
    }

    public void setWritten_on(String written_on) {
        this.written_on = written_on;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getThumbs_down() {
        return thumbs_down;
    }

    public void setThumbs_down(String thumbs_down) {
        this.thumbs_down = thumbs_down;
    }
}
