package com.example.innovanglaisgame;

import com.google.gson.annotations.SerializedName;

public class Tests {

    public Tests(int id, String libelle, String idNiveau, String imageUrl, String idListMot){
        this.id = id;
        this.libelle = libelle;
        this.idNiveau = idNiveau;
        this.imageUrl = imageUrl;
        this.idListeMot = idListMot;
    }

    @SerializedName("id")
    int id;

    @SerializedName("libelle")
    String libelle;

    @SerializedName("idNiveau")
    String idNiveau;

    @SerializedName("imageUrl")
    String imageUrl;

    @SerializedName("listeMot")
    String idListeMot;

    public int getIdDuTest() {
        return id;
    }

    public String getLibelle() {
        return libelle;
    }

    public String getIdNiveau(){
        return idNiveau;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getIdListeMot() {
        return idListeMot;
    }
}
