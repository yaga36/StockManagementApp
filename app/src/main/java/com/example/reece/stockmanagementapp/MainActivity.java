package com.example.reece.stockmanagementapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {


    Button scan, stock, client, appointment, price, register, logout;

    TextView title, welcome, message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scan = (Button) findViewById(R.id.scanBtn);
        stock = (Button) findViewById(R.id.stockBtn);
        client = (Button) findViewById(R.id.clientBtn);
        appointment = (Button) findViewById(R.id.appointmentBtn);
        price = (Button) findViewById(R.id.priceBtn);
        register = (Button) findViewById(R.id.registerBtn);
        logout = (Button) findViewById(R.id.logOutBtn);

        title = (TextView) findViewById(R.id.titleText);
        welcome = (TextView) findViewById(R.id.textWelcome);
        message = (TextView) findViewById(R.id.textMessage);

        Typeface font = Typeface.createFromAsset(getAssets(),
                "fonts/limerick-serial-heavy-regular.ttf");
        title.setTypeface(font);



//        scan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
//                startActivity(intent);
//            }
//        });

        stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StockActivity.class);
                startActivity(intent);
            }
        });
        client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ClientActivity.class);
                startActivity(intent);

            }
        });

        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AppointmentActivity.class);
                startActivity(intent);

            }
        });

        price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PriceListActivity.class);
                startActivity(intent);

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterSQLiteActivity.class);
                startActivity(intent);

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, LoginSQLiteActivity.class);
                startActivity(intent);

            }
        });
    }

}
