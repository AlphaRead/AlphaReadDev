package com.example.allan.appalpharead.Algoritms;

public class RankUsers {

    private String Name;
    private int point;

    public RankUsers(String name, int point){
        this.Name = name;
        this.point = point;
    }

    public String getName() {
        return Name;
    }
    public int getPoint() {
        return point;
    }

    public void setName(String name) {
        Name = name;
    }
    public void setPoint(int point) {
        this.point = point;
    }
}
