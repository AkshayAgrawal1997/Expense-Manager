package com.example.akshay_agrawal.btp_2;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class add_expense extends AppCompatActivity implements View.OnClickListener{

    EditText amount_field, merchant_field, note_field;
    TextView category_field,date_field;
    private int mYear, mMonth, mDay;
    String [] category={"Food","Drinks","Education","Entertainment","Medical","Music","Shopping","Sports","Travel"};
    int Value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        amount_field= (EditText)findViewById(R.id.amount_field);
        merchant_field= (EditText)findViewById(R.id.merchant_field);
        category_field= (TextView) findViewById(R.id.category_field);
        date_field= (TextView) findViewById(R.id.date_field);
        note_field= (EditText)findViewById(R.id.note_field);

        date_field.setOnClickListener(this);

        category_field.setOnClickListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {

        if (v == date_field) {
            choose_date();
        }

        else if(v == category_field) {
            choose_category();
        }
    }

    public void choose_date(){
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        monthOfYear+=1;
                        date_field.setText(year + "-" + (monthOfYear<=9?"0"+String.valueOf(monthOfYear):String.valueOf(monthOfYear)) + "-" + (dayOfMonth<=9?"0"+String.valueOf(dayOfMonth):String.valueOf(dayOfMonth)));

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();

    }
    public void choose_category(){
        Intent intent = new Intent(add_expense.this, select_category.class);
        startActivityForResult(intent, 1);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Value = data.getIntExtra("id", -1);
        if (Value + 1 > 0) {
            //category_field.setText(String.valueOf(Value));
            category_field.setText(category[Value]);
            //Define array at the /p, and access category by indexing.........
        }
    }
    @Override
    public void onBackPressed()
    {
        if(getIntent().getExtras().getString("Parent").equals("see_all")){
            Intent intent = new Intent(this, view_expense.class);
            startActivity(intent);
            finish();
        }
        else {
            Intent intent = new Intent(this, User_Interface.class);
            startActivity(intent);
            finish();
        }


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.display_list, menu);
        //return true;
        getMenuInflater().inflate(R.menu.display_list, menu);
        return true;
    }

    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_confirm:
                save();
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void save(){
        String amount=amount_field.getText().toString();
        String merchant=merchant_field.getText().toString();
        String date=date_field.getText().toString();
        String note=note_field.getText().toString();
        //Toast.makeText(this, "Menu Item 1 selected", Toast.LENGTH_SHORT).show();
        if (amount_field.getText().toString().equals("") || merchant_field.getText().toString().equals("") || category_field.getText().toString().equals("") || date_field.getText().toString().equals("")){
            Toast.makeText(this, "Please enter the details ", Toast.LENGTH_LONG).show();
        }
        else if (amount_field.getText().toString().equals("0")){
            Toast.makeText(this, "Please enter non-zero amount ", Toast.LENGTH_LONG).show();
        }
        else {
            Expense e = new Expense(Integer.toString(Value), Integer.parseInt(amount), date, note , merchant);
            DBHelper helper = new DBHelper(this);
            helper.insertData(e);

            Intent intent = new Intent(this, User_Interface.class);
            startActivity(intent);
            finish();
        }
    }
}
