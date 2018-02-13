package com.example.reece.stockmanagementapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginSQLiteActivity extends AppCompatActivity {

    DatabaseHelper db = new DatabaseHelper(this);

    EditText username, password;

    Button loginBtn, guestLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sqlite);

        username = (EditText) findViewById(R.id.usernameEditText);
        password = (EditText) findViewById(R.id.passwordLogEditText);

        loginBtn = (Button) findViewById(R.id.loginBtn);
        guestLoginBtn = (Button) findViewById(R.id.guestloginBtn);


        loginBtn.setOnClickListener
                (new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String usernameEntered = username.getText().toString();
                        String passwordEntered = password.getText().toString();

                        String password = db.searchPass(usernameEntered);
                        String status = db.returnStatus(usernameEntered);
                        if (passwordEntered.equals(password)) {
                            Toast message = Toast.makeText(LoginSQLiteActivity.this,
                                    "Username and Password match!" , Toast.LENGTH_SHORT);
                            message.show();
                            if (status.equals("admin")){
                                Intent intent = new Intent(LoginSQLiteActivity.this,
                                        MainActivity.class);
                                startActivity(intent);
                            } else if (status.equals("normal")){
                                Intent intent = new Intent(LoginSQLiteActivity.this,
                                        MainNormalActivity.class);
                                startActivity(intent);
                            }
                        } else {
                            Toast message = Toast.makeText(LoginSQLiteActivity.this,
                                    "Username and Password do not exist!" , Toast.LENGTH_SHORT);
                            message.show();
                        }
                    }
                });

        guestLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginSQLiteActivity.this, MainGuestActivity.class);
                startActivity(intent);
            }
        });
    }



}
