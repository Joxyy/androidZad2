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
    private static final String TABLE_USERS = "users";
    private static final String TABLE_STUDENTS = "studenti";
    private static final String TABLE_YEARS = "godine";
    private static final String TABLE_SUBJECTS = "predmeti";
    private static final String TABLE_KAT = "kategorije";

    //common column names
    private static final String KEY_ID = "_id";
    private static final String KEY_UNAME = "uname";
    private static final String KEY_PASS = "pass";
    private static final String KEY_ROLE = "role";
    //stud column names
    private static final String KEY_USER_ID = "userID";
    private static final String KEY_IME = "ime";
    private static final String KEY_PREZIME = "prezime";
    private static final String KEY_INDEX = "indeks";
    private static final String KEY_JMBG = "jmbg";
    //godPred column names
    private static final String KEY_PREDMET_ID = "predmetID";
    private static final String KEY_GODINA_ID = "godinaID";
    //kat column names
    private static final String KEY_PREDMET = "predmet";
    private static final String KEY_KATEGORIJA = "kategorija";
    private static final String KEY_MIN = "min ";
    private static final String KEY_MAX = "max ";
    private static final String KEY_BODOVI = "bodovi ";
    private static final String KEY_OCENA = "ocena ";

    //table create statements
    String CREATE_TABLE_USERS = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS +
            " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_UNAME + " TEXT, " +
            KEY_PASS + " TEXT, " +
            KEY_ROLE + " TEXT);";
    String CREATE_TABLE_STUDENTS = "CREATE TABLE IF NOT EXISTS "
            + TABLE_STUDENTS + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_USER_ID + " INTEGER, "
            + KEY_IME + " TEXT, "
            + KEY_PREZIME + " TEXT, "
            + KEY_INDEX + " TEXT, "
            + KEY_JMBG + " INTEGER);";
    String CREATE_TABLE_YEARS = "CREATE TABLE IF NOT EXISTS " + TABLE_YEARS +
            " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_IME + " TEXT);";
    String CREATE_TABLE_SUBJECTS = "CREATE TABLE IF NOT EXISTS " + TABLE_SUBJECTS +
            " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_IME + " TEXT);";
    String CREATE_TABLE_KAT = "CREATE TABLE IF NOT EXISTS " + TABLE_KAT +
            " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_IME + " INTEGER, "
            + KEY_MAX + " INTEGER, "
            + KEY_PREDMET_ID + " INTEGER, "
            + KEY_GODINA_ID + " INTEGER);";
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_STUDENTS);
        db.execSQL(CREATE_TABLE_YEARS);
        db.execSQL(CREATE_TABLE_SUBJECTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_YEARS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECTS);

    onCreate(db);
    }
    //This to be used when needed to create tables from scratch
    public void createTables(){
        if(db==null)    db = getWritableDatabase();

        //vreating required tables
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_STUDENTS);
    }
    public void dropTables(){   //delete tabels

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
    }

    /*creating an admin*/
    public long addAdmin(String uname, String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues(); //za upis u bazu se koristi, cuva kljuc vrednost oblik

        values.put(KEY_UNAME, uname);
        values.put(KEY_PASS, pass);
        values.put(KEY_ROLE, "admin");

        //insert row
        long admin_id = db.insert(TABLE_USERS, null, values);//null sluzzi za upis bez neke kolone (tj onda unosimo tu kolonu)
        if(admin_id == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else Toast.makeText(context, "Added successfully", Toast.LENGTH_SHORT).show();
        //now we know id obtained after writing actor to a db
        return admin_id;
    }
    public long addStud(String studUname, String studPass, String studIme, String studPrezime, String studIndex, String studJmbg){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values1 = new ContentValues(); //za upis u bazu se koristi, cuva kljuc vrednost oblik

        values1.put(KEY_UNAME, studUname);
        values1.put(KEY_PASS, studPass);
        values1.put(KEY_ROLE, "student");

        long user_id = db.insert(TABLE_USERS, null, values1);
        if(user_id == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else Toast.makeText(context, "Added successfully", Toast.LENGTH_SHORT).show();

        ContentValues values2 = new ContentValues();
        values2.put(KEY_USER_ID, user_id);
        values2.put(KEY_IME, studIme);
        values2.put(KEY_PREZIME, studPrezime);
        values2.put(KEY_INDEX, studIndex);
        values2.put(KEY_JMBG, studJmbg);

        //insert row
        long stud_id = db.insert(TABLE_STUDENTS, null, values2);
        if(stud_id == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else Toast.makeText(context, "Added successfully", Toast.LENGTH_SHORT).show();
        //stud.setId(stud_id);
        //now we know id obtained after writing actor to a db
        return user_id;
    }
    public void addGodPred(String godina, String predmet){

    }
    public Cursor readAllUsers(){
        String query = "SELECT * FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getReadableDatabase();

        Log.e(LOG, query);

        Cursor cursor = null;
        if(db!=null){
            cursor=db.rawQuery(query, null);
        }
        return cursor;
    }
    public Cursor readAllStudents(){
        String query = "SELECT * FROM " + TABLE_STUDENTS;
        SQLiteDatabase db = this.getReadableDatabase();

        Log.e(LOG, query);

        Cursor cursor = null;
        if(db!=null){
            cursor=db.rawQuery(query, null);
        }
        return cursor;
    }
    @SuppressLint("Range")
    public Student getStud(Long stud_id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_STUDENTS + " WHERE " + KEY_USER_ID + " = " + stud_id;

        Log.e(LOG, query);

        Cursor cursor = db.rawQuery(query,null);
        if(cursor!=null) cursor.moveToFirst();

        Student s = new Student();

        s.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
        s.setIme(cursor.getString(cursor.getColumnIndex(KEY_IME)));
        s.setPrezime(cursor.getString(cursor.getColumnIndex(KEY_PREZIME)));
        s.setBrIndexa(cursor.getString(cursor.getColumnIndex(KEY_INDEX)));
        s.setJmbg(cursor.getString(cursor.getColumnIndex(KEY_JMBG)));

        //predmet p =getPredmet(c.getInd(c.getColumnIndex(KEY_PREDMET

        return s;

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
