package com.example.reece.stockmanagementapp;

/**
 * Created by Reece on 09/02/2016.
 */

public class Client {
    private int _id;
    private String _name;
    private String _surname;
    private String _email;
    private String _dob;


    public Client(){

    }

    public Client(int id, String name, String surname, String email, String dob){
        this._id = id;
        this._name = name;
        this._surname = surname;
        this._email = email;
        this._dob = dob;
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

    public void setSurname(String surname){
        this._surname = surname;
    }

    public String getSurname() {
        return this._surname;
    }

    public void setEmail(String email){
        this._email = email;
    }

    public String getEmail() {
        return this._email;
    }


    public void setDOB(String dob) {
        this._dob = dob;
    }

    public String getDOB() {
        return this._dob;
    }
}
