package com.example.allan.appalpharead;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

public class PaginaPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principal);

        Fragment init = new PerfilFrament();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, init).commit();

        BottomNavigationView bottomnav = findViewById(R.id.navigation);
        bottomnav.setOnNavigationItemSelectedListener(navListener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment selectFragment = null;
        switch(menuItem.getItemId()){
            case R.id.navigation_perfil:
                selectFragment = new PerfilFrament();
                break;
            case R.id.navigation_ranking:
                selectFragment = new RankingFrament();
                break;
            case R.id.navigation_provas:
                selectFragment = new ProvaFrament();
                break;
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.container, selectFragment).commit();

        return true;
        }
    };

}
