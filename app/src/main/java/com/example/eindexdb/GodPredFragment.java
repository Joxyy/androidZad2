package com.example.eindexdb;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.eindexdb.sqlite.helper.DBhelper;


public class GodPredFragment extends Fragment {
    EditText etGod, etPredmet;
    Button btnDodGod, btnDodPred;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_god_pred, container, false);

        etGod = view.findViewById(R.id.etGod);
        etPredmet = view.findViewById(R.id.etPredmet);
        btnDodGod = view.findViewById(R.id.btnDodGod);
        btnDodPred = view.findViewById(R.id.btnDodPred);

        btnDodGod.setOnClickListener(view1->{
            DBhelper myDB = new DBhelper(getActivity());
            myDB.createTables();
            if(!etGod.getText().toString().equals("")) {
                if (etGod.getText().toString().matches("^20\\d{2}/20\\d{2}$"))
                    myDB.addYear(etGod.getText().toString());
            }

        });
        btnDodPred.setOnClickListener(view1->{
            DBhelper myDB = new DBhelper(getActivity());
            myDB.createTables();
            if(!etPredmet.getText().toString().equals("")) {
                myDB.addPred(etPredmet.getText().toString());
            }

        });
        return view;
    }
}