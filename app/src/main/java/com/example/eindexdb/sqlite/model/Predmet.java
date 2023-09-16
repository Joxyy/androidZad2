package com.example.eindexdb.sqlite.model;

import java.util.ArrayList;

public class Predmet {
    private String nazivPredmeta;
    private ArrayList<String> godine = new ArrayList<String>();
    public ArrayList<String> kategorije = new ArrayList<String>();
    public ArrayList<Integer> maxPoeni= new ArrayList<Integer>();
    public ArrayList<Integer> minPoeni= new ArrayList<Integer>();
    public ArrayList<Integer> poeni = new ArrayList<Integer>();
    private int Ocena;
}
