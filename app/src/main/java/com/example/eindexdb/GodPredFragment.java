package com.example.eindexdb;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class GodPredFragment extends Fragment {
    EditText etGod, etPredmet;
    Button btnDodGod, btnDodStud;

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
        btnDodStud = view.findViewById(R.id.btnDodPred);

        return view;
    }
}