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

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class StockGuestActivity extends AppCompatActivity {

    DatabaseHelper db;

    Button searchStock, viewStock, scanStock, homeButton;

    EditText editID, editBarcode, editProductName, editType, editQuantity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_guest);
        db = new DatabaseHelper(this);


        viewStock = (Button) findViewById(R.id.viewStockBtnGuest);
        searchStock = (Button) findViewById(R.id.searchStockBtnGuest);
        scanStock = (Button) findViewById(R.id.scanStockBtnGuest);
        homeButton = (Button) findViewById(R.id.returnHomeBtnGuest);

        editID = (EditText) findViewById(R.id.idStockEditText);
        editBarcode = (EditText) findViewById(R.id.barcodeEditText);
        editProductName = (EditText) findViewById(R.id.productNameEditText);
        editType = (EditText) findViewById(R.id.typeEditText);
        editQuantity = (EditText) findViewById(R.id.quantityEditText);

        lookupProduct();
        ViewAll();

        scanStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(StockGuestActivity.this);
                scanIntegrator.initiateScan();
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StockGuestActivity.this, MainGuestActivity.class);
                startActivity(intent);
            }
        });

    }

    //Method to view all data that is stored within the database
    public void ViewAll() {
        viewStock.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor result = db.getAllStockData();
                        if (result.getCount() == 0) {
                            //Show Message
                            showMessage("Stock List", "No data to display");
                            return;
                        }
                        StringBuilder buffer = new StringBuilder();
                        while (result.moveToNext()) {
                            buffer.append("ID :" + result.getString(result.
                                    getColumnIndex(db.STOCK_ID)) + "\n");
                            buffer.append("Barcode :" + result.getString(result.
                                    getColumnIndex(db.STOCK_BARCODE)) + "\n");
                            buffer.append("Product Name :" + result.getString(result.
                                    getColumnIndex(db.STOCK_NAME)) + "\n");
                            buffer.append("Type :" + result.getString(result.
                                    getColumnIndex(db.STOCK_TYPE)) + "\n");
                            buffer.append("Quantity :" + result.getString(result.
                                    getColumnIndex(db.STOCK_QUANTITY)) + "\n\n");
                        }
                        //Show all data
                        showMessage("Stock List", buffer.toString());
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


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode,
                intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            if (scanContent != null) {
                editBarcode.setText(scanContent);
                Toast toast = Toast.makeText(StockGuestActivity.this,
                        "Item Scanned!", Toast.LENGTH_SHORT);
                toast.show();
                lookupProduct();
            } else {
                Intent cancelIntent = new Intent(this, StockGuestActivity.class);
                startActivity(cancelIntent);
                Toast toast = Toast.makeText(StockGuestActivity.this,
                        "Scanner closed,no scan data was received!", Toast.LENGTH_LONG);
                toast.show();
            }
        } else {

            Toast toast = Toast.makeText(StockGuestActivity.this,
                    "No scan data received!", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void lookupProduct() {
        searchStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editBarcode.getText().toString().trim().length() <= 0) {

                    Toast.makeText(StockGuestActivity.this, "Barcode Field Empty",
                            Toast.LENGTH_LONG).show();

                } else {
                    Stock product = db.findProduct(editBarcode.getText().toString());
                    if (product != null) {
                        editID.setText(String.valueOf(product.getID()));
                        editBarcode.setText(String.valueOf(product.getBarcode()));
                        editProductName.setText(String.valueOf(product.getProductName()));
                        editType.setText(String.valueOf(product.getProductType()));
                        editQuantity.setText(String.valueOf(product.getQuantity()));
                    } else {
                        Toast.makeText(StockGuestActivity.this, "No Match Found",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
