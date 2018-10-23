package com.example.allan.appalpharead.provas;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class QuestionOne {

    private static int score = 10;

    private String questionTitle;
    private String q1;
    private String q2;
    private String q3;

    public QuestionOne(String q1, String q2, String q3, String title){
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
        this.questionTitle = title;
    }

    public QuestionOne(){};

    public String getTitle(){
        return this.questionTitle;
    }
    public String getQ1(){
        return this.q1;
    }
    public String getQ2(){
        return this.q2;
    }
    public String getQ3(){
        return this.q3;
    }
    public int getScore(){
        return this.score;
    }

    public void setTitle(String title){
        this.questionTitle = title;
    }
    public void setQ1(String q1){
        this.q1 = q1;
    }
    public void setQ2(String q2){
        this.q2 = q2;
    }
    public void setQ3(String q3){
        this.q3 = q3;
    }

}
