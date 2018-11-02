package com.example.allan.appalpharead.provas;

public class User {

    private String nome, sobrenome;
    private Integer score;

    public User(String nome, String sobrenome) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.score = 0;
    }

    public String getNome() {
        return nome;
    }
    public String getSobrenome() {
        return sobrenome;
    }
    public Integer getScore() {
        return score;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }
    public void setScore(Integer score) {
        this.score = score;
    }
}
