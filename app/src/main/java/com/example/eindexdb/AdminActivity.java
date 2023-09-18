package com.example.eindexdb;

import static com.example.eindexdb.R.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;


import com.example.eindexdb.sqlite.helper.DBhelper;
import com.google.android.material.navigation.NavigationView;

public class AdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Intent intent;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_admin);

        intent = getIntent();

        Toolbar toolbar = findViewById(id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(id.drawer_layout);
        NavigationView navView = findViewById(id.nav_view);
        navView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar, string.titleAdmin, string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(id.fragment_container,new DodAdminFragment()).commit();
            navView.setCheckedItem(id.dodAdmin);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == id.dodAdmin) {
            getSupportFragmentManager().beginTransaction().replace(id.fragment_container, new DodAdminFragment()).commit();
        } else if (itemId == id.dodStud) {
            getSupportFragmentManager().beginTransaction().replace(id.fragment_container, new DodStudFragment()).commit();
        } else if (itemId == id.dodGodPred) {
            getSupportFragmentManager().beginTransaction().replace(id.fragment_container, new GodPredFragment()).commit();
        } else if (itemId == id.dodKat) {
            getSupportFragmentManager().beginTransaction().replace(id.fragment_container, new KatFragment()).commit();
        }else if (itemId == id.dodelaStudNaPredmet) {
            getSupportFragmentManager().beginTransaction().replace(id.fragment_container, new DodelaStudNaPredFragment()).commit();
        }else if (itemId == id.pretrage) {
            getSupportFragmentManager().beginTransaction().replace(id.fragment_container, new PretragaFragment()).commit();
        }else if (itemId == id.logout) {
            setResult(RESULT_OK, intent);
            finish();
        } else {
            throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
}