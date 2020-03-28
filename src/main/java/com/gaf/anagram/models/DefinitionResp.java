package com.gaf.anagram.models;

import java.util.ArrayList;
import java.util.List;

public class DefinitionResp {
    private List<DefinitionObj> list = new ArrayList<>();

    public List<DefinitionObj> getList() {
        return list;
    }

    public void setList(List<DefinitionObj> list) {
        this.list = list;
    }
}
