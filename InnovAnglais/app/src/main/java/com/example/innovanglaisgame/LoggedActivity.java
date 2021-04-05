package com.example.innovanglaisgame;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LoggedActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    Fragment jeuFragment;
    Fragment choixTestFragment;
    Fragment statsFragment;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_logged);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);

        choixTestFragment = new ChoixTestFragment();
        statsFragment = new StatsFragment();

        bottomNavigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);

        loadFragment(choixTestFragment);
    }

    private boolean loadFragment( Fragment fragment){
        if(fragment != null) {
            getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.fragment_jeu_container, fragment).commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch(item.getItemId()) {
            case R.id.action_liste:
                fragment = choixTestFragment;
                break;
            case R.id.action_stats:
                fragment = statsFragment;
                break;
            default:
                break;
        }
        return loadFragment(fragment);
    }
}
