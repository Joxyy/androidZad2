package com.example.eindexdb;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.eindexdb.sqlite.helper.DBhelper;
import com.example.eindexdb.sqlite.model.Predmet;
import com.example.eindexdb.sqlite.model.Student;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DodelaStudNaPredFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DodelaStudNaPredFragment extends Fragment {

    Spinner spnPred2, spnGod2, spnStud;
    Button btnDodelaStudNaPred;
    DBhelper myDB;
    Predmet p;
    Student s;
    ArrayList<String> predmeti;
    ArrayList<String> godine;
    ArrayList<String> studenti;
    Cursor cursorPred;
    Cursor cursorGod;
    Cursor cursorStud;

    // TODO: Rename and change types and number of parameters

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_dodela_stud_na_pred, container, false);

        spnPred2 = view.findViewById(R.id.spnPred2);
        spnGod2 = view.findViewById(R.id.spnGod2);
        spnStud = view.findViewById(R.id.spnStud);
        btnDodelaStudNaPred = view.findViewById(R.id.btnDodelaStudNaPred);

        myDB=new DBhelper(getActivity());
        myDB.createTables();

        p = new Predmet();
        predmeti = new ArrayList<String>();
        godine = new ArrayList<String>();
        studenti = new ArrayList<String>();


        cursorPred = myDB.readAllSubjects();
        cursorGod = myDB.readAllYears();
        cursorStud = myDB.readAllStudents();

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
        if (cursorStud.getCount()==0){
            Toast.makeText(getActivity(), "Nema unetih stud", Toast.LENGTH_SHORT).show();

        }else{
            while(cursorStud.moveToNext()){
                //predmeti_id.add(cursorGod.getLong(0));
                studenti.add(cursorStud.getString(2)+" "+cursorStud.getString(3)+" "+cursorStud.getString(4));
                loadSpnStud(studenti);
            }
        }
        btnDodelaStudNaPred.setOnClickListener(view1->{
            myDB.addStudNaPred(spnStud.getSelectedItem().toString().split(" ")[0].trim(), spnPred2.getSelectedItem().toString(),spnGod2.getSelectedItem().toString());
            myDB.addBod(spnStud.getSelectedItem().toString().split(" ")[0].trim(),spnPred2.getSelectedItem().toString() ,spnGod2.getSelectedItem().toString());
        });


        return view;
    }
    void loadSpnPred(ArrayList<String> pred){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, pred);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPred2.setAdapter(dataAdapter);
    }
    void loadSpnGod(ArrayList<String> god){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, god);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGod2.setAdapter(dataAdapter);
    }
    void loadSpnStud(ArrayList<String> stud){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, stud);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnStud.setAdapter(dataAdapter);
    }
}