package com.example.eindexdb;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.eindexdb.sqlite.helper.DBhelper;
import com.example.eindexdb.sqlite.model.Predmet;

import java.util.ArrayList;

public class BodoviAzurFragment extends Fragment {

    Button btnUpdate;
    EditText etBodovi;
    Spinner spnPred4, spnKat4, spnStud4;
    Cursor cursorStud;
    DBhelper myDB;
    ArrayList<String> studenti;
    ArrayList<String> predmeti;
    ArrayList<String> kategorije;
    Predmet p;
    public BodoviAzurFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bodovi_azur, container, false);

        btnUpdate = view.findViewById(R.id.btnUpdate);
        etBodovi = view.findViewById(R.id.etBodovi);
        spnStud4 = view.findViewById(R.id.spnStud4);
        spnPred4 = view.findViewById(R.id.spnPred4);
        spnKat4 = view.findViewById(R.id.spnKat4);

        myDB=new DBhelper(getActivity());
        myDB.createTables();

        studenti = new ArrayList<String>();
        cursorStud = myDB.readAllStudents();

        if (cursorStud.getCount()==0){
            Toast.makeText(getActivity(), "Nema unetih studENATA", Toast.LENGTH_SHORT).show();
        }else{
            while(cursorStud.moveToNext()){
                //predmeti_id.add(cursorGod.getLong(0));
                studenti.add(cursorStud.getString(2)+" "+cursorStud.getString(3)+" "+cursorStud.getString(4));
                loadSpnStud(studenti);
            }
        }
        spnStud4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // This method is called when an item is selected in the Spinner
                String selectedItem = parentView.getItemAtPosition(position).toString();
                if(myDB.findPredByStudID(myDB.findStudID(selectedItem.toString().split(" ")[0].trim()))!=null) {
                    predmeti = myDB.findPredByStudID(myDB.findStudID(selectedItem.toString().split(" ")[0].trim()));
                    loadSpnPred(predmeti);
                }else Toast.makeText(getActivity(), "nema unetih predmeta za studenta", Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnPred4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // This method is called when an item is selected in the Spinner
                String selectedItem = parentView.getItemAtPosition(position).toString();
                if(myDB.findKatByPredGod(selectedItem.toString().split(" ")[0].trim(), selectedItem.toString().split(" ")[1].trim())!=null) {
                    kategorije = myDB.findKatByPredGod(selectedItem.toString().split(" ")[0].trim(), selectedItem.toString().split(" ")[1].trim());
                    loadSpnKat(kategorije);
                }else Toast.makeText(getActivity(), "nema unetih predmeta za studenta", Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnUpdate.setOnClickListener(view1 -> {
            if(spnPred4.getSelectedItem()!=null && spnKat4.getSelectedItem()!=null && etBodovi.getText().toString()!=null){
                String index = spnStud4.getSelectedItem().toString().split(" ")[0].trim();
                String pred= spnPred4.getSelectedItem().toString().split(" ")[0].trim();
                String god=spnPred4.getSelectedItem().toString().split(" ")[1].trim();
                String kat=spnKat4.getSelectedItem().toString();
                int bod = Integer.parseInt(etBodovi.getText().toString());

                Predmet p = myDB.getPredKat(pred, god);
                for(String k : p.getKategorije()){
                    if(kat.equals(k)){
                        if( bod>p.getMaxPoeni().get(p.getKategorije().indexOf(k)))
                            Toast.makeText(getActivity(), "Premasili ste max za datu kategoriju", Toast.LENGTH_SHORT).show();
                        else myDB.updateBod(index,pred ,god, kat, bod);
                    }
                }
                int suma = 0;
                for(String k : p.getKategorije()){
                    if( bod>p.getMinPoeni().get(p.getKategorije().indexOf(k))) {
                        String ocena;
                        suma += myDB.findBodovi(kat, pred, god, index);
                        if (suma >= 51) {
                            if (suma <= 61) ocena = "6";
                            else if (suma <= 71) ocena = "7";
                            else if (suma <= 81) ocena = "8";
                            else if (suma <= 91) ocena = "9";
                            else ocena = "10";
                            Toast.makeText(getActivity(), "trenutna ocena "+ ocena, Toast.LENGTH_SHORT).show();

                            myDB.updateGrades(index, pred, god, ocena);
                        }
                    }
                }

            }
        });

        return view;
    }
    void loadSpnStud(ArrayList<String> stud){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, stud);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnStud4.setAdapter(dataAdapter);
    }
    void loadSpnPred(ArrayList<String> pred){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, pred);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPred4.setAdapter(dataAdapter);
    }
    void loadSpnKat(ArrayList<String> kat){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, kat);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnKat4.setAdapter(dataAdapter);
    }
}