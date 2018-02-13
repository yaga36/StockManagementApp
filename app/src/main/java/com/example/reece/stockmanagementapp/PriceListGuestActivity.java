package com.example.reece.stockmanagementapp;

//Importing the required classes

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class PriceListGuestActivity extends AppCompatActivity {

    //Declaring all the variables for the class
    DatabaseHelper db;

    Utility util;

    Button searchPrice, viewPrice, homeButton;

    EditText editTextPrice, editService, editDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_list_guest);

        db = new DatabaseHelper(this);


        util = new Utility();

        //Assigning the edit text variables to the edit text fields in xml
        editTextPrice = (EditText) findViewById(R.id.priceEditText);
        editService = (EditText) findViewById(R.id.serviceEditText);
        editDuration = (EditText) findViewById(R.id.durationEditText);


        //Assigning the button variables to the buttons in the xml
        searchPrice = (Button) findViewById(R.id.searchPriceBtn);
        viewPrice = (Button) findViewById(R.id.viewPriceBtnGuest);
        homeButton = (Button) findViewById(R.id.returnHomeBtnGuest);

        ViewAll();
        lookupService();

        //Methods to tell the buttons what to do when clicked
        homeButton.setOnClickListener
                (new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(PriceListGuestActivity.this,
                                MainGuestActivity.class);
                        startActivity(intent);
                    }
                });
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
                                    PriceListGuestActivity.this);
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
                                PriceListGuestActivity.this);
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

                            Toast.makeText(PriceListGuestActivity.this, "Service Field is Empty",
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
                                Toast.makeText(PriceListGuestActivity.this, "No Match Found",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

}

