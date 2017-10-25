package com.example.akshay_agrawal.btp_2;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class view_expense extends AppCompatActivity implements OnItemClickListener{

    ListView userList;
    //ArrayAdapter<StringBuffer> adapter;
    private DBHelper mHelper;
    private SQLiteDatabase dataBase;
    //variables to hold staff records
    private ArrayList<String> expense_category = new ArrayList<String>();
    private ArrayList<String> expense_expense = new ArrayList<String>();
    private ArrayList<String> expense_date = new ArrayList<String>();
    private ArrayList<String> expense_note = new ArrayList<String>();
    private ArrayList<String> expense_merchant = new ArrayList<String>();
    private ArrayList<Integer> expense_id = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expense);
        userList=(ListView)findViewById(R.id.view_expense_list);

        mHelper = new DBHelper(this);
        displayData();
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(),"You Clicked "+names[i],Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view_expense.this, edit_delete_expense.class);
                Expense e=new Expense(expense_category.get(i), Integer.parseInt(expense_expense.get(i)), expense_date.get(i), expense_note.get(i), expense_merchant.get(i));
                intent.putExtra("e",e);
                intent.putExtra("id",expense_id.get(i));
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //.setAction("Action", null).show();
                Intent intent  = new Intent(view_expense.this, add_expense.class);
                intent.putExtra("Parent","see_all");
                startActivity(intent);
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub

    }

    //for return to main Activity
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, User_Interface.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    private void displayData() {
        dataBase = mHelper.getWritableDatabase();
        //the SQL command to fetched all records from the table
        Cursor mCursor = dataBase.rawQuery("SELECT * FROM "
                + DBHelper.TABLE_NAME + " ORDER BY "+ DBHelper.expense_Date + " DESC", null);

        //reset variables
        expense_category.clear();
        expense_expense.clear();
        expense_date.clear();
        expense_merchant.clear();
        expense_note.clear();
        expense_id.clear();


        //fetch each record
        if (mCursor.moveToFirst()) {
            do {
                //get data from field
                expense_category.add(mCursor.getString(mCursor.getColumnIndex(DBHelper.expense_Select_Category)));
                expense_expense.add(mCursor.getString(mCursor.getColumnIndex(DBHelper.expense_Expense)));
                expense_date.add(mCursor.getString(mCursor.getColumnIndex(DBHelper.expense_Date)));
                expense_note.add(mCursor.getString(mCursor.getColumnIndex(DBHelper.expense_Note)));
                expense_merchant.add(mCursor.getString(mCursor.getColumnIndex(DBHelper.expense_Merchant)));
                expense_id.add(mCursor.getInt(mCursor.getColumnIndex(DBHelper.expense_id)));


            } while (mCursor.moveToNext());
            //do above till data exhausted

        }
        LayoutInflater inflater = getLayoutInflater();

        //display to screen
        DisplayAdapter disadpt = new DisplayAdapter(view_expense.this, expense_category, expense_expense, expense_date, expense_note, expense_merchant);
        userList.setAdapter(disadpt);
        mCursor.close();
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
