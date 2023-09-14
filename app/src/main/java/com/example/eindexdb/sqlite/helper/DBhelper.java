package com.example.eindexdb.sqlite.helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.eindexdb.sqlite.model.Admin;
import com.example.eindexdb.sqlite.model.Student;

import java.util.ArrayList;

public class DBhelper extends SQLiteOpenHelper {
    SQLiteDatabase db;

    private Context context;
    //Logcat tag
    private static final String LOG = "Databasehelper";

    //DB version
    private static final int DATABASE_VERSION = 1;

    //DBname
    private static final String DATABASE_NAME = "SSluzba.db";
    public DBhelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    //table names
    private static final String TABLE_ADMINS = "admini";
    private static final String TABLE_STUDENTS = "studenti";
    private static final String TABLE_YEAR = "godina";
    private static final String TABLE_SUBJECTS = "predmeti";
    private static final String TABLE_CATEGORIES = "kategorije";

    //common column names
    private static final String KEY_ID = "_id";
    private static final String KEY_UNAME = "uname";
    private static final String KEY_PASS = "pass";
    //stud column names
    private static final String KEY_IME = "ime";
    private static final String KEY_PREZIME = "prezime";
    private static final String KEY_INDEX = "indeks";
    private static final String KEY_JMBG = "jmbg";
    //stud column names
    private static final String KEY_PREDMET = "predmet";
    private static final String KEY_KATEGORIJA = "kategorija";
    private static final String KEY_MIN = "min ";
    private static final String KEY_MAX = "max ";
    private static final String KEY_BODOVI = "bodovi ";
    private static final String KEY_OCENA = "ocena ";

    //table create statements
    String CREATE_TABLE_ADMINS = "CREATE TABLE IF NOT EXISTS " + TABLE_ADMINS +
            " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_UNAME + " TEXT, " +
            KEY_PASS + " TEXT);";
    String CREATE_TABLE_STUDENTS = "CREATE TABLE IF NOT EXISTS "
            + TABLE_STUDENTS + " (" + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_UNAME + " TEXT, "
            + KEY_PASS + " TEXT, "
            + KEY_IME + " TEXT, "
            + KEY_PREZIME + " TEXT, "
            + KEY_INDEX + " TEXT, "
            + KEY_JMBG + " INTEGER);";
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ADMINS);
        db.execSQL(CREATE_TABLE_STUDENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMINS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);

    onCreate(db);
    }
    //This to be used when needed to create tables from scratch
    public void createTables(){
        if(db==null)    db = getWritableDatabase();

        //vreating required tables
        db.execSQL(CREATE_TABLE_ADMINS);
        db.execSQL(CREATE_TABLE_STUDENTS);
    }
    public void dropTables(){   //delete tabels

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMINS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
    }

    /*creating an admin*/
    public long addAdmin(String uname, String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues(); //za upis u bazu se koristi, cuva kljuc vrednost oblik

        values.put(KEY_UNAME, uname);
        values.put(KEY_PASS, pass);

        //insert row
        long admin_id = db.insert(TABLE_ADMINS, null, values);//null sluzzi za upis bez neke kolone (tj onda unosimo tu kolonu)
        if(admin_id == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else Toast.makeText(context, "Added successfully", Toast.LENGTH_SHORT).show();
        //now we know id obtained after writing actor to a db
        return admin_id;
    }
    public long addStud(String studUname, String studPass, String studIme, String studPrezime, String studIndex, String studJmbg){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues(); //za upis u bazu se koristi, cuva kljuc vrednost oblik

        values.put(KEY_UNAME, studUname);
        values.put(KEY_PASS, studPass);
        values.put(KEY_IME, studIme);
        values.put(KEY_PREZIME, studPrezime);
        values.put(KEY_INDEX, studIndex);
        values.put(KEY_JMBG, studJmbg);


        //insert row
        long stud_id = db.insert(TABLE_STUDENTS, null, values);
        if(stud_id == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else Toast.makeText(context, "Added successfully", Toast.LENGTH_SHORT).show();
        //stud.setId(stud_id);
        //now we know id obtained after writing actor to a db
        return stud_id;
    }
    Cursor readAllAdmins(){
        String query = "SELECT * FROM " + TABLE_ADMINS;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db!=null){
            cursor=db.rawQuery(query, null);
        }
        return cursor;
    }

    /*getting all students*/
    /*@SuppressLint("Range")
    public ArrayList<Student> getAllStudents(){
        ArrayList<Student> students = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_STUDENTS;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);
        //looping through all rows and adding to list
        if(c.moveToFirst()){
            do{
                Student s = new Student();
                s.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                s.setUsername(c.getString(c.getColumnIndex(KEY_UNAME)));
                s.setPass(c.getString(c.getColumnIndex(KEY_PASS)));
                s.setIme(c.getString(c.getColumnIndex(KEY_IME)));
                s.setPrezime(c.getString(c.getColumnIndex(KEY_PREZIME)));
                s.setBrIndexa(c.getString(c.getColumnIndex(KEY_INDEX)));
                s.setJmbg(c.getString(c.getColumnIndex(KEY_JMBG)));
            }while (c.moveToNext());
        }
        return students;
    }
    @SuppressLint("Range")
    public ArrayList<Admin> getAllAdmins(){
        ArrayList<Admin> admins = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_ADMINS;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);
        //looping through all rows and adding to list
        if(c.moveToFirst()){
            do{
                Admin a = new Admin();
                a.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                a.setUsername(c.getString(c.getColumnIndex(KEY_UNAME)));
                a.setPass(c.getString(c.getColumnIndex(KEY_PASS)));
            }while (c.moveToNext());
        }
        return admins;
    }*/
}