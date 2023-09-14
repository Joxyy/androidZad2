package com.example.eindexdb;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.eindexdb.sqlite.helper.DBhelper;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class DodStudFragment extends Fragment {

    EditText etStudUname, etStudPass, etStudIme, etStudPrezime, etStudIndex, etStudJmbg;
    Button btnDodStud;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dod_stud, container, false);

        etStudUname = view.findViewById(R.id.etStudUname);
        etStudPass = view.findViewById(R.id.etStudPass);
        etStudIme = view.findViewById(R.id.etStudIme);
        etStudPrezime = view.findViewById(R.id.etStudPrezime);
        etStudIndex = view.findViewById(R.id.etStudIndex);
        etStudJmbg = view.findViewById(R.id.etStudJmbg);
        btnDodStud = view.findViewById(R.id.btnDodStud);

        btnDodStud.setOnClickListener(view1 -> {
            DBhelper myDB = new DBhelperBuilder().setContext(getActivity()).createDBhelper();
            myDB.createTables();
            myDB.addStud(etStudUname.getText().toString().trim(),
                    etStudPass.getText().toString().trim(),
                    etStudIme.getText().toString().trim(),
                    etStudPrezime.getText().toString().trim(),
                    etStudIndex.getText().toString().trim(),
                    etStudJmbg.getText().toString().trim());
        });

        return view;
    }
}