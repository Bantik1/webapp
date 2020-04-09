package com.example.web.app.api.request;

import java.util.ArrayList;
import java.util.List;

public class AllUsersId {
    private List<Integer> listId;

    public AllUsersId() {
        this.listId = new ArrayList<>();
    }

    public List<Integer> getListId() {
        return listId;
    }

    public void setListId(List<Integer> listId) {
        this.listId = listId;
    }
}
