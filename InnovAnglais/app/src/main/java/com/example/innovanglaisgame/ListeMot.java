package com.example.innovanglaisgame;


import com.google.gson.annotations.SerializedName;

public class ListeMot {

    public ListeMot(String[] idVocabulaire){
        this.idVocabulaire = idVocabulaire;
    }

    @SerializedName("idVocabulaire")
    String[] idVocabulaire;

    public String[] getIdVocabulaire() {
        return idVocabulaire;
    }
}
