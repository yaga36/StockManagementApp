package com.example.reece.stockmanagementapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.NumberPicker;

/**
 * Created by Reece on 20/03/2016.
 */
public class NumberPickerDialog extends DialogFragment{

    NumberPicker np;


    public Dialog onCreateDialog(Bundle savedInstanceState){

        np = new NumberPicker(getActivity());

        np.setMinValue(0);
        np.setMaxValue(100);
        np.setWrapSelectorWheel(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.number_dialog, null))
                .setTitle("Decrease Stock Quantity")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("OK", "This is OK message");
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("CANCEL", "This is Cancel Message");
                    }
                });
        return builder.create();
    }



}
