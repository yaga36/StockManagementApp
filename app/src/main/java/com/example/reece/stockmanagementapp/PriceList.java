package com.example.reece.stockmanagementapp;

/**
 * Created by Reece on 17/02/2016.
 */


public class PriceList {
    private int _id;
    private String _service;
    private int _price;
    private int _duration;

    public PriceList(){

    }

    public PriceList(int id, String service, int price, int duration){
        this._id = id;
        this._service = service;
        this._price = price;
        this._duration = duration;
    }

    public void setID(int id){
        this._id = id;
    }

    public int getID(){
        return this._id;
    }

    public void setService(String service){
        this._service = service;
    }

    public String getService(){
        return this._service;
    }

    public void setPrice(int price){
        this._price = price;
    }

    public int getPrice() {
        return this._price;
    }

    public void setDuration(int duration) {
        this._duration = duration;
    }

    public int getDuration() {
        return this._duration;
    }


}


