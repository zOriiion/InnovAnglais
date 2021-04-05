package com.example.innovanglaisgame;

import com.google.gson.annotations.SerializedName;

public class Mots {

    public Mots(String motTraduit, String monNonTraduit){
        this.motTraduit = motTraduit;
        this.motNonTraduit = monNonTraduit;
    }

    @SerializedName("libelle")
    String motTraduit;

    @SerializedName("libelleNonTraduit")
    String motNonTraduit;

    public String getMotTraduit() {
        return motTraduit;
    }

    public String getMotNonTraduit() {
        return motNonTraduit;
    }
}
