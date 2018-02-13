package com.example.reece.stockmanagementapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Patterns;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Reece on 21/03/2016.
 */

public class Utility {

    public long parseLongFromDateTime(String time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.UK);

            Date date = sdf.parse(time);

            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public long parseLongFromDate(String dateTime) {
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);

            Date date = sdf.parse(dateTime);

            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String parseDateFromLong(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
        return sdf.format(new Date(time));
    }

    public String parseTimeFromLong(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.UK);
        return sdf.format(new Date(time));
    }

    public String parseDateTimeFromLong(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.UK);
        return sdf.format(new Date(time));
    }

    public String parseFullDateTimeFromLong(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.UK);
        return sdf.format(new Date(time));
    }

    public long getCurrentDateTime(){
        return System.currentTimeMillis();
    }

    public long currentDateAsLong(){
        return parseLongFromDate(
                parseDateFromLong(
                        getCurrentDateTime()));
    }

    public String concatDate(EditText editTextDate) {
        return editTextDate.getText().toString() + " 00:00:00";
    }

    public boolean isEmailValid(CharSequence email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    //Method to bring up an alert box that contains database data
    public void showMessage(String title, String Message, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }


}
