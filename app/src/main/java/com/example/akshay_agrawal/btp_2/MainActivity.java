package com.example.akshay_agrawal.btp_2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity  {

    SharedPreferences sharedpreferences ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedpreferences =getSharedPreferences("MyData1",MainActivity.this.MODE_PRIVATE);
        String check= sharedpreferences.getString("checkfirst","true");
        if( check.equals("true")){
            SharedPreferences.Editor editor=sharedpreferences.edit();
            editor.putString("checkfirst","true");
            editor.apply();
        }

        if(check.equals("true")){
            Intent intent= new Intent(this, SignUp.class);startActivity(intent);
        }
        else{
            Intent intent= new Intent(this, Login.class);
            startActivity(intent);

        }
    }

}
