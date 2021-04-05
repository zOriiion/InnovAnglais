package com.example.innovanglaisgame;

import com.google.gson.annotations.SerializedName;

public class Niveaux {

    @SerializedName("id")
    int id;
    @SerializedName("libelle")
    String libelle;

    @SerializedName("imageUrl")
    String image_url;

    public Niveaux(int id, String libelle, String image_url){
        this.id = id;
        this.libelle = libelle;
        this.image_url = image_url;
    }

    public int getIdDuNiveau() {
        return id;
    }

    public String getLibelle() {
        return libelle;
    }

    public String getImage_url() {
        return image_url;
    }
}
