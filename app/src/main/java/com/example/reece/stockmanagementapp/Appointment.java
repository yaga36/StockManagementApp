package com.example.reece.stockmanagementapp;

/**
 * Created by Reece on 09/02/2016.
 */

public class Appointment {
    private int _id;
    private String _name;
    private String _surname;
    private String _email;
    private String _date;
    private String _time;
    private String _service;
    private int _count;


    public Appointment(){

    }

    public Appointment(int id, String name, String surname, String email, String date, String time, String service, int count){
        this._id = id;
        this._name = name;
        this._surname = surname;
        this._email = email;
        this._date = date;
        this._time = time;
        this._service = service;
        this._count = count;
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


    public void setDate(String date) {
        this._date = date;
    }

    public String getDate() {
        return this._date;
    }

    public void setTime(String time) {
        this._time = time;
    }

    public String getTime() {
        return this._time;
    }

    public void setService(String service){
        this._service = service;
    }

    public String getService(){
        return this._service;
    }

    public void setCount(int count){
        this._count = count;
    }

    public int getCount(){
        return this._count;
    }
}
