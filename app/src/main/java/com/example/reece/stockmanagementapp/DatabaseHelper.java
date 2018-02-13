package com.example.reece.stockmanagementapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Reece on 09/01/2016.
 */


public class DatabaseHelper extends SQLiteOpenHelper {

    Utility util = new Utility();

    StockActivity stockActivity = new StockActivity();

    AppointmentActivity appoint;

    private static final String TAG = DatabaseHelper.class.getSimpleName();

    //  Variables for the database
    private static final String DATABASE_NAME = "shop.db";
    private static final int DATABASE_VERSION = 1;


    //  Variables for client table
    private static final String APPOINTMENT_TABLE_NAME = "appointment_table";
    public static final String APP_ID = "ID";
    public static final String APP_NAME = "NAME";
    public static final String APP_SURNAME = "SURNAME";
    public static final String APP_EMAIL = "EMAIL";
    public static final String APP_DATE = "DATE_TIME";
    public static final String APP_SERVICE = "HAIR_SERVICE";


    //  Variables for the stock table
    private static final String STOCK_TABLE_NAME = "stock_table";
    public static final String STOCK_ID = "ID";
    public static final String STOCK_BARCODE = "BARCODE";
    public static final String STOCK_NAME = "PRODUCT_NAME";
    public static final String STOCK_TYPE = "PRODUCT_TYPE";
    public static final String STOCK_QUANTITY = "QUANTITY";


//  Variables for the clients table

    private static final String CLIENT_TABLE_NAME = "client_table";
    public static final String CLIENT_ID = "ID";
    public static final String CLIENT_NAME = "NAME";
    public static final String CLIENT_SURNAME = "SURNAME";
    public static final String CLIENT_EMAIL = "EMAIL";
    public static final String CLIENT_DOB = "DOB";

//  Variables for the price list table

    private static final String PRICE_TABLE_NAME = "price_table";
    public static final String PRICE_ID = "ID";
    public static final String PRICE_SERVICE = "SERVICE";
    public static final String PRICE_PRICE = "PRICE";
    public static final String DURATION = "DURATION";

    //  Variables for the user login table
    private static final String LOGIN_SQLITE_TABLE_NAME = "login_sqlite_table";
    public static final String LOGIN_SQLITE_ID = "ID";
    public static final String LOGIN_SQLITE_NAME = "NAME";
    public static final String LOGIN_SQLITE_USERNAME = "USERNAME";
    public static final String LOGIN_SQLITE_PASSWORD = "PASSWORD";
    public static final String LOGIN_SQLITE_STATUS = "STATUS";

    private static final String DEFAULT_ADMIN_NAME = "'Admin'";
    private static final String DEFAULT_ADMIN_USERNAME = "'admin'";
    private static final String DEFAULT_ADMIN_PASSWORD = "'password'";
    private static final String DEFAULT_ADMIN_STATUS = "'admin'";

    private static final String DEFAULT_NORMAL_NAME = "'Default'";
    private static final String DEFAULT_NORMAL_USERNAME = "'normal'";
    private static final String DEFAULT_NORMAL_PASSWORD = "'password'";
    private static final String DEFAULT_NORMAL_STATUS = "'normal'";

    private static final String DEFAULT_PRICELIST_SERVICE = "'Cut and Blow'";
    private static final String DEFAULT_PRICELIST_DURATION = "'30'";
    private static final String DEFAULT_PRICELIST_PRICE = "'25'";

    private static final String DEFAULT_PRICELIST_SERVICE_2 = "'Hair Dye'";
    private static final String DEFAULT_PRICELIST_DURATION_2 = "'60'";
    private static final String DEFAULT_PRICELIST_PRICE_2 = "'50'";

    private static final String DEFAULT_PRICELIST_SERVICE_3 = "'Service:'";
    private static final String DEFAULT_PRICELIST_DURATION_3 = null;
    private static final String DEFAULT_PRICELIST_PRICE_3 = null;

    private static final String DEFAULT_APP_EMAIL = null;
    private static final String DEFAULT_APP_DATE = null;
    private static final String DEFAULT_APP_TIME = "";
    private static final String DEFAULT_APP_SERVICE = null;


    //  Variables for the user login table
    private static final String LOGIN_TABLE_NAME = "login_table";
    private static final String LOGIN_ID = "ID";
    private static final String LOGIN_USER_ID = "UID";
    private static final String LOGIN_NAME = "NAME";
    private static final String LOGIN_EMAIL = "EMAIL";
    private static final String LOGIN_CREATED_AT = "CREATED_AT";


    //  Creating the tables and assigning them as variables
    private static final String CREATE_TABLE_CLIENT = "CREATE TABLE IF NOT EXISTS " +
            CLIENT_TABLE_NAME +
            " " + " (" + CLIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            " " + CLIENT_NAME + " TEXT," +
            " " + CLIENT_SURNAME + " TEXT," +
            " " + CLIENT_EMAIL + " TEXT UNIQUE," +
            " " + CLIENT_DOB + " INTEGER)";


    private static final String CREATE_TABLE_STOCK = "CREATE TABLE IF NOT EXISTS "
            + STOCK_TABLE_NAME +
            " (" + STOCK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            " " + STOCK_BARCODE + " INTEGER UNIQUE," +
            " " + STOCK_NAME + " TEXT," +
            " " + STOCK_TYPE + " TEXT," +
            " " + STOCK_QUANTITY + " INTEGER)";

    private static final String CREATE_TABLE_APP = "CREATE TABLE IF NOT EXISTS "
            + APPOINTMENT_TABLE_NAME +
            " (" + APP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            " " + APP_NAME + " TEXT," +
            " " + APP_SURNAME + " TEXT," +
            " " + APP_EMAIL + " TEXT," +
            " " + APP_DATE + " INTEGER UNIQUE," +
            " " + APP_SERVICE + " TEXT)";

    private static final String CREATE_TABLE_PRICE = "CREATE TABLE IF NOT EXISTS "
            + PRICE_TABLE_NAME +
            " (" + PRICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            " " + PRICE_SERVICE + " TEXT," +
            " " + PRICE_PRICE + " INTEGER," +
            " " + DURATION + " INTEGER)";

    private static final String CREATE_TABLE_SQLITE_LOGIN = "CREATE TABLE IF NOT EXISTS "
            + LOGIN_SQLITE_TABLE_NAME +
            " (" + LOGIN_SQLITE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            " " + LOGIN_SQLITE_NAME + " TEXT," +
            " " + LOGIN_SQLITE_USERNAME + " TEXT UNIQUE NOT NULL," +
            " " + LOGIN_SQLITE_PASSWORD + " TEXT NOT NULL," +
            " " + LOGIN_SQLITE_STATUS + " TEXT)";

    private static final String ADD_ADMIN_DEFAULT_USER = "INSERT INTO " + LOGIN_SQLITE_TABLE_NAME +
            " (" + LOGIN_SQLITE_NAME + ", " + LOGIN_SQLITE_USERNAME + ", " + LOGIN_SQLITE_PASSWORD +
            ", " + LOGIN_SQLITE_STATUS + ") VALUES (" + DEFAULT_ADMIN_NAME + ", "
            + DEFAULT_ADMIN_USERNAME + ", " + DEFAULT_ADMIN_PASSWORD + ", "
            + DEFAULT_ADMIN_STATUS + ");";

    private static final String ADD_DEFAULT_USER = "INSERT INTO " + LOGIN_SQLITE_TABLE_NAME +
            " (" + LOGIN_SQLITE_NAME + ", " + LOGIN_SQLITE_USERNAME + ", " + LOGIN_SQLITE_PASSWORD +
            ", " + LOGIN_SQLITE_STATUS + ") VALUES (" + DEFAULT_NORMAL_NAME + ", "
            + DEFAULT_NORMAL_USERNAME + ", " + DEFAULT_NORMAL_PASSWORD + ", "
            + DEFAULT_NORMAL_STATUS + ");";

    private static final String ADD_DEFAULT_PRICELIST = "INSERT INTO " + PRICE_TABLE_NAME +
            "( " + PRICE_SERVICE + ", " + PRICE_PRICE + ", " + DURATION + ") VALUES ( "
            + DEFAULT_PRICELIST_SERVICE + ", " + DEFAULT_PRICELIST_PRICE + ", "
            + DEFAULT_PRICELIST_DURATION + ");";

    private static final String ADD_DEFAULT_PRICELIST_2 = "INSERT INTO " + PRICE_TABLE_NAME +
            "( " + PRICE_SERVICE + ", " + PRICE_PRICE + ", " + DURATION + ") VALUES ( "
            + DEFAULT_PRICELIST_SERVICE_2 + ", " + DEFAULT_PRICELIST_PRICE_2 + ", "
            + DEFAULT_PRICELIST_DURATION_2 + ");";

    private static final String ADD_DEFAULT_PRICELIST_3 = "INSERT INTO " + PRICE_TABLE_NAME +
            "( " + PRICE_SERVICE + ", " + PRICE_PRICE + ", " + DURATION + ") VALUES ( "
            + DEFAULT_PRICELIST_SERVICE_3 + ", " + DEFAULT_PRICELIST_PRICE_3 + ", "
            + DEFAULT_PRICELIST_DURATION_3 + ");";

    private static final String ADD_DEFAULT_APP = "INSERT INTO " + APPOINTMENT_TABLE_NAME +
            "( " + APP_EMAIL + ", " + APP_DATE + ", " +  ", " + APP_SERVICE + ") VALUES ( "
            + DEFAULT_APP_EMAIL + ", " + DEFAULT_APP_DATE + ", "  + DEFAULT_APP_SERVICE + ");";

    private static final String CREATE_TABLE_LOGIN = "CREATE TABLE IF NOT EXISTS "
            + LOGIN_TABLE_NAME +
            " (" + LOGIN_ID + " INTEGER PRIMARY KEY," +
            " " + LOGIN_USER_ID + " TEXT," +
            " " + LOGIN_NAME + " TEXT," +
            " " + LOGIN_EMAIL + " TEXT UNIQUE," +
            " " + LOGIN_CREATED_AT + " TEXT)";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CLIENT);
        db.execSQL(CREATE_TABLE_STOCK);
        db.execSQL(CREATE_TABLE_APP);
        db.execSQL(CREATE_TABLE_PRICE);
        db.execSQL(CREATE_TABLE_SQLITE_LOGIN);
        db.execSQL(CREATE_TABLE_LOGIN);
        db.execSQL(ADD_ADMIN_DEFAULT_USER);
        db.execSQL(ADD_DEFAULT_USER);
        db.execSQL(ADD_DEFAULT_PRICELIST);
        db.execSQL(ADD_DEFAULT_PRICELIST_2);
        //db.execSQL(ADD_DEFAULT_APP);

        Log.d(TAG, "Database tables have been created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CLIENT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + STOCK_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + APPOINTMENT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + LOGIN_SQLITE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PRICE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + LOGIN_TABLE_NAME);

    }


//------------------------------Methods for the table Appointment---------------------------------//

    public boolean insertAppData(String email, Long date,
                                 String hair_service) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(APP_EMAIL, email);
        contentValues.put(APP_DATE, date);
        contentValues.put(APP_SERVICE, hair_service);
        long result = db.insert(APPOINTMENT_TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean updateAppData(String name, String surname, String email, Long date,
                                 String hair_service) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(APP_NAME, name);
        contentValues.put(APP_SURNAME, surname);
        contentValues.put(APP_EMAIL, email);
        contentValues.put(APP_DATE, date);
        contentValues.put(APP_SERVICE, hair_service);
        return (db.update(APPOINTMENT_TABLE_NAME, contentValues, "EMAIL = ?"
                , new String[]{email}) > 0);
    }

    public Integer deleteAppData(String email, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(APPOINTMENT_TABLE_NAME, "EMAIL = ? AND ID = ?", new String[]{email, id});
    }

    public Cursor getAllAppData() {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + APPOINTMENT_TABLE_NAME + "." + APP_ID + ", " + CLIENT_TABLE_NAME
                + "." + CLIENT_NAME + ", " + CLIENT_TABLE_NAME + "." + CLIENT_SURNAME + ", "
                + CLIENT_TABLE_NAME + "." + CLIENT_EMAIL + ", " + APPOINTMENT_TABLE_NAME + "."
                + APP_SERVICE + ", " + APPOINTMENT_TABLE_NAME + "." + APP_DATE + ", "
                + PRICE_TABLE_NAME + "." + PRICE_PRICE + ", " + PRICE_TABLE_NAME + "." + DURATION
                + ", " + APPOINTMENT_TABLE_NAME + "." + APP_DATE + " FROM " + APPOINTMENT_TABLE_NAME
                + " LEFT JOIN " + CLIENT_TABLE_NAME + " ON " + APPOINTMENT_TABLE_NAME
                + "." + APP_EMAIL + " = " + CLIENT_TABLE_NAME + "." +
                CLIENT_EMAIL + " LEFT JOIN " + PRICE_TABLE_NAME + " ON " + PRICE_TABLE_NAME
                + "." + PRICE_SERVICE + " = " + APPOINTMENT_TABLE_NAME + "." + APP_SERVICE
                + " WHERE " + APPOINTMENT_TABLE_NAME + "." + APP_DATE + " >=" +
                 util.currentDateAsLong();

        return db.rawQuery(query, null);
    }

//    public void getDate() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String query1 = "SELECT * FROM " + APPOINTMENT_TABLE_NAME + " " + APP_DATE + ";";
//
//        Cursor cursor;
//
//        cursor = db.rawQuery(query1, null);
//
//        cursor.moveToFirst();
//        if (cursor.moveToNext()) {
//            Log.d("DATE", DatabaseUtils.dumpCursorToString(cursor));
//        }
//
//    }

    public Cursor findAppointment(String email){

        String query = "SELECT "  + APPOINTMENT_TABLE_NAME + "." + APP_ID + ", "
                + CLIENT_TABLE_NAME + "." + CLIENT_NAME + ", "
                + CLIENT_TABLE_NAME + "." + CLIENT_SURNAME + ", " + CLIENT_TABLE_NAME + "."
                + CLIENT_EMAIL + ", " + APPOINTMENT_TABLE_NAME + "." + APP_SERVICE + ", " +
                APPOINTMENT_TABLE_NAME + "." + APP_DATE + ", " + PRICE_TABLE_NAME + "."
                + PRICE_PRICE + ", " + PRICE_TABLE_NAME + "." + DURATION + ", "
                + APPOINTMENT_TABLE_NAME + "." + APP_DATE +
                " FROM " + CLIENT_TABLE_NAME +
                " LEFT JOIN " + APPOINTMENT_TABLE_NAME + " ON " + APPOINTMENT_TABLE_NAME
                + "." + APP_EMAIL + " = " + CLIENT_TABLE_NAME + "." +
                CLIENT_EMAIL + " LEFT JOIN " + PRICE_TABLE_NAME + " ON " + PRICE_TABLE_NAME
                + "." + PRICE_SERVICE + " = " + APPOINTMENT_TABLE_NAME + "." + APP_SERVICE +
                " WHERE " + CLIENT_TABLE_NAME + "." + CLIENT_EMAIL + " =  \"" + email + "\" AND "
                + APPOINTMENT_TABLE_NAME + "." + APP_DATE + " >=" + util.currentDateAsLong();

        SQLiteDatabase db = getWritableDatabase();

        return db.rawQuery(query, null);

    }

    public Cursor findAppointmentID(String email, String id){

        String query = "SELECT "  + APPOINTMENT_TABLE_NAME + "." + APP_ID + ", "
                + CLIENT_TABLE_NAME + "." + CLIENT_NAME + ", "
                + CLIENT_TABLE_NAME + "." + CLIENT_SURNAME + ", " + CLIENT_TABLE_NAME + "."
                + CLIENT_EMAIL + ", " + APPOINTMENT_TABLE_NAME + "." + APP_SERVICE + ", " +
                APPOINTMENT_TABLE_NAME + "." + APP_DATE + ", " + PRICE_TABLE_NAME + "."
                + PRICE_PRICE + ", " + PRICE_TABLE_NAME + "." + DURATION + ", "
                + APPOINTMENT_TABLE_NAME + "." + APP_DATE +
                " FROM " + CLIENT_TABLE_NAME +
                " LEFT JOIN " + APPOINTMENT_TABLE_NAME + " ON " + APPOINTMENT_TABLE_NAME
                + "." + APP_EMAIL + " = " + CLIENT_TABLE_NAME + "." +
                CLIENT_EMAIL + " LEFT JOIN " + PRICE_TABLE_NAME + " ON " + PRICE_TABLE_NAME
                + "." + PRICE_SERVICE + " = " + APPOINTMENT_TABLE_NAME + "." + APP_SERVICE +
                " WHERE " + CLIENT_TABLE_NAME + "." + CLIENT_EMAIL + " =  \"" + email + "\"" +
                " AND " + APPOINTMENT_TABLE_NAME + "." + APP_ID + " = \"" + id + "\" AND "
                + APPOINTMENT_TABLE_NAME + "." + APP_DATE + " >=" + util.currentDateAsLong();

        SQLiteDatabase db = getWritableDatabase();

        return db.rawQuery(query, null);

    }


    public Cursor checkEmail(String email){
        String query = "SELECT " + CLIENT_EMAIL + " FROM " + CLIENT_TABLE_NAME + " WHERE "
                + CLIENT_EMAIL + " =  \"" + email + "\"";

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        return cursor;

}



//---------------------------Methods for the table Client-----------------------------------------//


    public Cursor getAllClientData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + CLIENT_TABLE_NAME, null);
    }

    public boolean insertClientData(String name, String surname, String email, long dob) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CLIENT_NAME, name);
        contentValues.put(CLIENT_SURNAME, surname);
        contentValues.put(CLIENT_EMAIL, email);
        contentValues.put(CLIENT_DOB, dob);
        long result = db.insert(CLIENT_TABLE_NAME, null, contentValues);
        return (result == -1);
    }

    public boolean updateClientData(String name, String surname, String email, long dob) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CLIENT_NAME, name);
        contentValues.put(CLIENT_SURNAME, surname);
        contentValues.put(CLIENT_EMAIL, email);
        contentValues.put(CLIENT_DOB, dob);
        return (db.update(CLIENT_TABLE_NAME, contentValues, "EMAIL=?", new String[]{email}) > 0);
    }

    public Integer deleteClientData(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(CLIENT_TABLE_NAME, "EMAIL = ?", new String[]{email});
    }

    public Client findClient(String email) {

        String query = "SELECT * FROM " + CLIENT_TABLE_NAME + " WHERE " + CLIENT_EMAIL +
                " =  \"" + email + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Client client = new Client();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            client.setID(Integer.parseInt(cursor.getString(0)));
            client.setName(cursor.getString(1));
            client.setSurname(cursor.getString(2));
            client.setEmail(cursor.getString(3));
            client.setDOB(cursor.getString(4));
            cursor.close();
        } else {
            client = null;
        }
        db.close();
        return client;
    }


//------------------------------Methods for the table Stock---------------------------------------//

    //A method to insert data into the database based on user input
    public boolean insertStockData(String barcode, String product_name, String type,
                                   String quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(STOCK_BARCODE, barcode);
        contentValues.put(STOCK_NAME, product_name);
        contentValues.put(STOCK_TYPE, type);
        contentValues.put(STOCK_QUANTITY, quantity);
        long result = db.insert(STOCK_TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

//    public boolean insertScanData(String barcode) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(STOCK_BARCODE, barcode);
//        long result = db.insert(STOCK_TABLE_NAME, null, contentValues);
//        return (result == -1);
//    }

    //A method to run a query to return all fields from the stock database
    public Cursor getAllStockData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + STOCK_TABLE_NAME, null);

    }

    //Runs a query to select everything from the stock table with the matching barcode,
    //a cursor then sets each field value and returns the results.
    public Stock findProduct(String barcode) {
        String query = "SELECT * FROM " + STOCK_TABLE_NAME + " WHERE " + STOCK_BARCODE +
                " =  " + barcode + "" ;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Stock product = new Stock();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            product.setID(Integer.parseInt(cursor.getString(0)));
            product.setBarcode(cursor.getString(1));
            product.setProductName(cursor.getString(2));
            product.setProductType(cursor.getString(3));
            product.setQuantity(Integer.parseInt(cursor.getString(4)));
            cursor.close();
        } else {
            product = null;
        }
        db.close();
        return product;
    }

    //Updates the database table with the fields input by the user
    public boolean updateStockData(String barcode, String product_name, String type,
                                   String quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(STOCK_BARCODE, barcode);
        contentValues.put(STOCK_NAME, product_name);
        contentValues.put(STOCK_TYPE, type);
        contentValues.put(STOCK_QUANTITY, quantity);
        return (db.update(STOCK_TABLE_NAME, contentValues
                , "BARCODE = ?", new String[]{barcode}) > 0);
    }

    //A query gets the quantity field based on the rows barcode a cursor then assigns the value
    //of the quantity to an int variable and returns the value.
    public Integer getQuantity(String barcode) {

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + STOCK_QUANTITY + " FROM " + STOCK_TABLE_NAME + " WHERE "
                + STOCK_BARCODE + " = " + barcode + ";";

        Cursor cursor = db.rawQuery(query, null);

        Integer quantityInt;

        cursor.moveToFirst();
        try {
            quantityInt = cursor.getInt(0);
        } catch (CursorIndexOutOfBoundsException e) {
            return 500;
        }

        cursor.close();

        return quantityInt;
    }

    //A query updates the quantity field based on the rows barcode, the cursor then loops through
    //and runs the query on the database updating the field.
    public void updateQuantity(String barcode, int quantity) {

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + STOCK_TABLE_NAME + " SET " + STOCK_QUANTITY + " = "
                + quantity + " WHERE " + STOCK_BARCODE + " = " + barcode + ";";

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        cursor.close();
    }

    //Runs a query to delete the row of data based on the barcode
    public Integer deleteStockData(String barcode) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(STOCK_TABLE_NAME, "BARCODE = ?", new String[]{barcode});
    }


//------------------------------Methods for the table Price--------------------------------------//

    public boolean insertPriceData(String service, String price, String duration) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRICE_SERVICE, service);
        contentValues.put(PRICE_PRICE, price);
        contentValues.put(DURATION, duration);
        long result = db.insert(PRICE_TABLE_NAME, null, contentValues);
        return (result == -1);
    }

    public Cursor getAllPriceData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + PRICE_TABLE_NAME, null);
    }

    public PriceList findPriceService(String service) {
        String query = "SELECT * FROM " + PRICE_TABLE_NAME + " WHERE " + PRICE_SERVICE +
                " =  \"" + service + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        PriceList pricelist = new PriceList();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            pricelist.setID(Integer.parseInt(cursor.getString(0)));
            pricelist.setService(cursor.getString(1));
            pricelist.setPrice(Integer.parseInt(cursor.getString(2)));
            pricelist.setDuration(Integer.parseInt(cursor.getString(3)));
            cursor.close();
        } else {
            pricelist = null;
        }
        db.close();
        return pricelist;
    }

    public boolean updatePriceData(String service, String price, String duration) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRICE_SERVICE, service);
        contentValues.put(PRICE_PRICE, price);
        contentValues.put(DURATION, duration);
        return (db.update(PRICE_TABLE_NAME, contentValues, "SERVICE = ?"
                , new String[]{service}) > 0);

    }

    public Integer deletePriceData(String service) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(PRICE_TABLE_NAME, "SERVICE = ?", new String[]{service});
    }


    public ArrayList<String> getAllServices() {

        ArrayList<String> spinnerService = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + PRICE_SERVICE + " FROM " + PRICE_TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String service = cursor.getString(cursor.getColumnIndexOrThrow(PRICE_SERVICE));
                spinnerService.add(service);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return spinnerService;
    }


//------------------------------Methods for the table LoginSQLite---------------------------------//

    public String searchPass(String usernameEntered) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + LOGIN_SQLITE_USERNAME + ", " + LOGIN_SQLITE_PASSWORD + " FROM "
                + LOGIN_SQLITE_TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String usernameString, passwordString;
        passwordString = "Not Found";
        if (cursor.moveToFirst()) {
            do {
                usernameString = cursor.getString(0);

                if (usernameString.equals(usernameEntered)) {
                    passwordString = cursor.getString(1);
                    cursor.close();
                    break;
                }
            }
            while (cursor.moveToNext());
        }
        return passwordString;
    }

    public String returnStatus(String usernameEntered) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + LOGIN_SQLITE_USERNAME + ", " + LOGIN_SQLITE_STATUS + " FROM "
                + LOGIN_SQLITE_TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String usernameString, statusString;
        statusString = "Not Found";
        if (cursor.moveToFirst()) {
            do {
                usernameString = cursor.getString(0);
                if (usernameString.equals(usernameEntered)) {
                    statusString = cursor.getString(1);
                    cursor.close();
                    break;
                }
            }
            while (cursor.moveToNext());
        }
        return statusString;
    }


//---------------------------Methods for the table RegisterSQLite---------------------------------//

    public boolean insertRegisterData(String name, String username, String password, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LOGIN_SQLITE_NAME, name);
        contentValues.put(LOGIN_SQLITE_USERNAME, username);
        contentValues.put(LOGIN_SQLITE_PASSWORD, password);
        contentValues.put(LOGIN_SQLITE_STATUS, status);

        long result = db.insert(LOGIN_SQLITE_TABLE_NAME, null, contentValues);
        db.close();
        return (result == -1);
    }

    public Cursor getAllRegisterData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + LOGIN_SQLITE_TABLE_NAME, null);
    }

    public boolean updateRegisterData(String name, String username, String password, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LOGIN_SQLITE_NAME, name);
        contentValues.put(LOGIN_SQLITE_USERNAME, username);
        contentValues.put(LOGIN_SQLITE_PASSWORD, password);
        contentValues.put(LOGIN_SQLITE_STATUS, status);
        return (db.update(LOGIN_SQLITE_TABLE_NAME, contentValues, "USERNAME = ?"
                , new String[]{username}) > 0);

    }

    public Integer deleteRegisterData(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(LOGIN_SQLITE_TABLE_NAME, "USERNAME = ?", new String[]{username});
    }


    public List<String> getAllStatus() {
        List<String> status = new ArrayList<>();

        String selectQuery = "SELECT DISTINCT " + LOGIN_SQLITE_STATUS + " FROM "
                + LOGIN_SQLITE_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                status.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return status;
    }

    public Register findUser(String username) {

        String query = "SELECT * FROM " + LOGIN_SQLITE_TABLE_NAME + " WHERE "
                + LOGIN_SQLITE_USERNAME + " = \"" + username + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Register user = new Register();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            user.setID(Integer.parseInt(cursor.getString(0)));
            user.setName(cursor.getString(1));
            user.setUsername(cursor.getString(2));
            user.setPassword(cursor.getString(3));
            user.setUser(cursor.getString(4));
            cursor.close();
        } else {
            user = null;
        }
        db.close();
        return user;
    }

//-------------------------------Methods for the table Login--------------------------------------//

    public void addUser(String name, String email, String uid, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(LOGIN_NAME, name);
        contentValues.put(LOGIN_EMAIL, email);
        contentValues.put(LOGIN_USER_ID, uid);
        contentValues.put(LOGIN_CREATED_AT, created_at);

        long result = db.insert(LOGIN_TABLE_NAME, null, contentValues);
        db.close();

        Log.d(TAG, "New user inserted into database: " + result);

    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT * FROM " + LOGIN_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("name", cursor.getString(1));
            user.put("email", cursor.getString(2));
            user.put("uid", cursor.getString(3));
            user.put("created_at", cursor.getString(4));
        }
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching user from database: " + user.toString());

        return user;
    }

    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(LOGIN_TABLE_NAME, null, null);
        db.close();

        Log.d(TAG, "Deleted all user information from database");
    }


}
