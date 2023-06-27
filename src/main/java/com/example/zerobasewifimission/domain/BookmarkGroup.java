package com.example.zerobasewifimission.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookmarkGroup {
    private int no;
    private String name;
    private int BO;
    private String created_time;
    private String modify_time;

    public BookmarkGroup() {
    }

    public BookmarkGroup(int no, String name, int BO, String created_time, String modify_time) {
        this.no = no;
        this.name = name;
        this.BO = BO;
        this.created_time = created_time;
        this.modify_time = modify_time;
    }
}


