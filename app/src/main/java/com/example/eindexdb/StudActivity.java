package com.example.eindexdb;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.example.eindexdb.sqlite.helper.DBhelper;
import com.example.eindexdb.sqlite.model.Student;
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
    DBhelper myDB;

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

        myDB=new DBhelper(StudActivity.this);
        Student s = myDB.getStud(id);

        tvIme.setText(s.getIme());
        tvPrezime.setText(s.getPrezime());
        tvIndex.setText(s.getBrIndexa());
        tvJmbg.setText(s.getJmbg());
    }

}