package com.example.reece.stockmanagementapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainGuestActivity extends AppCompatActivity {


    Button scan, stock, client, appointment, price, logout;

    TextView title, welcome, message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_guest);

        scan = (Button) findViewById(R.id.scanBtnGuest);
        stock = (Button) findViewById(R.id.stockBtnGuest);
        client = (Button) findViewById(R.id.clientBtnGuest);
        appointment = (Button) findViewById(R.id.appointmentBtnGuest);
        price = (Button) findViewById(R.id.priceBtnGuest);
        logout = (Button) findViewById(R.id.logOutBtn);

        title = (TextView) findViewById(R.id.titleText);
        welcome = (TextView) findViewById(R.id.textWelcome);
        message = (TextView) findViewById(R.id.textMessage);

        Typeface font = Typeface.createFromAsset(getAssets(),
                "fonts/limerick-serial-heavy-regular.ttf");
        title.setTypeface(font);


        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainGuestActivity.this, ScanActivity.class);
                startActivity(intent);
            }
        });
        stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainGuestActivity.this, StockGuestActivity.class);
                startActivity(intent);
            }
        });
        client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainGuestActivity.this, ClientGuestActivity.class);
                startActivity(intent);

            }
        });

        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainGuestActivity.this, AppointmentGuestActivity.class);
                startActivity(intent);

            }
        });

        price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainGuestActivity.this, PriceListGuestActivity.class);
                startActivity(intent);

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainGuestActivity.this, LoginSQLiteActivity.class);
                startActivity(intent);

            }
        });
    }

}
