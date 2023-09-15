package com.example.eindexdb;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.eindexdb.databinding.ActivityStudBinding;

public class StudActivity extends AppCompatActivity {
    Intent intent;

    TextView tvIme, tvPrezime, tvIndex, tvJmbg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stud);

        intent = getIntent();

        tvIme = findViewById(R.id.tvIme);
        tvPrezime = findViewById(R.id.tvPrezime);
        tvIndex = findViewById(R.id.tvIndex);
        tvJmbg = findViewById(R.id.tvJmbg);

        Long id = intent.getExtras().getLong(MainActivity.ID);
        Toast.makeText(this, "id je " + id, Toast.LENGTH_SHORT).show();


    }

}