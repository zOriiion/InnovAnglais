package com.example.innovanglaisgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity{

    Fragment menuPrincipalFragment;
    Fragment connexionFragment;
    Fragment inscriptionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_main);

        menuPrincipalFragment = new MenuPrincipalFragment();
        connexionFragment = new ConnexionFragment();
        inscriptionFragment = new InscriptionFragment();

        loadFragment(menuPrincipalFragment);
    }

    private boolean loadFragment(Fragment fragment){
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    public void retourMenu(View v){
        loadFragment(menuPrincipalFragment);
    }

    public void goConnexion(View v){
        loadFragment(connexionFragment);
    }

    public void goInscription(View v){
        loadFragment(inscriptionFragment);
    }
}