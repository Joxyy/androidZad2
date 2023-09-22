package com.example.eindexdb.sqlite.helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


import com.example.eindexdb.sqlite.model.Predmet;
import com.example.eindexdb.sqlite.model.Student;

import java.util.ArrayList;
import java.util.List;

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
    private static final String TABLE_BOD = "bodovi";
    private static final String TABLE_GRADES = "ocene";
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
    private static final String KEY_KAT_ID = "katID";
    //kat column names
    private static final String KEY_STUD_ID = "studentId";
    private static final String KEY_MIN = "min";
    private static final String KEY_MAX = "max";
    private static final String KEY_BODOVI = "bodovi";
    private static final String KEY_OCENA = "ocena";

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
            + KEY_MIN + " INTEGER, "
            + KEY_PREDMET_ID + " INTEGER, "
            + KEY_GODINA_ID + " INTEGER);";
    String CREATE_TABLE_BOD = "CREATE TABLE IF NOT EXISTS " + TABLE_BOD +
            " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_BODOVI + " INTEGER, "
            + KEY_KAT_ID + " INTEGER, "
            + KEY_STUD_ID + " INTEGER);";
    String CREATE_TABLE_GRADES = "CREATE TABLE IF NOT EXISTS " + TABLE_GRADES +
            " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_OCENA + " TEXT, "
            + KEY_STUD_ID + " INTEGER, "
            + KEY_PREDMET_ID + " INTEGER, "
            + KEY_GODINA_ID + " INTEGER);";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_STUDENTS);
        db.execSQL(CREATE_TABLE_YEARS);
        db.execSQL(CREATE_TABLE_SUBJECTS);
        db.execSQL(CREATE_TABLE_GRADES);
        db.execSQL(CREATE_TABLE_BOD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_YEARS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GRADES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOD);

    onCreate(db);
    }
    //This to be used when needed to create tables from scratch
    public void createTables(){
        if(db==null)    db = getWritableDatabase();

        //vreating required tables
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_STUDENTS);
        db.execSQL(CREATE_TABLE_YEARS);
        db.execSQL(CREATE_TABLE_SUBJECTS);
        db.execSQL(CREATE_TABLE_KAT);
        db.execSQL(CREATE_TABLE_GRADES);
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
    public long addPred(String pred){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues(); //za upis u bazu se koristi, cuva kljuc vrednost oblik

        values.put(KEY_IME, pred);

        //insert row
        long pred_id = db.insert(TABLE_SUBJECTS, null, values);//null sluzzi za upis bez neke kolone (tj onda unosimo tu kolonu)
        if(pred_id == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else Toast.makeText(context, "Added successfully", Toast.LENGTH_SHORT).show();
        //now we know id obtained after writing actor to a db
        return pred_id;
    }
    public long addYear(String god){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues(); //za upis u bazu se koristi, cuva kljuc vrednost oblik

        values.put(KEY_IME, god);

        //insert row
        long god_id = db.insert(TABLE_YEARS, null, values);//null sluzzi za upis bez neke kolone (tj onda unosimo tu kolonu)
        if(god_id == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else Toast.makeText(context, "Added successfully", Toast.LENGTH_SHORT).show();
        //now we know id obtained after writing actor to a db
        return god_id;
    }
    public void addKat(Predmet p){
        SQLiteDatabase db = this.getWritableDatabase();

        for(String kat : p.getKategorije()){
            ContentValues values = new ContentValues(); //za upis u bazu se koristi, cuva kljuc vrednost oblik
            values.put(KEY_IME, kat);
            values.put(KEY_MAX, p.getMaxPoeni().get(p.getKategorije().indexOf(kat)));
            values.put(KEY_MIN, p.getMinPoeni().get(p.getKategorije().indexOf(kat)));
            values.put(KEY_PREDMET_ID, findPredID(p.getNazivPredmeta()));
            values.put(KEY_GODINA_ID, findGodID(p.getGod()));

            //insert row
            long pred_id = db.insert(TABLE_KAT, null, values);//null sluzzi za upis bez neke kolone (tj onda unosimo tu kolonu)
            if(pred_id == -1){
                Toast.makeText(context, kat + "Failed", Toast.LENGTH_SHORT).show();
            }else Toast.makeText(context, kat + "Added successfully", Toast.LENGTH_SHORT).show();
        }
    }
    public int addBod(String index, String pred, String god){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues(); //za upis u bazu se koristi, cuva kljuc vrednost oblik

        for(String t : findKatByPredGod(pred, god)) {
            //values.put(KEY_KAT_ID, findKatID(pred, god, findKatByPredGod(pred, god)));
            values.put(KEY_KAT_ID, findKatID(pred,god,t));
            values.put(KEY_STUD_ID, findStudID(index));
            values.put(KEY_BODOVI, 0);


            //insert row
            long bod_id = db.insert(TABLE_BOD, null, values);//null sluzzi za upis bez neke kolone (tj onda unosimo tu kolonu)
            if (bod_id == -1) {
                Toast.makeText(context, " Failed", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(context, " Added successfully", Toast.LENGTH_SHORT).show();
        }
        //now we know id obtained after writing actor to a db
        return 0;
    }
    public long updateBod(String index, String pred, String god, String kat, int bod){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues(); //za upis u bazu se koristi, cuva kljuc vrednost oblik

        values.put(KEY_KAT_ID, findKatID(pred, god, kat));
        values.put(KEY_STUD_ID, findStudID(index));
        values.put(KEY_BODOVI, bod);

        //insert row
        long bod_id = db.update(TABLE_BOD, values, "_id=?", new String[]{String.valueOf(findBodID(index,pred,god, kat))});;//null sluzzi za upis bez neke kolone (tj onda unosimo tu kolonu)
        if(bod_id == -1){
            Toast.makeText(context, " Failed", Toast.LENGTH_SHORT).show();
        }else Toast.makeText(context, " Added successfully", Toast.LENGTH_SHORT).show();
        //now we know id obtained after writing actor to a db
        return bod_id;
    }
    public void updateGrades(String index, String pred, String god, String ocena){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues(); //za upis u bazu se koristi, cuva kljuc vrednost oblik

        values.put(KEY_OCENA, ocena);

        //insert row
        long ocena_id = db.update(TABLE_GRADES, values, "_id=?", new String[]{String.valueOf(findOcenaID(index,pred,god))});
        if(ocena_id == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else Toast.makeText(context, "Added successfully", Toast.LENGTH_SHORT).show();
        //now we know id obtained after writing actor to a db


    }
    public long addStudNaPred(String index, String pred, String god){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues(); //za upis u bazu se koristi, cuva kljuc vrednost oblik

        values.put(KEY_STUD_ID, findStudID(index));
        values.put(KEY_PREDMET_ID, findPredID(pred));
        values.put(KEY_GODINA_ID, findGodID(god));
        values.put(KEY_OCENA, "nije polozio");

        //insert row
        long ocena_id = db.insert(TABLE_GRADES, null, values);//null sluzzi za upis bez neke kolone (tj onda unosimo tu kolonu)
        if(ocena_id == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else Toast.makeText(context, "Added successfully", Toast.LENGTH_SHORT).show();
        //now we know id obtained after writing actor to a db
        return ocena_id;
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
    public Cursor readAllSubjects(){
        String query = "SELECT * FROM " + TABLE_SUBJECTS;
        SQLiteDatabase db = this.getReadableDatabase();

        Log.e(LOG, query);

        Cursor cursor = null;
        if(db!=null){
            cursor=db.rawQuery(query, null);
        }
        return cursor;
    }
    public Cursor readAllYears(){
        String query = "SELECT * FROM " + TABLE_YEARS;
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
    @SuppressLint("Range")
    public String getPred(Long pred_id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_SUBJECTS + " WHERE " + KEY_ID + " = " + pred_id;

        Log.e(LOG, query);

        Cursor cursor = db.rawQuery(query,null);
        if(cursor!=null) cursor.moveToFirst();

        String p = cursor.getString(cursor.getColumnIndex(KEY_IME));
        return p;

    }
    @SuppressLint("Range")
    public Predmet getPredKat(String pred, String god){
        Predmet p = new Predmet();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_KAT + " WHERE " + KEY_PREDMET_ID + " = " + findPredID(pred) + " AND " + KEY_GODINA_ID + " = " + findGodID(god);

        Log.e(LOG, query);



        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                p.dodKat(cursor.getString(cursor.getColumnIndex(KEY_IME)));
                p.dodMax(cursor.getInt(cursor.getColumnIndex(KEY_MAX)));
                p.dodMin(cursor.getInt(cursor.getColumnIndex(KEY_MIN)));

            }while (cursor.moveToNext());
        }
        if(cursor!=null) cursor.moveToFirst();

        return p;

    }
    @SuppressLint("Range")
    public String getGod(Long god_id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_YEARS + " WHERE " + KEY_ID + " = " + god_id;

        Log.e(LOG, query);

        Cursor cursor = db.rawQuery(query,null);
        if(cursor!=null) cursor.moveToFirst();

        String g = cursor.getString(cursor.getColumnIndex(KEY_IME));
        return g;

    }
    @SuppressLint("Range")
    public long findPredID(String pred){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_SUBJECTS + " WHERE " + KEY_IME + " = '" + pred + "'";

        Log.e(LOG, query);

        Cursor cursor = db.rawQuery(query,null);
        if(cursor!=null) cursor.moveToFirst();
        long pred_id = cursor.getLong(cursor.getColumnIndex(KEY_ID));
        return pred_id;
    }
    @SuppressLint("Range")
    public long findOcenaID(String index, String pred, String god){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_GRADES + " WHERE " + KEY_PREDMET_ID + " = " + findPredID(pred) + " AND " + KEY_GODINA_ID + " = " + findGodID(god)
        +" AND " + KEY_STUD_ID + " = " + findStudID(index);

        Log.e(LOG, query);

        Cursor cursor = db.rawQuery(query,null);
        if(cursor!=null) cursor.moveToFirst();
        long ocena_id = cursor.getLong(cursor.getColumnIndex(KEY_ID));
        return ocena_id;
    }
    @SuppressLint("Range")
    public long findBodID(String index, String pred, String god, String kat){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_BOD + " WHERE " + KEY_KAT_ID + " = " + findKatID(pred, god, kat)
                +" AND " + KEY_STUD_ID + " = " + findStudID(index);

        Log.e(LOG, query);

        Cursor cursor = db.rawQuery(query,null);
        if(cursor!=null) cursor.moveToFirst();
        long bod_id = cursor.getLong(cursor.getColumnIndex(KEY_ID));
        return bod_id;
    }
    @SuppressLint("Range")
    public long findGodID(String god){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_YEARS + " WHERE " + KEY_IME + " = '" + god + "'";

        Log.e(LOG, query);

        Cursor cursor = db.rawQuery(query,null);
        if(cursor!=null) cursor.moveToFirst();
        long god_id = cursor.getLong(cursor.getColumnIndex(KEY_ID));
        return god_id;
    }
    @SuppressLint("Range")
    public long findStudID(String index){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_STUDENTS + " WHERE " + KEY_INDEX + " = '" + index + "'";

        Log.e(LOG, query);

        Cursor cursor = db.rawQuery(query,null);
        if(cursor!=null) cursor.moveToFirst();
        long stud_id = cursor.getLong(cursor.getColumnIndex(KEY_ID));
        return stud_id;
    }
    @SuppressLint("Range")
    public ArrayList<String> findPredByStudID(long stud_id){
        ArrayList<String> predmeti = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_GRADES + " WHERE " + KEY_STUD_ID + " = " + stud_id;

        //String query = "SELECT  p." + KEY_ID + ", m." + KEY_ID + " FROM " + TABLE_SUBJECTS + " p, "
        //        + TABLE_YEARS + " g, " + TABLE_GRADES + " o WHERE " + KEY_ID + " = " + stud_id;

        Log.e(LOG, query);

        Cursor cursor = db.rawQuery(query,null);
        if(cursor!=null) {
            cursor.moveToFirst();
            do{
                long pred_id = cursor.getInt(cursor.getColumnIndex(KEY_PREDMET_ID));
                String p= getPred(pred_id);
                long god_id = cursor.getInt(cursor.getColumnIndex(KEY_GODINA_ID));
                String g= getGod(god_id);
                predmeti.add(p + " " + g);
            }while(cursor.moveToNext());
            return predmeti;
        }
        return null;
    }
    @SuppressLint("Range")
    public ArrayList<String> findKatByPredGod(String pred, String god){
        ArrayList<String> kat = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_KAT + " WHERE " + KEY_PREDMET_ID + " = " + findPredID(pred) + " AND " + KEY_GODINA_ID + " = " + findGodID(god);

        Log.e(LOG, query);

        Cursor cursor = db.rawQuery(query,null);
        if(cursor!=null) {
            cursor.moveToFirst();
            do{
                kat.add(cursor.getString(cursor.getColumnIndex(KEY_IME)));
            }while(cursor.moveToNext());
            return kat;
        }
        return null;
    }


    @SuppressLint("Range")
    public int findBodovi(String kat,String pred, String god, String index){

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_BOD + " WHERE " + KEY_KAT_ID + " = " + findKatID(pred,god, kat) + " AND " + KEY_STUD_ID + " = " + findStudID(index);

        Log.e(LOG, query);

        Cursor cursor = db.rawQuery(query,null);
        if(cursor!=null) cursor.moveToFirst();
        int bodovi= cursor.getInt(cursor.getColumnIndex(KEY_BODOVI));
        return bodovi;
    }
    @SuppressLint("Range")
    public long findKatID(String pred, String god, String kat){

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_KAT + " WHERE " + KEY_PREDMET_ID + " = " + findPredID(pred) + " AND " + KEY_GODINA_ID + " = " + findGodID(god)
        + " AND " + KEY_IME + " = '" + kat + "'";

        Log.e(LOG, query);

        Cursor cursor = db.rawQuery(query,null);
        if(cursor!=null) cursor.moveToFirst();
        long kat_id = cursor.getLong(cursor.getColumnIndex(KEY_ID));
        return kat_id;
    }
    @SuppressLint("Range")
    public String findOcena(Long studID, String pred, String god){

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_GRADES + " WHERE " + KEY_STUD_ID + " = " + studID + " AND " + KEY_GODINA_ID + " = " + findGodID(god)
                + " AND " + KEY_PREDMET_ID + " = " + findPredID(pred);

        Log.e(LOG, query);

        Cursor cursor = db.rawQuery(query,null);
        if(cursor!=null) cursor.moveToFirst();
        String ocena = cursor.getString(cursor.getColumnIndex(KEY_OCENA));
        return ocena;
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
