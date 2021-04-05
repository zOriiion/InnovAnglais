package com.example.innovanglaisgame;

import com.google.gson.annotations.SerializedName;

public class Utilisateur {

    public Utilisateur(int id, String pseudo, String mdp){
        this.id = id;
        this.pseudo = pseudo;
        this.mdp = mdp;
    }

    @SerializedName("id")
    int id;

    @SerializedName("username")
    String pseudo;

    @SerializedName("password")
    String mdp;

    public int getId() {
        return id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getMdp() {
        return mdp;
    }
}
