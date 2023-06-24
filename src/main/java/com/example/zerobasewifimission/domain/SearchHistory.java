package com.example.zerobasewifimission.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SearchHistory {
    private String id;
    private String xc;
    private String yc;
    private String time;

    public SearchHistory() {
    }

    public SearchHistory(String id,String xc, String yc, String time) {
        this.id = id;
        this.xc = xc;
        this.yc = yc;
        this.time = time;
    }
}
