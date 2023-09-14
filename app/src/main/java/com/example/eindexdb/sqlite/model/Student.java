package com.example.eindexdb.sqlite.model;

import java.util.ArrayList;

public class Student {
    long id;
    private String ime;
    private String prezime;
    private String brIndexa;    //moze bilo sta
    private String jmbg;        //13 kar
    public ArrayList<Predmet> predmetiKojeSlusa = new ArrayList<Predmet>();

    private String username;
    private String pass;

    public long getId() {
        return id;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public String getBrIndexa() {
        return brIndexa;
    }

    public String getJmbg() {
        return jmbg;
    }

    public String getUsername() {
        return username;
    }

    public String getPass() {
        return pass;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public void setBrIndexa(String brIndexa) {
        this.brIndexa = brIndexa;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Student(String ime, String prezime, String brIndexa, String jmbg, String username, String password) {

        this.brIndexa=brIndexa;

        this.ime=ime;
        this.prezime=prezime;
        this.jmbg=jmbg;
        this.username=username;
        this.pass=password;

        /*PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(Ssluzba.getF(),true), "UTF8"));
        out.println(this.username + ":"+ this.pass + ":" + "student");
        out.flush();
        out.close();*/

    }
    public Student(){ }
}
