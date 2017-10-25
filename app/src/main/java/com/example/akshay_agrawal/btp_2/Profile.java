package com.example.akshay_agrawal.btp_2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import static android.R.id.input;

public class Profile extends AppCompatActivity {
    SharedPreferences myPrefrence;
    private  String imageEncoded;
    private static int RESULT_LOAD_IMG;
    String imgDecodableString;
    EditText user_name,user_email;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        RESULT_LOAD_IMG=1;
        user_name = (EditText)findViewById(R.id.editText2);
        user_email = (EditText)findViewById(R.id.editText5);
        myPrefrence = getSharedPreferences("MyData1",this.MODE_PRIVATE);
        imageView=(ImageView)findViewById(R.id.imageView3);
        if (myPrefrence.contains("user_name") && myPrefrence.contains("user_email")){
            user_name.setText(myPrefrence.getString("user_name",""));
            user_email.setText(myPrefrence.getString("user_email",""));
        }
        if(myPrefrence.contains("imagePreferance")){
            byte[] decodedByte = Base64.decode(myPrefrence.getString("imagePreferance",""), 0);
             imageView.setImageBitmap(BitmapFactory
                     .decodeByteArray(decodedByte, 0, decodedByte.length));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        //Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();

                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                try {

                    // Move to first row
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();
                    ImageView imgView = (ImageView) findViewById(R.id.imageView3);
                    // Set the Image in ImageView after decoding the String
                    imgView.setImageBitmap(BitmapFactory
                            .decodeFile(imgDecodableString));
                    save_image(BitmapFactory.decodeFile(imgDecodableString));
                    //Toast.makeText(getBaseContext(),filePathColumn[0],Toast.LENGTH_SHORT).show();
                }
                catch (NullPointerException e){
                    Toast.makeText(getBaseContext(),"Image may have been deleted",Toast.LENGTH_SHORT);
                }
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
                //save_image(BitmapFactory.decodeResource(getResources(),R.drawable.profile));
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }

    public void save_image(Bitmap yourbitmap){
        SharedPreferences.Editor editor = myPrefrence.edit();
        editor.putString("imagePreferance", encodeTobase64(yourbitmap));
        editor.commit();
    }
    public String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image_Log:", imageEncoded);
        return imageEncoded;
    }
/*
    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }*/

    public void update_user_info(View view){
        SharedPreferences.Editor editor = myPrefrence.edit();
        if (!user_name.getText().toString().equals("") && !user_email.getText().toString().equals("")){
            editor.putString("user_name",user_name.getText().toString());
            editor.putString("user_email",user_email.getText().toString());
            editor.apply();

            Intent intent = new Intent(Profile.this,User_Interface.class);
            startActivity(intent);
            finish();
        }
        //editor.putString("user_name",user_name.getText().toString());
        else {
            Toast.makeText(this,"Enter valid details",Toast.LENGTH_SHORT).show();
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
