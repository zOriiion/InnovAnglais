package com.example.innovanglaisgame;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "http://serveur1.arras-sio.com/symfony4-4063/projetppeb2/public/api/";

    private static Retrofit getRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    public static WebServiceInterface getInnovAnglaisService(){
        WebServiceInterface webServiceInterface = getRetrofit().create(WebServiceInterface.class);
        return webServiceInterface;
    }
}
