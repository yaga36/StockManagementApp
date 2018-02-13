package com.example.reece.stockmanagementapp;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Typeface;
import android.widget.*;
import android.content.*;
import android.graphics.*;
import android.view.*;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Reece on 23/03/2016.
 */
public class FontManager {

    public static final String ROOT = "fonts/",
            FONTAWESOME = ROOT + "fontawesome-webfont.ttf";


    public static Typeface getTypeface(Context context, String font) {
        return Typeface.createFromAsset(context.getAssets(), font);
    }
    
}

