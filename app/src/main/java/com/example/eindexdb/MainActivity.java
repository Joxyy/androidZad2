package com.example.eindexdb;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.example.eindexdb.sqlite.helper.DBhelper;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.eindexdb.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText etUname;
    EditText etPass;
    Button btnLogin;

    DBhelper myDB;
    ArrayList<String> studId, studPass, adminId, adminPass;
    public static String ID = "RequestKey";

    //Fragment currentActFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //replaceFrag(LoginFragment.newInstance(null));
        etUname = (EditText) findViewById(R.id.etUname);
        etPass = (EditText) findViewById(R.id.etPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        //createTablesAndInitData();

        myDB=new DBhelper(MainActivity.this);
        studId = new ArrayList<>();
        studPass= new ArrayList<>();
        adminId = new ArrayList<>();
        adminPass= new ArrayList<>();

        btnLogin.setOnClickListener(view1 -> {
            if (!MainActivity.this.etUname.getText().toString().equals("") && !MainActivity.this.etPass.getText().toString().equals("")){
                Cursor cursorAdmin = myDB.readAllAdmins();
                Cursor cursorStud = myDB.readAllStudents();
                if (etUname.getText().toString().equals("ADMIN") && etPass.getText().toString().equals("ADMIN")) {
                    try {
                        startAdmin();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                if(cursorAdmin.getCount() != 0) {
                    while (cursorAdmin.moveToNext()) {
                        if (etUname.getText().toString().equals(cursorAdmin.getString(1)) && etPass.getText().toString().equals(cursorAdmin.getString(2))) {
                            try {
                                startAdmin();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
                if (cursorStud.getCount() != 0) {
                    while (cursorStud.moveToNext()) {
                        if (etUname.getText().toString().equals(cursorStud.getString(1)) && etPass.getText().toString().equals(cursorStud.getString(2))) {
                            try {
                                startStud(cursorStud.getLong(0));
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }else Toast.makeText(MainActivity.this, "Neispravan unos", Toast.LENGTH_SHORT).show();
            }else Toast.makeText(MainActivity.this, "Popunite sva polja", Toast.LENGTH_SHORT).show();
        });
    }

    /*void replaceFrag(Fragment frag){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.frameLayout, frag);
        transaction.commit();
    }*/

    /*void createTablesAndInitData(){
        dbHelper.createTables();
    }*/

    public void startAdmin() throws InterruptedException {
        Intent intent = new Intent(this, AdminActivity.class);
        //ReceiveMessageFromServer.setPauseReading(true);
        ActivityLauncher.launch(intent);
    }
    public void startStud(Long id) throws InterruptedException {
        Intent intent = new Intent(this, StudActivity.class);
        intent.putExtra(ID,id);
        //ReceiveMessageFromServer.setPauseReading(true);
        ActivityLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> ActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                //et.setText("");
                if (result.getResultCode() == RESULT_OK){
                    //Intent data = result.getData();
                    //String response;
                    //response = data.getStringExtra(GameActivity.RESPONSE_MESSAGE);
                    //tv.setText(response);
                }
            }
    );
}