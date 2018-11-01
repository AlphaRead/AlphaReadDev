package com.example.allan.appalpharead.provas;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class QuestionOne {

    private static int score = 3;

    private String w1;
    private String w2;
    private String w3;

    public QuestionOne(String w1, String w2, String w3){
        this.w1 = w1;
        this.w2 = w2;
        this.w3 = w3;
    }

    public static int getScore() {
        return score;
    }
    public String getW1() {
        return w1;
    }
    public String getW2() {
        return w2;
    }
    public String getW3() {
        return w3;
    }

    public static void setScore(int score) {
        QuestionOne.score = score;
    }
    public void setW1(String w1) {
        this.w1 = w1;
    }
    public void setW2(String w2) {
        this.w2 = w2;
    }
    public void setW3(String w3) {
        this.w3 = w3;
    }
}
