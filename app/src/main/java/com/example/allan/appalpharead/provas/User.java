package com.example.allan.appalpharead.provas;

public class User {

    private String name, nameProva;

    public User(String name, String nameProva) {
        this.name = name;
        this.nameProva = nameProva;
    }

    public String getName() {
        return name;
    }

    public String getNameProva() {
        return nameProva;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNameProva(String nameProva) {
        this.nameProva = nameProva;
    }

}
