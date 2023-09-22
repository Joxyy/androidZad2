package com.example.eindexdb;

import static android.app.PendingIntent.getActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.example.eindexdb.sqlite.helper.DBhelper;
import com.example.eindexdb.sqlite.model.Predmet;
import com.example.eindexdb.sqlite.model.Student;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.eindexdb.databinding.ActivityStudBinding;

import java.util.ArrayList;

public class StudActivity extends AppCompatActivity {
    Intent intent;
    TextView tvIme, tvPrezime, tvIndex, tvJmbg, tvOcena, tvBodovi;
    Spinner spnPred3, spnKat3;
    Button btnLogout;
    DBhelper myDB;
    Cursor cursorPred, cursorGod;
    ArrayList<String> kategorije;
    ArrayList<String> predmeti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stud);

        intent = getIntent();

        tvIme = findViewById(R.id.tvIme);
        tvPrezime = findViewById(R.id.tvPrezime);
        tvIndex = findViewById(R.id.tvIndex);
        tvJmbg = findViewById(R.id.tvJmbg);
        tvOcena = findViewById(R.id.tvOcena);
        tvBodovi = findViewById(R.id.tvBodovi);
        btnLogout = findViewById(R.id.btnLogout);
        spnPred3 = findViewById(R.id.spnPred3);
        spnKat3 = findViewById(R.id.spnKat3);

        Long id = intent.getExtras().getLong(MainActivity.ID);
        Toast.makeText(this, "id je " + id, Toast.LENGTH_SHORT).show();

        myDB=new DBhelper(StudActivity.this);
        Student s = myDB.getStud(id);

        tvIme.setText(s.getIme());
        tvPrezime.setText(s.getPrezime());
        tvIndex.setText(s.getBrIndexa());
        tvJmbg.setText(s.getJmbg());

        myDB=new DBhelper(this);
        myDB.createTables();

        predmeti = new ArrayList<String>();

        if(myDB.findPredByStudID(s.getId())!=null) {
            predmeti = myDB.findPredByStudID(s.getId());
            loadSpnPred(predmeti);
        }else Toast.makeText(this, "nema unetih predmeta za studenta", Toast.LENGTH_SHORT).show();

        spnPred3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // This method is called when an item is selected in the Spinner
                String selectedItem = parentView.getItemAtPosition(position).toString();
                if(myDB.findKatByPredGod(selectedItem.toString().split(" ")[0].trim(), selectedItem.toString().split(" ")[1].trim())!=null) {
                    kategorije = myDB.findKatByPredGod(selectedItem.toString().split(" ")[0].trim(), selectedItem.toString().split(" ")[1].trim());
                    loadSpnKat(kategorije);
                }else Toast.makeText(StudActivity.this, "Predmet nema uneth kategorija", Toast.LENGTH_SHORT).show();
                tvOcena.setText(myDB.findOcena(s.getId(), selectedItem.toString().split(" ")[0].trim(),selectedItem.toString().split(" ")[1].trim()));

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnKat3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // This method is called when an item is selected in the Spinner
                String selectedItem = parentView.getItemAtPosition(position).toString();
                //Toast.makeText(StudActivity.this, myDB.findBodovi(selectedItem,spnPred3.getSelectedItem().toString().split(" ")[0].trim(), spnPred3.getSelectedItem().toString().split(" ")[1].trim(), s.getBrIndexa()), Toast.LENGTH_SHORT).show();
                //tvBodovi.setText(myDB.findBodovi(selectedItem,spnPred3.getSelectedItem().toString().split(" ")[0].trim(), spnPred3.getSelectedItem().toString().split(" ")[1].trim(), s.getBrIndexa()));

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnLogout.setOnClickListener(view1 -> {
            setResult(RESULT_OK, intent);
            finish();
        });
    }
    void loadSpnPred(ArrayList<String> pred){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, pred);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPred3.setAdapter(dataAdapter);
    }
    void loadSpnKat(ArrayList<String> kat){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, kat);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnKat3.setAdapter(dataAdapter);
    }

}