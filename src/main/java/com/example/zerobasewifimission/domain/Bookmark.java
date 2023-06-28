package com.example.zerobasewifimission.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Bookmark {
    private int id;
    private String bookmark_group_name;
    private String wifi_name;
    private String created_date;

    public Bookmark() {
    }

    public Bookmark(int id, String bookmark_group_name, String wifi_name, String created_date) {
        this.id = id;
        this.bookmark_group_name = bookmark_group_name;
        this.wifi_name = wifi_name;
        this.created_date = created_date;
    }
}


