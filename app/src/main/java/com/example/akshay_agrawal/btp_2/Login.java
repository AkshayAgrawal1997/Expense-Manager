package com.example.akshay_agrawal.btp_2;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    private Button loginbutton;
    private EditText loginmob, loginpass;
    private CheckBox saveLoginCheckBox;
    private boolean saveLogin;
    SharedPreferences sharedpreferences ;
    CheckBox showpassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedpreferences =getSharedPreferences("MyData1",this.MODE_PRIVATE);
        saveLogin = sharedpreferences.getBoolean("saveLogin", false);

        loginmob=(EditText) this.findViewById(R.id.editText3);
        loginpass=(EditText) this.findViewById(R.id.editText4);
        loginbutton= (Button) this.findViewById(R.id.button2);
        saveLoginCheckBox=(CheckBox)this.findViewById(R.id.RemPassword);
        if (saveLogin == true) {
            loginmob.setText(sharedpreferences.getString("MOBILE_NO",""));
            loginpass.setText(sharedpreferences.getString("PASSWORD",""));
            saveLoginCheckBox.setChecked(true);
        }
        showpassword=(CheckBox)findViewById(R.id.cbShowPwd);
        showpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // checkbox status is changed from uncheck to checked.
                if (!isChecked) {
                    // show password
                    loginpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    loginpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

    }

    public void login(View view){
        if(view.getId()==loginbutton.getId()){
            SharedPreferences.Editor editor=sharedpreferences.edit();
            String mobileno=loginmob.getText().toString();
            String pass=loginpass.getText().toString();
            String MOBILE_NO=sharedpreferences.getString("MOBILE_NO","9876543210");
            String PASSWORD=sharedpreferences.getString("PASSWORD","12345");
            if (saveLoginCheckBox.isChecked()) {editor.putBoolean("saveLogin", true);}
            else {editor.putBoolean("saveLogin", false);}
            editor.apply();
            if(!mobileno.equals(MOBILE_NO) || !pass.equals(PASSWORD)){
                loginmob.setText("");
                loginpass.setText("");
                if(!mobileno.equals(MOBILE_NO) && pass.equals(PASSWORD)){
                    Toast.makeText(this,"Wrong Mobile Number !!!",Toast.LENGTH_SHORT).show();
                }
                if(mobileno.equals(MOBILE_NO) && !pass.equals(PASSWORD)){
                    Toast.makeText(this,"Wrong Password !!!",Toast.LENGTH_SHORT).show();
                }
                if(!mobileno.equals(MOBILE_NO) && !pass.equals(PASSWORD)) {
                    Toast.makeText(this, "Wrong Mobile Number & Password !!!", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                //Toast.makeText(this, "Correct", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(Login.this,User_Interface.class);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog diaBox = Exit();
        diaBox.show();
    }

    public void reset(View view) {
        AlertDialog diaBox = AskOption();
        diaBox.show();
    }

    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)
                .setTitle("Format..")
                .setMessage("All your data will we wiped, do you want to continue?")
                .setIcon(R.drawable.update)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent intent  =new Intent(Login.this, SignUp.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return myQuittingDialogBox;

    }
    private android.support.v7.app.AlertDialog Exit()
    {
        android.support.v7.app.AlertDialog myQuittingDialogBox =new android.support.v7.app.AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setIcon(R.drawable.close)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return myQuittingDialogBox;

    }
}
