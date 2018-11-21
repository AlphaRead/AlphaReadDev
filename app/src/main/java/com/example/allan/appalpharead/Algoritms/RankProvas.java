package com.example.allan.appalpharead.Algoritms;

import com.example.allan.appalpharead.provas.Prova;

import java.util.ArrayList;

public class RankProvas {
    private String titles, points, uid;
    private Prova prova;

    public RankProvas(String titles, String points, String uid, Prova prova) {
        this.titles = titles;
        this.points = points;
        this.uid = uid;
        this.prova = prova;
    }

    public String getTitles() {
        return titles;
    }
    public String getPoints() {
        return points;
    }
    public String getUid() {
        return uid;
    }
    public Prova getProva() {
        return prova;
    }
}
