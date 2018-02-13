package com.example.reece.stockmanagementapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StockTestingActivity extends AppCompatActivity {

    DatabaseHelper myDb;

    Button editStock, addStock, removeStock, searchStock, viewStock, homeButton;

    EditText editID, editBarcode, editProductName, editType, editQuantity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        myDb = new DatabaseHelper(this);

        editStock = (Button) findViewById(R.id.editStockBtn);
        addStock = (Button) findViewById(R.id.addStockBtn);
        removeStock = (Button) findViewById(R.id.removeStockBtn);
        viewStock = (Button) findViewById(R.id.viewStockBtn);
        searchStock = (Button) findViewById(R.id.searchStockBtn);
        homeButton = (Button) findViewById(R.id.returnHomeBtn);

        editID = (EditText) findViewById(R.id.idStockEditText);
        editBarcode = (EditText) findViewById(R.id.barcodeEditText);
        editProductName = (EditText) findViewById(R.id.productNameEditText);
        editType = (EditText) findViewById(R.id.typeEditText);
        editQuantity = (EditText) findViewById(R.id.quantityEditText);

        AddData();
        ViewAll();
        UpdateData();
        DeleteData();


        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StockTestingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    //Method to add data into the database from the edit text fields
    public void AddData() {
        addStock.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertStockData(editBarcode.getText().toString(),
                                editProductName.getText().toString(),
                                editType.getText().toString(),
                                editQuantity.getText().toString());
                        if (isInserted == true)
                            Toast.makeText(StockTestingActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(StockTestingActivity.this, "Data Not Inserted", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    //Method to view all data that is stored within the database
    public void ViewAll() {
        viewStock.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor result = myDb.getAllStockData();
                        if (result.getCount() == 0) {
                            //Show Message
                            showMessage("Stock List", "No data to display");
                            return;
                        }
                        StringBuilder buffer = new StringBuilder();
                        while (result.moveToNext()) {
                            buffer.append("ID :" + result.getString(0) + "\n");
                            buffer.append("Barcode :" + result.getString(1) + "\n");
                            buffer.append("Product Name :" + result.getString(2) + "\n");
                            buffer.append("Type :" + result.getString(3) + "\n");
                            buffer.append("Quantity :" + result.getString(4) + "\n\n");

                        }
                        //Show all data
                        showMessage("Stock List", buffer.toString());
                    }
                }
        );
    }

    public void lookupProduct (View view){
        DatabaseHelper db = new DatabaseHelper(this);

        Stock product = db.findProduct(editID.getText().toString());
        if (product != null) {
            editID.setText(String.valueOf(product.getID()));
        } else {
            editID.setText("No Match Found");
        }
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
        editStock.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdated = myDb.updateStockData(editBarcode.getText().toString(),
                                editProductName.getText().toString(),
                                editType.getText().toString(),
                                editQuantity.getText().toString());
                        if (isUpdated == true)
                            Toast.makeText(StockTestingActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(StockTestingActivity.this, "Data Not Updated", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void DeleteData() {
        removeStock.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteStockData(editID.getText().toString());
                        if (deletedRows > 0)
                            Toast.makeText(StockTestingActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(StockTestingActivity.this, "Data Not Deleted", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

}
