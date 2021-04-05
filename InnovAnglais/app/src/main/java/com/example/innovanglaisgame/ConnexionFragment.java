package com.example.innovanglaisgame;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.nio.charset.Charset;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConnexionFragment extends Fragment {

    EditText emailPlain;
    EditText mdpPlain;
    String strEmail;
    String strMdp;
    Button connButton;
    int isLogged = 0;
    int emailCorrect = 0;
    int mdpCorrect = 0;
    TextView erreurTextView;
    int verifData;
    int idUserLogged;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        final View connexionView = inflater.inflate(R.layout.connexion_fragment, container, false);

        emailPlain = connexionView.findViewById(R.id.connEmailInput);
        mdpPlain = connexionView.findViewById(R.id.connMdpInput);
        connButton = connexionView.findViewById(R.id.connButtonConn);

        erreurTextView = connexionView.findViewById(R.id.erreurConnTextView);

        connButton.setOnClickListener(this::onClickConnection);

        return connexionView;
    }

    public void onClickConnection(View v){
        strEmail = emailPlain.getText().toString();
        strMdp = mdpPlain.getText().toString();

        char[] emailInput = strEmail.toCharArray();
        char[] mdpInput = strMdp.toCharArray();

        Call<Utilisateur[]> utilisateurCall = ApiClient.getInnovAnglaisService().getListUtilisateurs();
        utilisateurCall.enqueue(new Callback<Utilisateur[]>() {
            @Override
            public void onResponse(Call<Utilisateur[]> call, Response<Utilisateur[]> response) {
                for(int i = 0; i< response.body().length; i++){
                    String userEmail = response.body()[i].getPseudo();
                    String userMdp = response.body()[i].getMdp();

                    char[] emailApi = userEmail.toCharArray();
                    char[] mdpApi = userMdp.toCharArray();

                    if (emailInput.length == emailApi.length){
                        for(int j = 0; j < emailInput.length; j ++){
                            if(emailInput[j] == emailApi[j]){
                                verifData ++;
                            }
                        }
                        if(verifData == emailInput.length) {
                            emailCorrect++;
                        }
                    }

                    verifData = 0;

                    if (mdpInput.length == mdpApi.length){
                        for(int j = 0; j < mdpInput.length; j ++){
                            if(mdpInput[j] == mdpApi[j]){
                                verifData ++;
                            }
                        }
                        if(verifData == mdpInput.length){
                            mdpCorrect ++;
                        }
                        verifData = 0;
                    }

                    if(emailCorrect == 1){
                        if(mdpCorrect == 1){
                            isLogged ++;
                            idUserLogged = response.body()[i].getId();
                        } else {
                            erreurTextView.setVisibility(View.VISIBLE);
                            erreurTextView.setTextColor(Color.RED);
                            erreurTextView.setText("Mot de passe incorect");
                        }
                    } else {
                        erreurTextView.setVisibility(View.VISIBLE);
                        erreurTextView.setTextColor(Color.RED);
                        erreurTextView.setText("Email incorect");
                    }

                    if(isLogged == 1){
                        Intent connecte = new Intent(getContext(), LoggedActivity.class);
                        connecte.putExtra("idUser", idUserLogged);
                        startActivity(connecte);
                    }
                }
            }

            @Override
            public void onFailure(Call<Utilisateur[]> call, Throwable t) {

            }
        });
    }
}
