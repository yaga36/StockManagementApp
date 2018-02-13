package com.example.reece.stockmanagementapp;

//Importing the required classes

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import android.app.DatePickerDialog;
import java.util.Calendar;

public class ClientGuestActivity extends AppCompatActivity {

    //Declaring all the variables for the class
    DatabaseHelper db;

    Utility util;

    Button searchClient, viewClient, homeButton;

    EditText editName, editSurname, editEmail, editDOB;

    //Declaring variablies for date picker
    int year_x, month_x, day_x;

    static final int DIALOG_ID = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_guest);

        db = new DatabaseHelper(this);

        util = new Utility();

        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);



        //Assigning the edit text variables to the edit text fields in xml
        editName = (EditText) findViewById(R.id.nameEditText);
        editSurname = (EditText) findViewById(R.id.surnameEditText);
        editEmail = (EditText) findViewById(R.id.emailEditText);
        editDOB = (EditText) findViewById(R.id.dobEditText);


        //Assigning the button variables to the buttons in the xml
        searchClient = (Button) findViewById(R.id.searchClientBtnGuest);
        homeButton = (Button) findViewById(R.id.returnHomeBtnGuest);
        viewClient = (Button) findViewById(R.id.viewClientBtnGuest);

        //Methods for buttons to perform actions

        ViewAll();
        lookupClient();
        showDialogOnDateEditTextSelect();


        //Methods to tell the buttons what to do when clicked
        homeButton.setOnClickListener
                (new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ClientGuestActivity.this, MainGuestActivity.class);
                        startActivity(intent);
                    }
                });
    }



    //Method to view all data that is stored within the database
    public void ViewAll() {
        viewClient.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor result = db.getAllClientData();
                        if (result.getCount() == 0) {

                            //Show Message
                            util.showMessage("Error", "No data to display",
                                    ClientGuestActivity.this);
                            return;

                        }

                        StringBuilder buffer = new StringBuilder();
                        while (result.moveToNext()) {

                            buffer.append("ID: " + result.getString(result.
                                    getColumnIndex(db.CLIENT_ID)) + "\n");
                            buffer.append("Name: " + result.getString(result.
                                    getColumnIndex(db.CLIENT_NAME)) + "\n");
                            buffer.append("Surname: " + result.getString(result.
                                    getColumnIndex(db.CLIENT_SURNAME)) + "\n");
                            buffer.append("Email: " + result.getString(result.
                                    getColumnIndex(db.CLIENT_EMAIL)) + "\n");
                            buffer.append("Date of Birth: " + (util.parseDateFromLong(
                                    result.getLong(result.getColumnIndex(db.CLIENT_DOB)))
                                    + "\n\n"));

                        }

                        //Show all data
                        util.showMessage("Client Appointments", buffer.toString(),
                                ClientGuestActivity.this);
                    }
                }
        );
    }

    public void showDialogOnDateEditTextSelect(){
        editDOB = (EditText)findViewById(R.id.dobEditText);

        editDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id){
        if(id == DIALOG_ID){
            return new DatePickerDialog(this, datePickerListener, year_x, month_x, day_x);
        }
        return null;
    }


    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            year_x = year;
            month_x = monthOfYear +1;
            day_x = dayOfMonth;
            Toast.makeText(ClientGuestActivity.this, day_x + "/" + month_x + "/" + year_x, Toast.LENGTH_SHORT).show();
            editDOB.setText(String.valueOf(day_x + "/" + month_x + "/" + year_x));
        }
    };


    public void lookupClient() {
        searchClient.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (editEmail.getText().toString().trim().length() <= 0) {

                            Toast.makeText(ClientGuestActivity.this, "eMail Field is Empty",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Client client = db.findClient(editEmail.getText().toString());
                            if (client != null) {
                                editName.setText(String.valueOf(client.getName()));
                                editSurname.setText(String.valueOf(client.getSurname()));
                                editEmail.setText(String.valueOf(client.getEmail()));
                                editDOB.setText(util.parseDateFromLong(
                                        Long.valueOf(client.getDOB())));
                            } else {
                                Toast.makeText(ClientGuestActivity.this, "No Match Found",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }
}
