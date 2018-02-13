package com.example.reece.stockmanagementapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class StockNormalActivity extends AppCompatActivity {

    DatabaseHelper db;

    Utility util;

    NumberPicker np;

    Button editStock, addStock, searchStock, viewStock, scanStock, homeButton,
            decreaseQuantity, increaseQuantity;

    EditText editID, editBarcode, editProductName, editType, editQuantity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_normal);

        db = new DatabaseHelper(this);

        util = new Utility();

        editStock = (Button) findViewById(R.id.editStockBtnNormal);
        addStock = (Button) findViewById(R.id.addStockBtnNormal);
        viewStock = (Button) findViewById(R.id.viewStockBtnNormal);
        searchStock = (Button) findViewById(R.id.searchStockBtnNormal);
        scanStock = (Button) findViewById(R.id.scanStockBtnNormal);
        homeButton = (Button) findViewById(R.id.returnHomeBtnNormal);
        decreaseQuantity = (Button) findViewById(R.id.decreaseQuantity);
        increaseQuantity = (Button) findViewById(R.id.increaseQuantity);

        editID = (EditText) findViewById(R.id.idStockEditText);
        editBarcode = (EditText) findViewById(R.id.barcodeEditText);
        editProductName = (EditText) findViewById(R.id.productNameEditText);
        editType = (EditText) findViewById(R.id.typeEditText);
        editQuantity = (EditText) findViewById(R.id.quantityEditText);


        AddData();
        ViewAll();
        UpdateData();
        lookupProduct();
        increaseNumberPicker();
        decreaseNumberPicker();

        scanStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(StockNormalActivity.this);
                scanIntegrator.initiateScan();
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StockNormalActivity.this, MainNormalActivity.class);
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
                        if (editBarcode.getText().toString().trim().length() <= 0 ||
                                editProductName.getText().toString().trim().length() <= 0 ||
                                editType.getText().toString().trim().length() <= 0 ||
                                editQuantity.getText().toString().trim().length() <= 0) {

                            util.showMessage("Fields Missing", "Please fill in all fields",
                                    StockNormalActivity.this);

                        } else {
                            boolean isInserted = db.insertStockData(editBarcode.getText().toString()
                                    , editProductName.getText().toString(),
                                    editType.getText().toString(),
                                    editQuantity.getText().toString());
                            if (isInserted)
                                Toast.makeText(StockNormalActivity.this, "Data Inserted",
                                        Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(StockNormalActivity.this, "Data Not Inserted",
                                        Toast.LENGTH_LONG).show();
                        }
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
                        Cursor result = db.getAllStockData();
                        if (result.getCount() == 0) {
                            //Show Message
                            util.showMessage("Stock List", "No data to display",
                                    StockNormalActivity.this);
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
                        util.showMessage("Stock List", buffer.toString(), StockNormalActivity.this);
                    }
                }
        );
    }


    public void lookupProduct() {
        searchStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editBarcode.getText().toString().trim().length() <= 0) {

                    Toast.makeText(StockNormalActivity.this, "Barcode Field Empty",
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
                        Toast.makeText(StockNormalActivity.this, "No Match Found",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    //Method to allow user to edit and update data in database
    public void UpdateData() {
        editStock.setOnClickListener
                (new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         if (editBarcode.getText().toString().trim().length() <= 0 ||
                                 editProductName.getText().toString().trim().length() <= 0 ||
                                 editType.getText().toString().trim().length() <= 0 ||
                                 editQuantity.getText().toString().trim().length() <= 0) {


                             util.showMessage("Fields Missing", "All fields required. \n \n" +
                                     "Scan or Search First", StockNormalActivity.this);

                         } else {
                             boolean isUpdated = db.updateStockData(
                                     editBarcode.getText().toString(),
                                     editProductName.getText().toString(),
                                     editType.getText().toString(),
                                     editQuantity.getText().toString());
                             if (isUpdated) {
                                 Toast.makeText(StockNormalActivity.this, "Data Updated",
                                         Toast.LENGTH_LONG).show();
                             } else {
                                 Toast.makeText(StockNormalActivity.this, "Data Not Updated",
                                         Toast.LENGTH_LONG).show();
                             }
                         }
                     }
                 }
                );
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode,
                intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            if (scanContent != null) {
                editBarcode.setText(scanContent);
                Toast toast = Toast.makeText(StockNormalActivity.this,
                        "Item Scanned!", Toast.LENGTH_SHORT);
                toast.show();
                lookupProduct();
            } else {
                Intent cancelIntent = new Intent(this, StockNormalActivity.class);
                startActivity(cancelIntent);
                Toast toast = Toast.makeText(StockNormalActivity.this,
                        "Scanner closed,no scan data was received!", Toast.LENGTH_LONG);
                toast.show();
            }
        } else {

            Toast toast = Toast.makeText(StockNormalActivity.this,
                    "No scan data received!", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void decreaseNumberPicker() {

        decreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editBarcode.getText().toString().trim().length() <= 0) {

                    util.showMessage("Fields Missing", "Barcode Field Required",
                            StockNormalActivity.this);

                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(StockNormalActivity.this);

                    np = new NumberPicker(StockNormalActivity.this);
                    np.setMinValue(1);
                    np.setMaxValue(100);
                    np.setWrapSelectorWheel(true);

                    builder.setView(np);

                    builder.setTitle("Decrease Stock Quantity")
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    int pickerValue = np.getValue();
                                    String barcode = editBarcode.getText().toString();
                                    int currentQuantityInt = db.getQuantity(barcode);
                                    int resultQuantity = (currentQuantityInt - pickerValue);

                                    if (resultQuantity < 0 && resultQuantity > -101) {
                                        Toast toast = Toast.makeText(StockNormalActivity.this,
                                                "Can't have negative products", Toast.LENGTH_SHORT);
                                        toast.show();
                                    } else if (resultQuantity >= 255) {
                                        util.showMessage("Warning", "Barcode doesn't exist \n" +
                                                "Please Scan/Search First",
                                                StockNormalActivity.this);
                                    } else {
                                        db.updateQuantity(barcode, resultQuantity);
                                        int decreaseResult = db.getQuantity(barcode);
                                        editQuantity.setText(String.valueOf(decreaseResult));
                                        Toast toast = Toast.makeText(StockNormalActivity.this,
                                                "Product Updated", Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                                }
                            }).setNegativeButton(R.string.cancel, new DialogInterface.
                            OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d("CANCEL", "This is Cancel Message");
                        }
                    });

                    builder.show();

                }
            }
        });
    }

    public void increaseNumberPicker() {

        increaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editBarcode.getText().toString().trim().length() <= 0) {

                    util.showMessage("Fields Missing", "Barcode Field Required",
                            StockNormalActivity.this);

                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(StockNormalActivity.this);

                    np = new NumberPicker(StockNormalActivity.this);
                    np.setMinValue(1);
                    np.setMaxValue(100);
                    np.setWrapSelectorWheel(true);

                    builder.setView(np);

                    builder.setTitle("Increase Stock Quantity")
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    int pickerValue = np.getValue();
                                    String barcode = editBarcode.getText().toString();
                                    int currentQuantityInt = db.getQuantity(barcode);
                                    int resultQuantity = (currentQuantityInt + pickerValue);

                                    if (resultQuantity > 100 && resultQuantity < 201) {
                                        Toast toast = Toast.makeText(StockNormalActivity.this,
                                                "Can't hold more than 100 products",
                                                Toast.LENGTH_SHORT);
                                        toast.show();

                                    } else if (resultQuantity >= 255) {
                                        util.showMessage("Warning", "Barcode doesn't exist \n" +
                                                "Please Scan/Search First",
                                                StockNormalActivity.this);
                                    } else {
                                        db.updateQuantity(barcode, resultQuantity);
                                        int increaseResult = db.getQuantity(barcode);
                                        editQuantity.setText(String.valueOf(increaseResult));
                                        Toast toast = Toast.makeText(StockNormalActivity.this,
                                                "Product Updated", Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                                }
                            }).setNegativeButton(R.string.cancel, new DialogInterface.
                            OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d("CANCEL", "This is Cancel Message");
                        }
                    });

                    builder.show();

                }
            }
        });
    }
}
