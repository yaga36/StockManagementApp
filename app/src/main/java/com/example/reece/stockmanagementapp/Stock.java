package com.example.reece.stockmanagementapp;

/**
 * Created by Reece on 09/02/2016.
 */

public class Stock {
    private int _id;
    private String _barcode;
    private String _productname;
    private String _producttype;
    private int _quantity;


    public Stock(){

    }

    public Stock(int id, String barcode, String productname, String producttype,int quantity){
        this._id = id;
        this._barcode = barcode;
        this._productname = productname;
        this._producttype = producttype;
        this._quantity = quantity;
    }

    public Stock(String productname, int quantity){
        this._productname = productname;
        this._quantity = quantity;
    }

    public void setID(int id){
        this._id = id;
    }

    public int getID(){
        return this._id;
    }

    public void setBarcode(String barcode){
        this._barcode = barcode;
    }

    public String getBarcode(){
        return this._barcode;
    }

    public void setProductName(String productname){
        this._productname = productname;
    }

    public String getProductName() {
        return this._productname;
    }

    public void setProductType(String producttype){
        this._producttype = producttype;
    }

    public String getProductType() {
        return this._producttype;
    }


    public void setQuantity(int quantity) {
        this._quantity = quantity;
    }

    public int getQuantity() {
        return this._quantity;
    }
}
