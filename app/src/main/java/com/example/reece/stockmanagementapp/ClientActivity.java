package com.example.reece.stockmanagementapp;

//Importing the required classes

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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

public class ClientActivity extends AppCompatActivity {

    //Declaring all the variables for the class
    DatabaseHelper db;

    Utility util;

    Button editClient, addClient, removeClient, searchClient, viewClient, homeButton, home;

    EditText editName, editSurname, editEmail, editDOB;

    //Declaring variables for date picker
    int year_x, month_x, day_x;

    static final int DIALOG_ID = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

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
        editClient = (Button) findViewById(R.id.editClientBtn);
        addClient = (Button) findViewById(R.id.addClientBtn);
        removeClient = (Button) findViewById(R.id.removeClientBtn);
        searchClient = (Button) findViewById(R.id.searchClientBtn);
        //homeButton = (Button) findViewById(R.id.returnHomeBtn);

        home = (Button) findViewById(R.id.returnHomeBtn);

        viewClient = (Button) findViewById(R.id.viewClientBtn);

        //Methods for buttons to perform actions
        AddData();
        ViewAll();
        UpdateData();
        DeleteData();
        lookupClient();
        showDialogOnDateEditTextSelect();


        //Method to return the user to the main menu when clicked
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClientActivity.this,
                        MainActivity.class);
                startActivity(intent);
            }
        });


    }

    //    //Method to add data into the database from the edit text fields
    public void AddData() {
        addClient.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (editName.getText().toString().trim().length() <= 0 ||
                                editSurname.getText().toString().trim().length() <= 0 ||
                                editEmail.getText().toString().trim().length() <= 0 ||
                                editDOB.getText().toString().trim().length() <= 0) {

                            util.showMessage("Missing Fields", "Fields Required \n\n Name " +
                                    "\n Surname \n eMail \n DOB \n", ClientActivity.this);

                        } else if ((util.parseLongFromDateTime(concatDate()) >
                                util.currentDateAsLong())) {

                            util.showMessage("Invalid Date",
                                    "Please enter a date in the past", ClientActivity.this);

                        } else if (!util.isEmailValid(editEmail.getText().toString())) {

                            util.showMessage("eMail Format", "Please enter a valid eMail address"
                                    , ClientActivity.this);

                        } else {
                            boolean isInserted = db.insertClientData(editName.getText().toString(),
                                    editSurname.getText().toString(),
                                    editEmail.getText().toString(),
                                    util.parseLongFromDate(editDOB.getText().toString()));

                            if (isInserted) {
                                Toast.makeText(ClientActivity.this, "Data Not Inserted",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(ClientActivity.this, "Data Inserted",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
        );
    }

    public String concatDate() {
        return editDOB.getText().toString() + " 00:00:00";
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
                            util.showMessage("Error", "No data to display", ClientActivity.this);
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
                        util.showMessage("Client Appointments", buffer.toString()
                                , ClientActivity.this);
                    }
                }
        );
    }

//    //Method to bring up an alert box that contains database data
//    public void util.showMessage();(String title, String Message) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setCancelable(true);
//        builder.setTitle(title);
//        builder.setMessage(Message);
//        builder.show();
//    }

    //Method to allow user to edit and update data in database
    public void UpdateData() {
        editClient.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (editName.getText().toString().trim().length() <= 0 ||
                                editSurname.getText().toString().trim().length() <= 0 ||
                                editEmail.getText().toString().trim().length() <= 0 ||
                                editDOB.getText().toString().trim().length() <= 0) {

                            util.showMessage("Missing Fields", "Fields \n\n Name \n Surname " +
                                    "\n eMail \n DOB \n\n" + "Required", ClientActivity.this);

                        } else if ((util.parseLongFromDateTime(concatDate()) >
                                util.currentDateAsLong())) {

                            util.showMessage("Invalid Date",
                                    "Please enter a date in the past", ClientActivity.this);

                        } else if (!util.isEmailValid(editEmail.getText().toString())) {

                            util.showMessage("eMail Format", "Please enter a valid eMail address"
                                    , ClientActivity.this);

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
                        boolean isUpdated = db.updateClientData
                                (editName.getText().toString(),
                                        editSurname.getText().toString(),
                                        editEmail.getText().toString(),
                                        util.parseLongFromDate(editDOB.getText().toString()));

                        if (isUpdated) {

                            Toast.makeText(ClientActivity.this, "Data Updated",
                                    Toast.LENGTH_LONG).show();

                        } else {

                            Toast.makeText(ClientActivity.this, "Data Not Updated",
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

    public void deleteMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false)
                .setTitle(title)
                .setMessage(Message)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Integer deletedRows = db.deleteClientData(editEmail.getText().toString());

                        if (deletedRows > 0) {

                            Toast.makeText(ClientActivity.this, "Data Deleted",
                                    Toast.LENGTH_LONG).show();

                        } else {

                            Toast.makeText(ClientActivity.this, "Data Not Deleted",
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
        removeClient.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (editEmail.getText().toString().trim().length() <= 0) {
                            util.showMessage("Fields Missing", "eMail field is required to " +
                                    "delete a client", ClientActivity.this);
                        } else if (!util.isEmailValid(editEmail.getText().toString())) {

                            util.showMessage("eMail Format", "Please enter a valid eMail address"
                                    , ClientActivity.this);

                        } else {
                            deleteMessage("Verify", "Are you sure you want to delete?");
                        }
                    }
                }
        );
    }


    public void showDialogOnDateEditTextSelect() {
        editDOB = (EditText) findViewById(R.id.dobEditText);

        editDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID) {
            return new DatePickerDialog(this, datePickerListener, year_x, month_x, day_x);
        }
        return null;
    }


    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            year_x = year;
            month_x = monthOfYear + 1;
            day_x = dayOfMonth;

            String editMonth = ("" + month_x);
            String editDay = ("" + day_x);

            if (month_x < 10) {
                editMonth = ("0" + month_x);
            }
            if (day_x < 10) {
                editDay = ("0" + day_x);
            }

            Toast.makeText(ClientActivity.this, day_x + "-" + month_x + "-" + year_x,
                    Toast.LENGTH_SHORT).show();

            editDOB.setText(String.valueOf(editDay + "-" + editMonth + "-" + year_x));
        }
    };

    public void lookupClient() {
        searchClient.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (editEmail.getText().toString().trim().length() <= 0) {

                            Toast.makeText(ClientActivity.this, "eMail Field is Empty",
                                    Toast.LENGTH_LONG).show();
                        } else if (!util.isEmailValid(editEmail.getText().toString())) {

                            util.showMessage("eMail Format", "Please enter a valid eMail address"
                                    , ClientActivity.this);

                        } else {
                            Client client = db.findClient(editEmail.getText().toString());
                            if (client != null) {
                                editName.setText(String.valueOf(client.getName()));
                                editSurname.setText(String.valueOf(client.getSurname()));
                                editEmail.setText(String.valueOf(client.getEmail()));
                                editDOB.setText(util.parseDateFromLong(
                                        Long.valueOf(client.getDOB())));
                            } else {
                                Toast.makeText(ClientActivity.this, "No Match Found",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }
}
