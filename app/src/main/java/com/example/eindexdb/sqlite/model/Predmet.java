package com.example.eindexdb.sqlite.model;

import java.util.ArrayList;

public class Predmet {
    private String nazivPredmeta;
    private long id;
    private String god;
    private ArrayList<String> kategorije = new ArrayList<String>();
    private ArrayList<Integer> maxPoeni= new ArrayList<Integer>();
    private ArrayList<Integer> minPoeni= new ArrayList<Integer>();
    private ArrayList<Integer> poeni = new ArrayList<Integer>();
    private int Ocena;

    public ArrayList<Integer> getMaxPoeni() {
        return maxPoeni;
    }

    public ArrayList<Integer> getMinPoeni() {
        return minPoeni;
    }

    public void dodKat(String kat) {
        kategorije.add(kat);
    }
    public void dodMax(int max) {
        maxPoeni.add(max);
    }
    public void setMax(int index, int max) {
        maxPoeni.set(index, max);
    }
    public void setMin(int index, int min) {
        minPoeni.set(index, min);
    }

    public void dodMin(int min) {
        minPoeni.add(min);
    }

    public ArrayList<String> getKategorije() {
        return kategorije;
    }

    public String getNazivPredmeta() {
        return nazivPredmeta;
    }

    public long getId() {
        return id;
    }

    public String getGod() {
        return god;
    }

    public int getOcena() {
        return Ocena;
    }

    public void setNazivPredmeta(String nazivPredmeta) {
        this.nazivPredmeta = nazivPredmeta;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setGod(String god) {
        this.god = god;
    }

    public void setOcena(int ocena) {
        Ocena = ocena;
    }
}
