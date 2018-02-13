package com.example.reece.stockmanagementapp;

import com.example.reece.stockmanagementapp.DatabaseHelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanActivity extends AppCompatActivity {

    Button scanAdd, scanRemove, scanView, scanUse, homeButton;
    TextView formatTxt, contentTxt;
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        scanAdd = (Button) findViewById(R.id.addProductBtn);
        scanUse = (Button) findViewById(R.id.useScanBtn);
        scanView = (Button) findViewById(R.id.viewScanBtn);
        scanRemove = (Button) findViewById(R.id.removeProductBtn);
        homeButton = (Button) findViewById(R.id.returnHomeBtn);
        formatTxt = (TextView) findViewById(R.id.scan_format);
        contentTxt = (TextView) findViewById(R.id.scan_content);
        myDB = new DatabaseHelper(this);

        //addScan();




        scanUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IntentIntegrator(ScanActivity.this).initiateScan();
            }
        });

        scanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScanActivity.this, ScanActivity.class);
                startActivity(intent);
            }
        });

        scanRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScanActivity.this, ScanActivity.class);
                startActivity(intent);
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScanActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode,
                intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            formatTxt.setText(scanFormat);
            contentTxt.setText(scanContent);

            Toast toast = Toast.makeText(ScanActivity.this,
                    "Item Scanned!", Toast.LENGTH_LONG);
            toast.show();

        } else {

            Toast toast = Toast.makeText(ScanActivity.this,
                    "No scan data received!", Toast.LENGTH_LONG);
            toast.show();
        }
    }

//    public void addScan() {
//        scanAdd.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        boolean isInserted = myDB.insertScanData(contentTxt.getText().toString());
//                        if (isInserted == false)
//                            Toast.makeText(ScanActivity.this, "Data Inserted",
//                                    Toast.LENGTH_LONG).show();
//                        else
//                            Toast.makeText(ScanActivity.this, "Data Not Inserted",
//                                    Toast.LENGTH_LONG).show();
//                    }
//                }
//        );
//    }

}
