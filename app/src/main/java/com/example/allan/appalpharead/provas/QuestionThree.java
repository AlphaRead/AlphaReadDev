package com.example.allan.appalpharead.provas;

import android.graphics.Bitmap;

public class QuestionThree {

    private static int score = 1;

    public String image;
    public String name;

    public QuestionThree(String name, String image){
        this.image = image;
        this.name = name;
    }

    public static int getScore() {
        return score;
    }
    public String getImage() {
        return image;
    }
    public String getName() {
        return name;
    }

    public static void setScore(int score) {
        QuestionThree.score = score;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public void setName(String name) {
        this.name = name;
    }
}
