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
public class DodAdminFragment extends Fragment {

    EditText etAdminUname, etAdminPass;
    Button btnDodAdmin;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_dod_admin, container, false);

        etAdminUname = view.findViewById(R.id.etAdminUname);
        etAdminPass = view.findViewById(R.id.etAdminPass);
        btnDodAdmin = view.findViewById(R.id.btnDodAdmin);

        btnDodAdmin.setOnClickListener(view1 -> {
            DBhelper myDB = new DBhelper(getActivity());
            myDB.createTables();
            myDB.addAdmin(etAdminUname.getText().toString().trim(),etAdminPass.getText().toString().trim());
        });

        return view;
    }
}