package com.example.reece.stockmanagementapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PriceListNormalActivity extends AppCompatActivity {

    //Declaring all the variables for the class
    DatabaseHelper db;

    Utility util;

    Button editPriceList, addPrice, searchPrice, viewPrice, homeButton;

    EditText editTextPrice, editService, editDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_list_normal);

        db = new DatabaseHelper(this);

        util = new Utility();

        //Assigning the edit text variables to the edit text fields in xml
        editTextPrice = (EditText) findViewById(R.id.priceEditText);
        editService = (EditText) findViewById(R.id.serviceEditText);
        editDuration = (EditText) findViewById(R.id.durationEditText);


        //Assigning the button variables to the buttons in the xml
        editPriceList = (Button) findViewById(R.id.editPriceBtnNormal);
        addPrice = (Button) findViewById(R.id.addPriceBtnNormal);
        searchPrice = (Button) findViewById(R.id.searchPriceBtnNormal);
        viewPrice = (Button) findViewById(R.id.viewPriceBtnNormal);
        homeButton = (Button) findViewById(R.id.returnHomeBtnNormal);


        AddData();
        ViewAll();
        UpdateData();
        lookupService();


        //Methods to tell the buttons what to do when clicked
        homeButton.setOnClickListener
                (new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(PriceListNormalActivity.this,
                                MainNormalActivity.class);
                        startActivity(intent);
                    }
                });

    }

    //Method to add data into the database from the edit text fields
    public void AddData() {
        addPrice.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (editService.getText().toString().trim().length() <= 0 ||
                                editTextPrice.getText().toString().trim().length() <= 0 ||
                                editDuration.getText().toString().trim().length() <= 0) {

                            util.showMessage("Missing Fields", "Fields \n\n Service \n Price \n Duration"
                                    + " \n DOB \n\n" + "Required", PriceListNormalActivity.this);
                        } else {
                            boolean isInserted = db.insertPriceData
                                    (editService.getText().toString(),
                                            editTextPrice.getText().toString(),
                                            editDuration.getText().toString());
                            if (isInserted)
                                Toast.makeText(PriceListNormalActivity.this, "Data Not Inserted",
                                        Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(PriceListNormalActivity.this, "Data Inserted",
                                        Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    //Method to view all data that is stored within the database
    public void ViewAll() {
        viewPrice.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor result = db.getAllPriceData();
                        if (result.getCount() == 0) {
                            //Show Message
                            util.showMessage("Error", "No data to display",
                                    PriceListNormalActivity.this);
                            return;
                        }
                        StringBuilder buffer = new StringBuilder();
                        while (result.moveToNext()) {
                            buffer.append("ID: " + result.getString(result.getColumnIndex(
                                    db.PRICE_ID)) + "\n");
                            buffer.append("Service: " + result.getString(result.getColumnIndex(
                                    db.PRICE_SERVICE)) + "\n");
                            buffer.append("Price: " + result.getString(result.getColumnIndex(
                                    db.PRICE_PRICE)) + "\n");
                            buffer.append("Duration: " + result.getString(result.getColumnIndex(
                                    db.DURATION)) + "\n\n");

                        }
                        //Show all data
                        util.showMessage("Client Appointments", buffer.toString(),
                                PriceListNormalActivity.this);
                    }
                }
        );
    }

    //Method to allow user to edit and update data in database
    public void UpdateData() {
        editPriceList.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (editService.getText().toString().trim().length() <= 0 ||
                                editTextPrice.getText().toString().trim().length() <= 0 ||
                                editDuration.getText().toString().trim().length() <= 0) {

                            util.showMessage("Missing Fields", "Fields \n\n Service \n Price " +
                                    "\n Duration" + " \n DOB \n\n" + "Required",
                                    PriceListNormalActivity.this);
                        } else {

                            boolean isUpdated = db.updatePriceData
                                    (editService.getText().toString(),
                                            editPriceList.getText().toString(),
                                            editDuration.getText().toString());
                            if (isUpdated) {
                                Toast.makeText(PriceListNormalActivity.this, "Data Updated",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(PriceListNormalActivity.this, "Data Not Updated",
                                        Toast.LENGTH_LONG).show();


                            }
                        }
                    }
                }
        );
    }

    public void lookupService() {
        searchPrice.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (editService.getText().toString().trim().length() <= 0) {

                            Toast.makeText(PriceListNormalActivity.this, "Service Field is Empty",
                                    Toast.LENGTH_LONG).show();

                        } else {

                            PriceList priceList = db.findPriceService(editService.getText()
                                    .toString());
                            if (priceList != null) {
                                editService.setText(String.valueOf("Service: " +
                                        priceList.getService()));
                                editTextPrice.setText(String.valueOf("Price: £" +
                                        priceList.getPrice()));
                                editDuration.setText(String.valueOf("Duration: " +
                                        priceList.getDuration() + " Minutes"));
                            } else {
                                Toast.makeText(PriceListNormalActivity.this, "No Match Found",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

}
