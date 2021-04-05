package com.example.innovanglaisgame;

import java.sql.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceInterface {
    @GET("users.json")
    Call<Utilisateur[]> getListUtilisateurs();

    @GET("niveaux.json")
    Call<Niveaux[]> getListNiveaux();

    @GET("tests.json")
    Call<Tests[]> getListTests();

    @GET("tests/{id}.json")
    Call<Tests> getTestById(@Path("id") int id);

    @GET("liste_mots/{id}.json")
    Call<ListeMot> getListMotsById(@Path("id") int id);

    @GET("vocabulaires/{id}.json")
    Call<Mots> getMotById(@Path("id") int id);
}
