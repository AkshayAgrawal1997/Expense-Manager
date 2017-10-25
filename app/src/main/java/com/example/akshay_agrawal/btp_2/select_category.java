package com.example.akshay_agrawal.btp_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class select_category extends AppCompatActivity {
    ListView list;
    Integer[] imageId={R.drawable.food, R.drawable.drinks, R.drawable.education, R.drawable.entertainment, R.drawable.medical, R.drawable.music, R.drawable.shopping, R.drawable.sports, R.drawable.travel};
    //String[] mTestArray1= getResources().getStringArray(R.array.category_names);
    String [] mTestArray= {"Food","Drinks","Education","Entertainment","Medical","Music","Shopping","Sports","Travel"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_category);
        DisplayAdapter2 adapter = new DisplayAdapter2(select_category.this, mTestArray, imageId);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Toast.makeText(select_category.this, "You Clicked at " , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("id", position);
                //intent.putExtra("cat",mTestArray[position]);
                setResult(RESULT_OK, intent);
                finish();

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void onBackPressed()
    {   //Intent intent = new Intent(this, MainActivity.class);
        //startActivity(intent);
        Intent intent = new Intent();
        intent.putExtra("id", -1);
        //intent.putExtra("cat",mTestArray[position]);
        setResult(RESULT_OK, intent);
        finish();
        //finishActivity();
        //super.onBackPressed();
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