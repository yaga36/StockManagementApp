package com.example.reece.stockmanagementapp;

/**
 * Created by Reece on 27/02/2016.
 */
public class Register {

    private int _id;
    private String _name;
    private String _username;
    private String _password;
    private String _user;


    public Register(){

    }

    public Register(int id, String name, String username, String password, String  user){
        this._id = id;
        this._name = name;
        this._username = username;
        this._password = password;
        this._user = user;
    }

    public void setID(int id){
        this._id = id;
    }

    public int getID(){
        return this._id;
    }

    public void setName(String name){
        this._name = name;
    }

    public String getName(){
        return this._name;
    }

    public void setUsername(String username){
        this._username = username;
    }

    public String getUsername(){
        return this._username;
    }

    public void setPassword(String password){
        this._password = password;
    }

    public String getPassword(){
        return this._password;
    }
    public void setUser(String user){
        this._user = user;
    }

    public String getUser(){
        return this._user;
    }

}
