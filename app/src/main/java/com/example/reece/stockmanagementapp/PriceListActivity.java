package com.example.reece.stockmanagementapp;

//Importing the required classes

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class PriceListActivity extends AppCompatActivity {

    //Declaring all the variables for the class
    DatabaseHelper db;

    Button editPriceList, addPrice, removePrice, searchPrice, viewPrice, homeButton;

    EditText editTextPrice, editService, editDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_list);

        db = new DatabaseHelper(this);

        //Assigning the edit text variables to the edit text fields in xml
        editTextPrice = (EditText) findViewById(R.id.priceEditText);
        editService = (EditText) findViewById(R.id.serviceEditText);
        editDuration = (EditText) findViewById(R.id.durationEditText);


        //Assigning the button variables to the buttons in the xml
        editPriceList = (Button) findViewById(R.id.editPriceBtn);
        addPrice = (Button) findViewById(R.id.addPriceBtn);
        if (addPrice == null) {
            Log.w("", "EditText is null");
        }
        removePrice = (Button) findViewById(R.id.removePriceBtn);
        searchPrice = (Button) findViewById(R.id.searchPriceBtn);
        viewPrice = (Button) findViewById(R.id.viewPriceBtn);
        homeButton = (Button) findViewById(R.id.returnHomeBtn);


        AddData();
        ViewAll();
        UpdateData();
        DeleteData();
        lookupService();


        //Methods to tell the buttons what to do when clicked
        homeButton.setOnClickListener
                (new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(PriceListActivity.this, MainActivity.class);
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

                            showMessage("Missing Fields", "Fields \n\n Service \n Price \n Duration"
                                    + " \n DOB \n\n" + "Required");
                        } else {
                            boolean isInserted = db.insertPriceData
                                    (editService.getText().toString(),
                                            editTextPrice.getText().toString(),
                                            editDuration.getText().toString());
                            if (isInserted)
                                Toast.makeText(PriceListActivity.this, "Data Not Inserted",
                                        Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(PriceListActivity.this, "Data Inserted",
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
                            showMessage("Error", "No data to display");
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
                        showMessage("Client Appointments", buffer.toString());
                    }
                }
        );
    }

    //Method to bring up an alert box that contains database data
    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
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

                            showMessage("Missing Fields", "Fields \n\n Service \n Price \n Duration"
                                    + " \n DOB \n\n" + "Required");
                        } else {

                            updateMessage("Verify", "Are you sure you wish to make these changes?");

                        }
                    }
                }
        );
    }


    public void updateMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false)
                .setTitle(title)
                .setMessage(Message)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean isUpdated = db.updatePriceData
                                (editService.getText().toString(),
                                        editTextPrice.getText().toString(),
                                        editDuration.getText().toString());
                        if (isUpdated) {
                            Toast.makeText(PriceListActivity.this, "Data Updated",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(PriceListActivity.this, "Data Not Updated",
                                    Toast.LENGTH_LONG).show();


                        }
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.show();
    }

    public void DeleteData() {
        removePrice.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (editService.getText().toString().trim().length() <= 0) {
                            showMessage("Fields Missing", "Service field is required to " +
                                    "delete a service");
                        } else {
                            deleteMessage("Verify", "Are you sure you want to delete?");
                        }
                    }
                }
        );
    }

    public void deleteMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false)
                .setTitle(title)
                .setMessage(Message)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Integer deletedRows = db.deletePriceData(editService.getText().toString());

                        if (deletedRows > 0) {

                            Toast.makeText(PriceListActivity.this, "Data Deleted",
                                    Toast.LENGTH_LONG).show();

                        } else {

                            Toast.makeText(PriceListActivity.this, "Data Not Deleted",
                                    Toast.LENGTH_LONG).show();

                        }
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }


    public void lookupService() {
        searchPrice.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (editService.getText().toString().trim().length() <= 0) {

                            Toast.makeText(PriceListActivity.this, "Service Field is Empty",
                                    Toast.LENGTH_LONG).show();

                        } else {

                            PriceList priceList = db.findPriceService(editService.getText()
                                    .toString());
                            if (priceList != null) {
                                editService.setText(String.valueOf(priceList.getService()));
                                editTextPrice.setText(String.valueOf(priceList.getPrice()));
                                editDuration.setText(String.valueOf(priceList.getDuration()));
                            } else {
                                Toast.makeText(PriceListActivity.this, "No Match Found",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }
}

