package com.example.eindexdb;

import android.content.Intent;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText etUname;
    EditText etPass;
    Button btnLogin;

    DBhelper myDB;
    ArrayList<String> studenti_id, studenti_pass, 
    public static String UNAME = "RequestKey1";

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

        btnLogin.setOnClickListener(view1 -> {
            if (etUname.getText().toString().equals("ADMIN")&& etPass.getText().toString().equals("ADMIN")){
                try {
                    startAdmin(etUname.getText().toString());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
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

    public void startAdmin(String uname) throws InterruptedException {
        Intent intent = new Intent(this, AdminActivity.class);
        intent.putExtra(UNAME, uname);
        //ReceiveMessageFromServer.setPauseReading(true);
        gameActivityLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> gameActivityLauncher = registerForActivityResult(
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