package com.example.innovanglaisgame;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChoixTestFragment extends Fragment implements OnNiveauListClickListener, OnTestListClickListener{

    RecyclerView recyclerView;
    List<Niveaux> listNiveau;
    List<Tests> listTest;
    String testName;
    String difficultyName;
    Button boutonRetour;
    int[] idVocab;
    String[] tabMotTraduit;
    String[] tabMotNonTraduit;
    TextView testNameTextView;
    TextView difficultyNameTextView;
    TextView scoreTextView;
    TextView consigneTextView;
    TextView vocabulaireTextView;
    EditText inputPlainText;
    Button validerButton;
    int score;
    int i = 0;
    int verifAnswer = 0;
    int idUserLogged;
    TextView endTextView;
    TextView endScoreTextView;
    int idTest;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View choixTestView = inflater.inflate(R.layout.choix_test_fragment, container, false);

        idUserLogged = getActivity().getIntent().getExtras().getInt("idUser");

        testNameTextView = choixTestView.findViewById(R.id.testNameTextView);
        testNameTextView.setVisibility(View.INVISIBLE);

        difficultyNameTextView = choixTestView.findViewById(R.id.difficultyNameTextView);
        difficultyNameTextView.setVisibility(View.INVISIBLE);

        scoreTextView = choixTestView.findViewById(R.id.scoreTextView);
        scoreTextView.setVisibility(View.INVISIBLE);

        vocabulaireTextView = choixTestView.findViewById(R.id.vocabulaireTextView);
        vocabulaireTextView.setVisibility(View.INVISIBLE);

        inputPlainText = choixTestView.findViewById(R.id.inputPlainText);
        inputPlainText.setVisibility(View.INVISIBLE);

        consigneTextView = choixTestView.findViewById(R.id.consigneTextView);
        consigneTextView.setVisibility(View.INVISIBLE);

        validerButton = choixTestView.findViewById(R.id.validerButton);
        validerButton.setVisibility(View.INVISIBLE);

        endTextView = choixTestView.findViewById(R.id.endTextView);
        endTextView.setVisibility(View.INVISIBLE);

        endScoreTextView = choixTestView.findViewById(R.id.endScoreTextView);
        endScoreTextView.setVisibility(View.INVISIBLE);

        idVocab = new int[4];
        tabMotTraduit = new String[4];
        tabMotNonTraduit = new String[4];

        boutonRetour = choixTestView.findViewById(R.id.jeuButtonRetour);
        boutonRetour.setVisibility(View.INVISIBLE);

        recyclerView = choixTestView.findViewById(R.id.choixTestRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        getNiveauList();

        return choixTestView;
    }

    private void getNiveauList(){
        listNiveau = new ArrayList<>();

        Call<Niveaux[]> niveauCall = ApiClient.getInnovAnglaisService().getListNiveaux();
        niveauCall.enqueue(new Callback<Niveaux[]>() {
            @Override
            public void onResponse(Call<Niveaux[]> call, Response<Niveaux[]> response) {
                for(int i = 0; i < response.body().length; i++){
                    listNiveau.add(new Niveaux(response.body()[i].getIdDuNiveau(), response.body()[i].getLibelle(), response.body()[i].getImage_url()));
                }
                recyclerView.setAdapter(new NiveauListAdapter(listNiveau, ChoixTestFragment.this::OnNiveauListClickListener));
            }

            @Override
            public void onFailure(Call<Niveaux[]> call, Throwable t) {
                System.out.println("Fail");
            }
        });
    }

    @Override
    public void OnNiveauListClickListener(Niveaux niveaux) {
        difficultyName = niveaux.getLibelle();
        boutonRetour.setVisibility(View.VISIBLE);
        boutonRetour.setOnClickListener(this::onClickRetour);
        listTest = new ArrayList<>();

        Call<Tests[]> testCall = ApiClient.getInnovAnglaisService().getListTests();
        testCall.enqueue(new Callback<Tests[]>() {
            @Override
            public void onResponse(Call<Tests[]> call, Response<Tests[]> response) {
                for(int i = 0; i < response.body().length; i++){
                    String strForeign = response.body()[i].getIdNiveau();
                    char chrForeign = strForeign.charAt(strForeign.length()-1);
                    int idNiveau = Character.getNumericValue(chrForeign);

                    if(niveaux.getIdDuNiveau() == idNiveau){
                        listTest.add(new Tests(response.body()[i].getIdDuTest(), response.body()[i].getLibelle(), response.body()[i].getIdNiveau(), response.body()[i].getImageUrl(), response.body()[i].getIdListeMot()));
                    }
                }
                recyclerView.setAdapter(new TestListAdapter(listTest, ChoixTestFragment.this::OnTestListClickListener));
            }

            @Override
            public void onFailure(Call<Tests[]> call, Throwable t) {
                System.out.println("FAIL");
            }
        });
    }

    @Override
    public void OnTestListClickListener(Tests tests) {
        idTest = tests.getIdDuTest();
        testName = tests.getLibelle();
        String strId = tests.getIdListeMot();
        String[] strExploded = strId.split("/");
        strId = strExploded[strExploded.length - 1];
        int id = Integer.parseInt(strId);

        Call<ListeMot> listeMotCall = ApiClient.getInnovAnglaisService().getListMotsById(id);
        listeMotCall.enqueue(new Callback<ListeMot>() {
            @Override
            public void onResponse(Call<ListeMot> call, Response<ListeMot> response) {
                for(int i = 0; i < response.body().getIdVocabulaire().length; i++){
                    String strId = response.body().getIdVocabulaire()[i];
                    String[] strExploded = strId.split("/");
                    strId = strExploded[strExploded.length - 1];
                    int id = Integer.parseInt(strId);
                    idVocab[i] = id;
                }
                RecupDesMots();
            }

            @Override
            public void onFailure(Call<ListeMot> call, Throwable t) {
                System.out.println("FAIL" + t.getMessage());
            }
        });
    }

    public void RecupDesMots(){

        recyclerView.setVisibility(View.INVISIBLE);

        for(int i = 0; i < 4; i ++){
            Call<Mots> motsCall = ApiClient.getInnovAnglaisService().getMotById(idVocab[i]);
            int j = i;
            motsCall.enqueue(new Callback<Mots>() {
                @Override
                public void onResponse(Call<Mots> call, Response<Mots> response) {
                    tabMotTraduit[j] = response.body().getMotTraduit();
                    tabMotNonTraduit[j] = response.body().getMotNonTraduit();
                    setupJeu();
                }

                @Override
                public void onFailure(Call<Mots> call, Throwable t) {

                }
            });
        }
    }

    public void setupJeu(){
        score = 0;

        boutonRetour.setVisibility(View.INVISIBLE);

        testNameTextView.setVisibility(View.VISIBLE);
        testNameTextView.setText(testName);

        difficultyNameTextView.setVisibility(View.VISIBLE);
        difficultyNameTextView.setText(difficultyName);

        scoreTextView.setVisibility(View.VISIBLE);
        scoreTextView.setText("Score : " + score + " / 4");

        vocabulaireTextView.setVisibility(View.VISIBLE);

        consigneTextView.setVisibility(View.VISIBLE);

        inputPlainText.setVisibility(View.VISIBLE);

        validerButton.setVisibility(View.VISIBLE);

        Jeu();
    }

    public void Jeu() {

        vocabulaireTextView.setText(tabMotNonTraduit[i]);
        scoreTextView.setText("Score : " + score + " / 4");
        validerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String libelle = tabMotTraduit[i];
                String reponse = inputPlainText.getText().toString().toLowerCase();

                inputPlainText.getText().clear();

                char[] reponseTab = reponse.toCharArray();
                char[] libelleTab = libelle.toCharArray();

                if(reponseTab.length == libelleTab.length){
                    System.out.println("Meme length");
                    for(int i = 0; i < reponseTab.length; i ++){
                        if(reponseTab[i] == libelleTab[i]){
                            verifAnswer ++;
                        }
                    }
                    if(verifAnswer == reponseTab.length){
                        verifAnswer = 0;
                        score++;
                    }
                }
                if(i < 3) {
                    i++;
                    Jeu();
                } else {
                    endingScreen();
                }
            }
        });
    }

    public void endingScreen(){
        testNameTextView.setVisibility(View.INVISIBLE);
        difficultyNameTextView.setVisibility(View.INVISIBLE);
        vocabulaireTextView.setVisibility(View.INVISIBLE);
        scoreTextView.setVisibility(View.INVISIBLE);
        consigneTextView.setVisibility(View.INVISIBLE);
        inputPlainText.setVisibility(View.INVISIBLE);

        endScoreTextView.setVisibility(View.VISIBLE);
        endTextView.setVisibility(View.VISIBLE);
        endScoreTextView.setText("Votre score : " + score + "/4");

        validerButton.setText("Retour au menu");
        validerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(View.VISIBLE);
                endScoreTextView.setVisibility(View.INVISIBLE);
                endTextView.setVisibility(View.INVISIBLE);
                validerButton.setVisibility(View.INVISIBLE);
                i = 0;
            }
        });
    }

    public void onClickRetour(View v){
        boutonRetour.setVisibility(View.INVISIBLE);
        getNiveauList();
    }


}
