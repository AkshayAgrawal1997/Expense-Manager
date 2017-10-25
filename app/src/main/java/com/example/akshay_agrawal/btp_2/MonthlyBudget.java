package com.example.akshay_agrawal.btp_2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MonthlyBudget extends AppCompatActivity {
    EditText amount;
    String budget;

    SharedPreferences sharedpreferences ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_budget);
        sharedpreferences =getSharedPreferences("MyData1",this.MODE_PRIVATE);
        amount= (EditText)findViewById(R.id.editText6);
        if (sharedpreferences.contains("budget")){
            amount.setText(sharedpreferences.getString("budget",""));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void set_amount(View view){
        SharedPreferences.Editor editor=sharedpreferences.edit();
        budget=(amount.getText().toString());

        if (amount.getText().toString().equals("")){
            Toast.makeText(this,"Please enter amount greater than 0",Toast.LENGTH_SHORT).show();
        }else{
            editor.putString("budget",budget);
            editor.putBoolean("entered",true);
            editor.putBoolean("first_string",true);
            editor.apply();
            Toast.makeText(this,"Budget Successfully updated",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this, User_Interface.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, User_Interface.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
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
