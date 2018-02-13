package com.example.reece.stockmanagementapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class AppointmentNormalActivity extends AppCompatActivity {

    DatabaseHelper db;

    Utility util;

    Spinner serviceSpinner;

    ArrayList<String> spinnerList;

    ArrayAdapter<String> spinnerAdapter;

    Button editApp, addApp, removeApp, searchApp, viewApp, homeButton;

    EditText editID, editName, editSurname, editEmail, editDate, editTime, editService;

    TextView tvName;

    //Declaring variables for date picker
    int year_x, month_x, day_x, hour_x, minute_x;

    static final int DIALOG_ID = 0;
    static final int DIALOG_ID_TIME = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_normal);

        db = new DatabaseHelper(this);

        util = new Utility();

        serviceSpinner = (Spinner) findViewById(R.id.serviceSpinner);

        spinnerList = db.getAllServices();

        spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, spinnerList) {
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
        spinnerAdapter.add("Service:");
        serviceSpinner.setAdapter(spinnerAdapter);
        serviceSpinner.setSelection(spinnerAdapter.getCount() - 1);
        serviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);
        hour_x = cal.get(Calendar.HOUR_OF_DAY);
        minute_x = cal.get(Calendar.MINUTE);


        //Assigning the edit text variables to the edit text fields in xml
        editID = (EditText) findViewById(R.id.idAppEditText);
        editName = (EditText) findViewById(R.id.nameAppEditText);
        editSurname = (EditText) findViewById(R.id.surnameAppEditText);
        editEmail = (EditText) findViewById(R.id.emailAppEditText);
        editDate = (EditText) findViewById(R.id.dateEditText);
        editTime = (EditText) findViewById(R.id.timeEditText);
        editService = (EditText) findViewById(R.id.serviceAppEditText);


        tvName = (TextView) findViewById(R.id.nameAppTextView);

        //Assigning the button variables to the buttons in the xml
        editApp = (Button) findViewById(R.id.editAppBtn);
        addApp = (Button) findViewById(R.id.addAppBtn);
        removeApp = (Button) findViewById(R.id.removeAppBtn);
        searchApp = (Button) findViewById(R.id.searchAppBtn);
        viewApp = (Button) findViewById(R.id.viewAppBtn);
        homeButton = (Button) findViewById(R.id.returnHomeBtn);


        //Methods for buttons to perform actions
        AddAppData();
        ViewAll();
        UpdateData();
        lookupAppointment();
        showDialogOnDateEditTextSelect();
        showDialogOnTimeEditTextSelect();


        //Methods to tell the buttons what to do when clicked
        homeButton.setOnClickListener
                (new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent
                                (AppointmentNormalActivity.this, MainNormalActivity.class);
                        startActivity(intent);
                    }
                });
    }

    public void AddAppData() {

        addApp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (editEmail.getText().toString().trim().length() <= 0 ||
                                editDate.getText().toString().trim().length() <= 0 ||
                                editTime.getText().toString().trim().length() <= 0 ||
                                serviceSpinner.getSelectedItem().toString().equals("Service:")) {
                            util.showMessage("Invalid", "Fields \n\n e-Mail \n Date \n Time " +
                                            "\n Service " + "\n\n" + "Required",
                                    AppointmentNormalActivity.this);
                        } else if (util.parseLongFromDateTime(util.concatDate(editDate)) <
                                util.currentDateAsLong()) {

                            util.showMessage("Invalid Date",
                                    "Please enter a date not in the past",
                                    AppointmentNormalActivity.this);

                        } else if (!util.isEmailValid(editEmail.getText().toString())) {

                            util.showMessage("eMail Format", "Please enter a valid eMail address",
                                    AppointmentNormalActivity.this);

                        } else {

                            try {
                                boolean isInserted = db.insertAppData(editEmail.getText().toString(),
                                        util.parseLongFromDateTime(editDate.getText().toString()
                                                + " " + editTime.getText().toString()),
                                        serviceSpinner.getSelectedItem().toString());

                                if (isInserted) {

                                    Toast.makeText(AppointmentNormalActivity.this, "Data Inserted",
                                            Toast.LENGTH_SHORT).show();

                                } else {

                                    Toast.makeText(AppointmentNormalActivity.this, "Date and time" +
                                                    " already booked",
                                            Toast.LENGTH_SHORT).show();
                                }

                            } catch (SQLiteConstraintException e) {
                                util.showMessage("Date Error", "Date and time already booked",
                                        AppointmentNormalActivity.this);

                            }

                        }
                    }
                }
        );
    }

    //Method to view all data that is stored within the database
    public void ViewAll() {
        viewApp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Cursor result = db.getAllAppData();
                        result.moveToFirst();
                        if (result.getCount() == 0) {
                            //Show Message
                            util.showMessage("Error", "No data to display",
                                    AppointmentNormalActivity.this);
                            return;
                        }
                        StringBuilder buffer = new StringBuilder();
                        while (result.moveToNext()) {
                            buffer.append("ID: " + result.getString(result.
                                    getColumnIndex(db.APP_ID)) + "\n");
                            buffer.append("Name: " + result.getString(result.
                                    getColumnIndex(db.APP_NAME)) + "\n");
                            buffer.append("Surname: " + result.getString(result.
                                    getColumnIndex(db.APP_SURNAME)) + "\n");
                            buffer.append("Email: " + result.getString(result.
                                    getColumnIndex(db.APP_EMAIL)) + "\n");
                            buffer.append("Date + Time: " + (util.parseDateTimeFromLong(
                                    result.getLong(result.getColumnIndex(db.APP_DATE))) + "\n"));
                            buffer.append("Service: " + result.getString(result.
                                    getColumnIndex(db.APP_SERVICE)) + "\n");
                            buffer.append("Price: " + result.getString(result.
                                    getColumnIndex(db.PRICE_PRICE)) + "\n");
                            buffer.append("Duration: " + result.getString(result.
                                    getColumnIndex(db.DURATION)) + "\n\n");

                        }
                        //Show all data
                        util.showMessage("Client Appointments", buffer.toString(),
                                AppointmentNormalActivity.this);
                        //db.getDate();
                    }
                }
        );
    }


    //Method to allow user to edit and update data in database
    public void UpdateData() {
        editApp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (editEmail.getText().toString().trim().length() <= 0 ||
                                editDate.getText().toString().trim().length() <= 0 ||
                                editTime.getText().toString().trim().length() <= 0 ||
                                serviceSpinner.getSelectedItem().toString().equals("Service:")) {

                            util.showMessage("Invalid", "Fields \n e-Mail \n Date \n Time \n Service" +
                                    " \n" + "Required", AppointmentNormalActivity.this);

                        } else if (util.parseLongFromDateTime(util.concatDate(editDate)) <
                                util.currentDateAsLong()) {

                            util.showMessage("Invalid Date",
                                    "Please enter a date not in the past",
                                    AppointmentNormalActivity.this);

                        } else if (!util.isEmailValid(editEmail.getText().toString())) {

                            util.showMessage("eMail Format", "Please enter a valid eMail address",
                                    AppointmentNormalActivity.this);

                        } else {

                            boolean isUpdated = db.updateAppData
                                    (editName.getText().toString(),
                                            editSurname.getText().toString(),
                                            editEmail.getText().toString(),
                                            util.parseLongFromDateTime(editDate.getText().toString()
                                                    + " " + editTime.getText().toString()),
                                            serviceSpinner.getSelectedItem().toString());

                            if (isUpdated) {
                                Toast.makeText(AppointmentNormalActivity.this, "Data Updated",
                                        Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(AppointmentNormalActivity.this, "Data Not Updated",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
        );
    }

    public void showDialogOnDateEditTextSelect() {
        editDate = (EditText) findViewById(R.id.dateEditText);

        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });
    }


    public void showDialogOnTimeEditTextSelect() {
        editTime = (EditText) findViewById(R.id.timeEditText);

        editTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID_TIME);
            }
        });
    }


    @Override
    protected Dialog onCreateDialog(int id) {

        AlertDialog myDialog = null;

        switch (id) {
            case DIALOG_ID:
                myDialog = new DatePickerDialog(this, datePickerListener, year_x, month_x, day_x);
                break;

            case DIALOG_ID_TIME:
                myDialog = new TimePickerDialog(this, timePickerListener, hour_x, minute_x, false);
                break;
        }
        return myDialog;
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
            editDate.setText(String.valueOf(editDay + "-" + editMonth + "-" + year_x));
        }
    };

    private TimePickerDialog.OnTimeSetListener timePickerListener
            = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour_x = hourOfDay;
            minute_x = minute;

            String editHour = ("" + hour_x);
            String editMinute = ("" + minute_x);

            if (hour_x < 10) {
                editHour = ("0" + hour_x);
            }
            if (minute_x < 10) {
                editMinute = ("0" + minute_x);
            }
            Toast.makeText(AppointmentNormalActivity.this, editHour + ":" + editMinute,
                    Toast.LENGTH_SHORT)
                    .show();
            editTime.setText(String.valueOf(editHour + ":" + editMinute + ":00"));
        }
    };


    public void lookupAppointment() {
        searchApp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor cursor = db.findAppointment(editEmail.getText().toString());

                        Cursor cursorID = db.findAppointmentID(editEmail.getText().toString(),
                                editID.getText().toString());

                        if (editEmail.getText().toString().trim().length() <= 0) {
                            util.showMessage("Email Missing",
                                    "Please enter an email to perform a search",
                                    AppointmentNormalActivity.this);

                        } else if (!util.isEmailValid(editEmail.getText().toString())) {

                            util.showMessage("eMail Format", "Please enter a valid eMail address",
                                    AppointmentNormalActivity.this);

                        } else if (editID.getText().toString().trim().length() <= 0) {

                            if (cursor.getCount() == 1) {

                                cursor.moveToFirst();

                                editID.setText(cursor.getString(cursor.getColumnIndex(db.APP_ID)));

                                tvName.setText(String.valueOf("Client Name: " + cursor.getString(
                                        cursor.getColumnIndex(db.APP_NAME))
                                        + " " + cursor.getString(
                                        cursor.getColumnIndex(db.APP_SURNAME))));

                                editEmail.setText(cursor.getString(cursor.
                                        getColumnIndex(db.APP_EMAIL)));

                                editDate.setText(util.parseDateFromLong(cursor.getLong(
                                        cursor.getColumnIndex(db.APP_DATE))));

                                Log.d("Date String", (util.parseDateFromLong(cursor.getLong(
                                        cursor.getColumnIndex(db.APP_DATE)))));

                                Log.d("Date Long", "" + (cursor.getLong(
                                        cursor.getColumnIndex(db.APP_DATE))));


                                editTime.setText(util.parseTimeFromLong(cursor.getLong(
                                        cursor.getColumnIndex(db.APP_DATE))));

                                serviceSpinner.setSelection(spinnerAdapter.getPosition(String.
                                        valueOf(cursor.getString(
                                                cursor.getColumnIndex(db.APP_SERVICE)))));

                                cursor.close();

                            } else if (cursor.getCount() > 1) {

                                cursor.moveToFirst();

                                StringBuilder buffer = new StringBuilder();

                                while (cursor.moveToNext()) {

                                    buffer.append("ID: " + cursor.getString(
                                            cursor.getColumnIndex(db.APP_ID)) + "\n");
                                    buffer.append("Name: " + cursor.getString(
                                            cursor.getColumnIndex(db.APP_NAME)) + "\n");
                                    buffer.append("Surname: " + cursor.getString
                                            (cursor.getColumnIndex(db.APP_SURNAME)) + "\n");
                                    buffer.append("Email: " + cursor.getString(
                                            cursor.getColumnIndex(db.APP_EMAIL)) + "\n");
                                    buffer.append("Date + Time: " + (util.parseDateTimeFromLong(
                                            cursor.getLong(cursor.getColumnIndex(
                                                    db.APP_DATE))) + "\n"));
                                    buffer.append("Service: " + cursor.getString(
                                            cursor.getColumnIndex(db.APP_SERVICE)) + "\n");
                                    buffer.append("Price: " + cursor.getString(
                                            cursor.getColumnIndex(db.PRICE_PRICE)) + "\n");
                                    buffer.append("Duration: " + cursor.getString(
                                            cursor.getColumnIndex(db.DURATION)) + "\n\n");

                                }

                                util.showMessage("Multiple Result search again using ID",
                                        "" + buffer, AppointmentNormalActivity.this);
                                cursor.close();

                            } else {
                                Toast.makeText(AppointmentNormalActivity.this, "No Match Found",
                                        Toast.LENGTH_LONG).show();
                            }

                        } else {

                            if (cursorID.moveToFirst()) {
                                editID.setText(cursorID.getString(cursorID.
                                        getColumnIndex(db.APP_ID)));

                                tvName.setText(String.valueOf("Client Name: " + cursorID.getString(
                                        cursorID.getColumnIndex(db.APP_NAME))
                                        + " " + cursorID.getString(
                                        cursorID.getColumnIndex(db.APP_SURNAME))));

                                editEmail.setText(cursorID.getString(cursorID.
                                        getColumnIndex(db.APP_EMAIL)));

                                editDate.setText(util.parseDateFromLong(cursorID.getLong(
                                        cursorID.getColumnIndex(db.APP_DATE))));

                                Log.d("Date String", (util.parseDateFromLong(cursorID.getLong(
                                        cursorID.getColumnIndex(db.APP_DATE)))));

                                Log.d("Date Long", "" + (cursorID.getLong(
                                        cursorID.getColumnIndex(db.APP_DATE))));


                                editTime.setText(util.parseTimeFromLong(cursorID.getLong(
                                        cursorID.getColumnIndex(db.APP_DATE))));

                                serviceSpinner.setSelection(spinnerAdapter.getPosition(String.
                                        valueOf(cursorID.getString(
                                                cursorID.getColumnIndex(db.APP_SERVICE)))));

                                cursorID.close();
                            } else {
                                util.showMessage("Search Error", "No results found," +
                                                " check eMail and ID are correct",
                                        AppointmentNormalActivity.this);
                            }
                        }
                    }
                });
    }
}
