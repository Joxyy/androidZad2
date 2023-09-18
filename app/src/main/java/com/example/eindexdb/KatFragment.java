package com.example.eindexdb;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.eindexdb.sqlite.helper.DBhelper;
import com.example.eindexdb.sqlite.model.Predmet;

import java.util.ArrayList;


public class KatFragment extends Fragment {

    EditText etKat, etMin, etMax;
    Button btnPoveziGodPred, btnDodKat, btnDefBod, btnSacuvaj, btnPonisti;
    Spinner spnPred, spnGod, spnKat;
    DBhelper myDB;

    ArrayList<String> predmeti;
    //ArrayList<Long> predmeti_id;
    ArrayList<String> godine;
    //ArrayList<Long> godine_id;
    Cursor cursorPred;
    Cursor cursorGod;
    Predmet p;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kat, container, false);

        etKat = view.findViewById(R.id.etKat);
        etMin = view.findViewById(R.id.etMin);
        etMax = view.findViewById(R.id.etMax);

        btnPoveziGodPred = view.findViewById(R.id.btnPoveziGodPred);
        btnDodKat = view.findViewById(R.id.btnDodKat);
        btnDefBod = view.findViewById(R.id.btnDefBod);
        btnSacuvaj = view.findViewById(R.id.btnSacuvaj);
        btnPonisti = view.findViewById(R.id.btnPonisti);

        spnPred = view.findViewById(R.id.spnPred);
        spnGod = view.findViewById(R.id.spnGod);
        spnKat = view.findViewById(R.id.spnKat);


        myDB=new DBhelper(getActivity());
        myDB.createTables();

        refresh();

        btnPoveziGodPred.setOnClickListener(view1->{
            p.setGod(spnGod.getSelectedItem().toString());
            p.setNazivPredmeta(spnPred.getSelectedItem().toString());

            spnKat.setEnabled(true);
            btnSacuvaj.setEnabled(true);
            btnDefBod.setEnabled(true);
            btnDodKat.setEnabled(true);
            etMin.setEnabled(true);
            etMax.setEnabled(true);
            etKat.setEnabled(true);

            spnPred.setEnabled(false);
            spnGod.setEnabled(false);
            btnPoveziGodPred.setEnabled(false);
        });
        btnDodKat.setOnClickListener(view1->{
            if(!etKat.getText().toString().equals("")){
                p.dodKat(etKat.getText().toString());
                p.dodMax(0);
                p.dodMin(0);
                loadSpnKat(p.getKategorije());
                Toast.makeText(getActivity(), "Uspesno", Toast.LENGTH_SHORT).show();
            }
        });

        btnDefBod.setOnClickListener(view1->{
            if(!etMin.getText().toString().matches("") && !etMin.getText().toString().equals("")){
                if(etMin.getText().toString().matches("^(0*(?:[0-9]|[1-9][0-9]|100))$") && etMax.getText().toString().matches("^(0*(?:[0-9]|[1-9][0-9]|100))$") && Integer.parseInt(etMax.getText().toString())>Integer.parseInt(etMin.getText().toString())){
                    for(String k : p.getKategorije()){
                        if(k.equals(spnKat.getSelectedItem().toString())){
                            p.setMax(p.getKategorije().indexOf(k), Integer.parseInt(etMax.getText().toString()));
                            p.setMin(p.getKategorije().indexOf(k), Integer.parseInt(etMin.getText().toString()));
                            //Toast.makeText(getActivity(), "min" + p.getMaxPoeni().get(k.indexOf(k)) + p.getKategorije().indexOf(k), Toast.LENGTH_SHORT).show();
                            Toast.makeText(getActivity(), "Uspesno", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else Toast.makeText(getActivity(), "Max i moraju biti 1-100 i max je veci", Toast.LENGTH_SHORT).show();
            }else Toast.makeText(getActivity(), "Popunite sva polja", Toast.LENGTH_SHORT).show();
        });
        btnSacuvaj.setOnClickListener(view1->{
            int suma=0;
            for(int bodovi : p.getMaxPoeni()){
                suma+=bodovi;
            }
            Toast.makeText(getActivity(), ""+suma, Toast.LENGTH_SHORT).show();
            if(suma==100){
                myDB.addKat(p);
            }else Toast.makeText(getActivity(), "Zbir max bodova mora biti 100", Toast.LENGTH_SHORT).show();
        });
        btnPonisti.setOnClickListener(view1->{
            refresh();
        });


        return view;
    }
    void refresh(){
        spnKat.setEnabled(false);
        btnSacuvaj.setEnabled(false);
        btnDefBod.setEnabled(false);
        btnDodKat.setEnabled(false);
        etMin.setEnabled(false);
        etMax.setEnabled(false);
        etKat.setEnabled(false);

        spnPred.setEnabled(true);
        spnGod.setEnabled(true);
        btnPoveziGodPred.setEnabled(true);

        p = new Predmet();
        predmeti = new ArrayList<String>();
        //predmeti_id = new ArrayList<Long>();
        godine = new ArrayList<String>();
        //godine_id = new ArrayList<Long>();

        cursorPred = myDB.readAllSubjects();
        cursorGod = myDB.readAllYears();

        if (cursorPred.getCount()==0){
            Toast.makeText(getActivity(), "Nema unetih predmeta", Toast.LENGTH_SHORT).show();
        }else{
            while(cursorPred.moveToNext()){
                //predmeti_id.add(cursorPred.getLong(0));
                predmeti.add(cursorPred.getString(1));
                loadSpnPred(predmeti);
            }
        }
        if (cursorGod.getCount()==0){
            Toast.makeText(getActivity(), "Nema unetih godina", Toast.LENGTH_SHORT).show();

        }else{
            while(cursorGod.moveToNext()){
                //predmeti_id.add(cursorGod.getLong(0));
                godine.add(cursorGod.getString(1));
                loadSpnGod(godine);
            }
        }
    }
    void loadSpnPred(ArrayList<String> pred){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, pred);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPred.setAdapter(dataAdapter);
    }
    void loadSpnGod(ArrayList<String> god){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, god);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGod.setAdapter(dataAdapter);
    }
    void loadSpnKat(ArrayList<String> kat){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, kat);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnKat.setAdapter(dataAdapter);
    }
}