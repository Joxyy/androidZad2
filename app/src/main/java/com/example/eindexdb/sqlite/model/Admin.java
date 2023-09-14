package com.example.eindexdb.sqlite.model;

public class Admin {
    long id;
    String username;
    String password;

    public Admin(String uname, String pass){
        this.username=uname;
        this.password=pass;
    }
    public Admin(){}

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String uname) {
        this.username = uname;
    }

    public void setPass(String pass) {
        this.password = pass;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPass() {
        return password;
    }
}
