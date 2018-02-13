package com.example.reece.stockmanagementapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class RegisterSQLiteActivity extends AppCompatActivity {

    //Declaring all the variables for the class
    DatabaseHelper db;

    Button editUser, addUser, removeUser, searchUser, viewUser, homeButton;

    EditText editName, editUsername, editPassword;

    Spinner spinnerStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_sqlite);

        db = new DatabaseHelper(this);

        editName = (EditText) findViewById(R.id.nameRegisterEditText);
        editUsername = (EditText) findViewById(R.id.usernameRegisterEditText);
        editPassword = (EditText) findViewById(R.id.passwordRegisterEditText);


        //Assigning the button variables to the buttons in the xml
        editUser = (Button) findViewById(R.id.editRegisterBtn);
        addUser = (Button) findViewById(R.id.addRegisterBtn);
        removeUser = (Button) findViewById(R.id.removeRegisterBtn);
        searchUser = (Button) findViewById(R.id.searchRegisterBtn);
        homeButton = (Button) findViewById(R.id.returnHomeBtn);
        viewUser = (Button) findViewById(R.id.viewRegisterBtn);

        spinnerStatus = (Spinner) findViewById(R.id.statusSpinner);

       // spinnerStatus.setOnItemSelectedListener(this);

        List<String> status = db.getAllStatus();

        AddData();
        ViewAll();
        UpdateData();
        DeleteData();
        lookupUser();

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, status);


        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerStatus.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, status) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView) v.findViewById(android.R.id.text1)).setText("");
                    ((TextView) v.findViewById(android.R.id.text2)).setHint(getItem(getCount()));
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount();
            }
        };
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAdapter.add("Status:");
        spinnerStatus.setAdapter(spinnerAdapter);
        spinnerStatus.setSelection(spinnerAdapter.getCount() - 1);
        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Methods to tell the buttons what to do when clicked
        homeButton.setOnClickListener
                (new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(RegisterSQLiteActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
    }

    //    //Method to add data into the database from the edit text fields
    public void AddData() {
        addUser.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (editName.getText().toString().trim().length() <= 0 ||
                                editUser.getText().toString().trim().length() <= 0 ||
                                editPassword.getText().toString().trim().length() <= 0 ||
                                spinnerStatus.getSelectedItem().toString().equals("Status:")) {

                            showMessage("Missing Fields", "Fields \n\n Service \n Price \n Duration"
                                    + " \n DOB \n\n" + "Required");

                        } else {
                            boolean isInserted = db.insertRegisterData(editName.getText().toString()
                                    , editUsername.getText().toString(),
                                    editPassword.getText().toString(),
                                    spinnerStatus.getSelectedItem().toString());
                            if (isInserted)
                                Toast.makeText(RegisterSQLiteActivity.this, "Data Not Inserted",
                                        Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(RegisterSQLiteActivity.this, "Data Inserted",
                                        Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    //Method to view all data that is stored within the database
    public void ViewAll() {
        viewUser.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor result = db.getAllRegisterData();
                        if (result.getCount() == 0) {
                            //Show Message
                            showMessage("Error", "No data to display");
                            return;
                        }
                        StringBuilder buffer = new StringBuilder();
                        while (result.moveToNext()) {
                            buffer.append("ID: " + result.getString(result.getColumnIndex(
                                    db.LOGIN_SQLITE_ID)) + "\n");
                            buffer.append("Name: " + result.getString(result.getColumnIndex(
                                    db.LOGIN_SQLITE_NAME)) + "\n");
                            buffer.append("Username: " + result.getString(result.getColumnIndex(
                                    db.LOGIN_SQLITE_USERNAME)) + "\n");
                            buffer.append("Password: " + result.getString(result.getColumnIndex(
                                    db.LOGIN_SQLITE_PASSWORD)) + "\n");
                            buffer.append("Status: " + result.getString(result.getColumnIndex(
                                    db.LOGIN_SQLITE_STATUS)) + "\n\n");

                        }
                        //Show all data
                        showMessage("Registered Users", buffer.toString());
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
        editUser.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (editName.getText().toString().trim().length() <= 0 ||
                                editUsername.getText().toString().trim().length() <= 0 ||
                                editPassword.getText().toString().trim().length() <= 0 ||
                                spinnerStatus.getSelectedItem().toString().equals("Status:")) {

                            showMessage("Missing Fields", "Fields \n\n Name \n Username \n Password"
                                    + " \n Status \n\n" + "Required");
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
                        boolean isUpdated = db.updateRegisterData
                                (editName.getText().toString(),
                                        editUsername.getText().toString(),
                                        editPassword.getText().toString(),
                                        spinnerStatus.getSelectedItem().toString());
                        if (isUpdated) {
                            Toast.makeText(RegisterSQLiteActivity.this, "Data Updated",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(RegisterSQLiteActivity.this, "Data Not Updated",
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
        removeUser.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (editUsername.getText().toString().trim().length() <= 0) {
                            showMessage("Fields Missing", "Username field is required to " +
                                    "delete a user");
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

                        Integer deletedRows = db.deleteRegisterData(editUsername.getText().toString());

                        if (deletedRows > 0) {

                            Toast.makeText(RegisterSQLiteActivity.this, "Data Deleted",
                                    Toast.LENGTH_LONG).show();

                        } else {

                            Toast.makeText(RegisterSQLiteActivity.this, "Data Not Deleted",
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


    private int getIndex(Spinner spinner, String myString) {
        int index = 0;
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public void lookupUser() {
        searchUser.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (editUsername.getText().toString().trim().length() <= 0) {

                            Toast.makeText(RegisterSQLiteActivity.this, "Username Field is Empty",
                                    Toast.LENGTH_LONG).show();

                        } else {

                            Register user = db.findUser(editUsername.getText().toString());
                            if (user != null) {
                                editName.setText(String.valueOf(user.getName()));
                                editUsername.setText(String.valueOf(user.getUsername()));
                                editPassword.setText(String.valueOf(user.getPassword()));
                                spinnerStatus.setSelection(getIndex(spinnerStatus,
                                        String.valueOf(user.getUser())));
                            } else {
                                Toast.makeText(RegisterSQLiteActivity.this,
                                        "No Match Found", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }
}
