package com.example.akshay_agrawal.btp_2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Password extends AppCompatActivity {
    private Button resetPass;
    private EditText pass1, pass2;
    SharedPreferences sharedpreferences ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        sharedpreferences =getSharedPreferences("MyData1",this.MODE_PRIVATE);
        resetPass = (Button)findViewById(R.id.button2);
        pass1 = (EditText)findViewById(R.id.editText3);
        pass2 = (EditText)findViewById(R.id.editText4);
        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_password();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void update_password(){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        if (pass1.getText().toString().equals(pass2.getText().toString()) && pass1.getText().toString().equals("") ){
            Toast.makeText(this,"Password can't be empty",Toast.LENGTH_SHORT).show();
            pass1.setText("");
            pass2.setText("");
        }
        else if (pass1.getText().toString().equals(pass2.getText().toString()) && pass1.getText().toString().length()<=6) {
            Toast.makeText(this,"Password should be greater than 6 characters",Toast.LENGTH_SHORT).show();
            pass1.setText("");
            pass2.setText("");
        }
        else if (!pass1.getText().toString().equals(pass2.getText().toString()) ) {
            Toast.makeText(this,"Passwords do not match",Toast.LENGTH_SHORT).show();
            pass1.setText("");
            pass2.setText("");
        }

        //editor.putString("user_name",user_name.getText().toString());
        else {
            editor.putString("PASSWORD",pass1.getText().toString());
            editor.apply();

            Intent intent = new Intent(Password.this,User_Interface.class);
            startActivity(intent);
            finish();
            Toast.makeText(this,"Password Successfully changed",Toast.LENGTH_SHORT).show();
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
