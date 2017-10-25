package com.example.akshay_agrawal.btp_2;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SignUp extends AppCompatActivity{
    Button signupbutton;
    EditText signupmob, signuppass,signemail;
    SharedPreferences sharedpreferences ;
    CheckBox showpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedpreferences =getSharedPreferences("MyData1",this.MODE_PRIVATE);

        setContentView(R.layout.activity_sign_up);
        signupmob=(EditText) this.findViewById(R.id.editText3);
        signuppass=(EditText) this.findViewById(R.id.editText4);
        signemail = (EditText)findViewById(R.id.editText);
        signupbutton= (Button) this.findViewById(R.id.button2);
        showpassword=(CheckBox)findViewById(R.id.cbShowPwd);
        showpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // checkbox status is changed from uncheck to checked.
                if (!isChecked) {
                    // show password
                    signuppass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    signuppass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
        signupmob.requestFocus();

    }



    public void signup(View view){
        if(view.getId()==signupbutton.getId()){
            String mobileno=signupmob.getText().toString();
            if (mobileno.length()==10){
                String pass=signuppass.getText().toString();
                String email=signemail.getText().toString();
                SharedPreferences.Editor editor=sharedpreferences.edit();
                editor.putString("MOBILE_NO",mobileno);
                editor.putString("PASSWORD",pass);
                editor.putString("user_email",email);

                editor.putString("checkfirst","false");
                editor.apply();
                //Toast.makeText(SignUp.this, "Correct", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(SignUp.this,User_Interface.class);
                startActivity(intent);
                finish();
            }
            else {
                signupmob.setText("");
                signuppass.setText("");
                signemail.setText("");
                Toast.makeText(this,"enter valid mobile number",Toast.LENGTH_SHORT).show();
            }

        }
    }
    @Override
    public void onBackPressed() {
        //finish();
        AlertDialog diaBox = AskOption();
        diaBox.show();
    }
    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)
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
