package com.example.akshay_agrawal.btp_2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.hardware.usb.UsbRequest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class edit_delete_expense extends AppCompatActivity implements View.OnClickListener{

    EditText amount_field, merchant_field, note_field;
    TextView category_field,date_field;
    private int mYear, mMonth, mDay;
    String [] category={"Food","Drinks","Education","Entertainment","Medical","Music","Shopping","Sports","Travel"};
    int Value;
    Expense e;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete_expense);
        amount_field= (EditText)findViewById(R.id.amount_field);
        merchant_field= (EditText)findViewById(R.id.merchant_field);
        category_field= (TextView) findViewById(R.id.category_field);
        date_field= (TextView) findViewById(R.id.date_field);
        note_field= (EditText)findViewById(R.id.note_field);
        date_field.setOnClickListener(this);
        category_field.setOnClickListener(this);
        e= (Expense) getIntent().getSerializableExtra("e");
        id= getIntent().getIntExtra("id",0);
        autofilldetails();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void autofilldetails(){
        amount_field.setText(Integer.toString(e.getExpense()));
        merchant_field.setText(e.getMerchant());
        date_field.setText(e.getDate());
        category_field.setText(category[Integer.valueOf(e.getCategory())]);
        note_field.setText(e.getNote());
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
        Intent intent = new Intent(edit_delete_expense.this, select_category.class);
        startActivityForResult(intent, 1);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Value = data.getIntExtra("id", -1);
        if (Value + 1 > 0) {
            //category_field.setText(String.valueOf(Value));
            category_field.setText(category[Value]);
            //Define array at the top, and access category by indexing.........
        }
    }

    public void onBackPressed()
    {
        Intent intent = new Intent(this, User_Interface.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.display_list2, menu);
        return true;
    }

    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                update();
            case R.id.action_delete:
                delete();
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }
    public void update(){
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
        else{
            Expense e = new Expense(Integer.toString(Value), Integer.parseInt(amount), date, note, merchant);
            DBHelper helper = new DBHelper(this);
            helper.updateData(e,id);
            Intent intent = new Intent(this, User_Interface.class);
            startActivity(intent);
            finish();
        }
    }
    public void delete(){
        DBHelper helper = new DBHelper(this);

        helper.deleteData(id);
        Intent intent = new Intent(this, User_Interface.class);
        startActivity(intent);
        finish();
    }
}