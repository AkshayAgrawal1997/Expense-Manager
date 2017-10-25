package com.example.akshay_agrawal.btp_2;


import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class generate_report extends AppCompatActivity {

    private DBHelper mHelper;
    private SQLiteDatabase dataBase;
    SharedPreferences sharedpreferences;
    private int[] category_sum= {0,0,0,0,0,0,0,0,0};
    String [] category={"Food","Drinks","Education","Entertainment","Medical","Music","Shopping","Sports","Travel"};
    Integer [] imageId={R.drawable.food, R.drawable.drinks, R.drawable.education, R.drawable.entertainment, R.drawable.medical, R.drawable.music, R.drawable.shopping, R.drawable.sports, R.drawable.travel};
    private int sum=0, expense_plan=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_report);
        generate_report();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void generate_report(){
        TextView amount,category1,category2,category3,category_sum1,category_sum2,category_sum3,plannedamount;
        ImageView image1,image2,image3;
        int index1 = Integer.MIN_VALUE;
        int index2 = Integer.MIN_VALUE;
        int index3 = Integer.MIN_VALUE;
        int max1 = Integer.MIN_VALUE;
        int max2 = Integer.MIN_VALUE;
        int max3 = Integer.MIN_VALUE;
        mHelper = new DBHelper(this);
        String plan;
        sharedpreferences =getSharedPreferences("MyData1",this.MODE_PRIVATE);
        plan = sharedpreferences.getString("budget", "0");
        //Toast.makeText(User_Interface.this,plan,Toast.LENGTH_LONG).show();
        expense_plan=Integer.parseInt(plan);
        dataBase = mHelper.getWritableDatabase();
        /*Cursor mCursor = dataBase.rawQuery("SELECT * FROM "
                + DBHelper.TABLE_NAME + " ORDER BY "+ DBHelper.expense_Date + " DESC", null);*/
        Cursor mCursor = dataBase.rawQuery("SELECT * FROM "
                + DBHelper.TABLE_NAME + " WHERE strftime('%Y',Date) = strftime('%Y',date('now')) AND strftime('%m',Date) = strftime('%m',date('now'))"  , null);
        if(mCursor.moveToFirst()){
            do{
                sum+=Integer.parseInt(mCursor.getString(mCursor.getColumnIndex(DBHelper.expense_Expense)));
                category_sum[Integer.parseInt(mCursor.getString(mCursor.getColumnIndex(DBHelper.expense_Select_Category)))]+=Integer.parseInt(mCursor.getString(mCursor.getColumnIndex(DBHelper.expense_Expense)));
            }while (mCursor.moveToNext());
        }

        for (int i = 0; i < category_sum.length; i++)
        {
            if (category_sum[i] > max1)
            {
                max3 = max2; max2 = max1; max1 = category_sum[i];
                index3 = index2; index2 = index1; index1 = i;
            }
            else if (category_sum[i] > max2)
            {
                max3 = max2; max2 = category_sum[i];
                index3=index2; index2=i;
            }
            else if (category_sum[i] > max3)
            {
                max3 = category_sum[i];
                index3=i;
            }
        }
        amount= (TextView) this.findViewById(R.id.amount);
        plannedamount= (TextView) this.findViewById(R.id.plannedamount);
        image1= (ImageView) findViewById(R.id.image1);
        image2= (ImageView) findViewById(R.id.image2);
        image3= (ImageView) findViewById(R.id.image3);
        category1= (TextView) this.findViewById(R.id.category1);
        category2= (TextView) findViewById(R.id.category2);
        category3= (TextView) findViewById(R.id.category3);
        category_sum1= (TextView) this.findViewById(R.id.category_sum1);
        category_sum2= (TextView) findViewById(R.id.category_sum2);
        category_sum3= (TextView) findViewById(R.id.category_sum3);
        amount.setText(Integer.toString(sum));
        plannedamount.setText(Integer.toString(expense_plan));
        image1.setImageResource(imageId[index1]);
        category1.setText(category[index1]);
        category_sum1.setText(Integer.toString(max1));
        if(max2>0){
            image2.setImageResource(imageId[index2]);
            category2.setText(category[index2]);
            category_sum2.setText(Integer.toString(max2));
        }
        else{
            image2.setImageResource(0);
            category2.setText("");
            category_sum2.setText("");
        }
        if(max3>0){
            image3.setImageResource(imageId[index3]);
            category3.setText(category[index3]);
            category_sum3.setText(Integer.toString(max3));
        }
        else{
            image3.setImageResource(0);
            category3.setText("");
            category_sum3.setText("");
        }

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


