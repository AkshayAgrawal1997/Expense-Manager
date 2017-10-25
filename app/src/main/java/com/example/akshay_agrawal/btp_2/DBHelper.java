package com.example.akshay_agrawal.btp_2;

/**
 * Created by Akshay_agrawal on 01-03-2017.
 */

import java.util.ArrayList;
import android.database.Cursor;
import java.util.List;
import android.content.Context;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public class DBHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "ExpensePlanner.db";
    public static String TABLE_NAME = "expense";
    public static int DATABASE_VERSION = 22;
    public static String expense_id = "id";
    public static String expense_Select_Category = "Select_Category";
    public static String expense_Expense = "Expense";
    public static String expense_Note = "Note";
    public static String expense_Date = "Date";
    public static String expense_Merchant= "Merchant";

    private SQLiteDatabase db;
    public static String TAG = "MyDb";

    public DBHelper(Context context) {
        //create the database
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create the table
        String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+" ("+expense_id+" INTEGER PRIMARY KEY AUTOINCREMENT, "+expense_Select_Category+" TEXT, "+expense_Expense+" TEXT, "+expense_Date+" TEXT, "+expense_Merchant+" TEXT, "+expense_Note+" TEXT)";
        db.execSQL(CREATE_TABLE);
        Log.d("TAG","Reached");
        //populate dummy data

        /*db.execSQL("INSERT INTO expense (id, Select_Category, Expense, Date,  Merchant, Note) VALUES(1, '1', '1000', '2017-02-19', 'Testing', 'private purpose');");
        db.execSQL("INSERT INTO expense (id, Select_Category, Expense, Date,  Merchant, Note) VALUES(2, '0', '2000', '2017-02-19', 'Testing', 'private purpose');");
        db.execSQL("INSERT INTO expense (id, Select_Category, Expense, Date,  Merchant, Note) VALUES(3, '0', '3000', '2017-02-19', 'Testing', 'private purpose');");
        db.execSQL("INSERT INTO expense (id, Select_Category, Expense, Date,  Merchant, Note) VALUES(4, '0', '4000', '2017-02-19', 'Testing', 'private purpose');");
        db.execSQL("INSERT INTO expense (id, Select_Category, Expense, Date,  Merchant, Note) VALUES(5, '1', '5000', '2017-02-19', 'Testing', 'private purpose');");
        db.execSQL("INSERT INTO expense (id, Select_Category, Expense, Date,  Merchant, Note) VALUES(6, '1', '6000', '2017-02-19', 'Testing', 'private purpose');");
        db.execSQL("INSERT INTO expense (id, Select_Category, Expense, Date,  Merchant, Note) VALUES(7, '0', '7000', '2017-02-19', 'Testing', 'private purpose');");
*/




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //onUpgrade remove the existing table, and recreate and populate new data
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }
    public void insertData(Expense e)
    {
        db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(expense_Select_Category,e.getCategory());
        values.put(expense_Expense, e.getExpense());
        values.put(expense_Date, e.getDate());
        values.put(expense_Merchant, e.getMerchant());
        values.put(expense_Note, e.getNote());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void updateData(Expense e, int id)
    {
        db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(expense_Select_Category,e.getCategory());
        values.put(expense_Expense, e.getExpense());
        values.put(expense_Date, e.getDate());
        values.put(expense_Merchant, e.getMerchant());
        values.put(expense_Note, e.getNote());

        db.update(TABLE_NAME, values, expense_id+"="+Integer.toString(id), null);
        db.close();
    }

    public void deleteData(int id){
        db = getWritableDatabase();
        db.delete(TABLE_NAME, expense_id+"="+ Integer.toString(id), null);
        db.close();
    }




}