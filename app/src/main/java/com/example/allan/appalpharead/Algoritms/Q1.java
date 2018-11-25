package com.example.allan.appalpharead.Algoritms;

import android.widget.TextView;

public class Q1 {
    private String word;
    private TextView sig;
    private int pos;

    public Q1(String word, TextView sig, int pos) {
        this.word = word;
        this.sig = sig;
        this.pos = pos;
    }

    public Q1(TextView sig, int pos){
        this.sig = sig;
        this.pos = pos;
    }

    public String getWord() {
        return word;
    }
    public TextView getSig() {
        return sig;
    }
    public int getPos() {
        return pos;
    }
}
