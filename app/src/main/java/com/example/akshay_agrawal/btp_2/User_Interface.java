package com.example.akshay_agrawal.btp_2;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.R.id.input;

public class User_Interface extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static ImageButton dp;
    private  String imageEncoded;
    TextView u_name,u_email;
    public Boolean entered = Boolean.FALSE;
    SharedPreferences sharedpreferences;
    private DBHelper mHelper;
    private SQLiteDatabase dataBase;
    String [] category={"Food","Drinks","Education","Entertainment","Medical","Music","Shopping","Sports","Travel"};
    PieChart mChart;
    // we're going to display pie chart for school attendance
    private int[] yValues = { 0, 0};
    private String[] xValues = { "Remaining|Underspent", "Spent|Overspent"};
    private int expense_plan=0;
    private int expense_spent=0;
    private Calendar cc = Calendar.getInstance();
    // colors for different sections in pieChart
    public static final int[] MY_COLORS = {
            Color.rgb(121, 206, 24), Color.rgb(255, 168, 197), Color.rgb(231, 237, 163),

    };

    //variables to hold staff records
    private ArrayList<String> expense_category = new ArrayList<String>();
    private ArrayList<String> expense_expense = new ArrayList<String>();
    private ArrayList<String> expense_date = new ArrayList<String>();
    private ArrayList<String> expense_note = new ArrayList<String>();
    private ArrayList<String> expense_merchant = new ArrayList<String>();
    private ArrayList<Integer> expense_id = new ArrayList<Integer>();

    private ListView userList;
    private AlertDialog.Builder build;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__interface);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        dp = (ImageButton)findViewById(R.id.user_profile_photo);
        setSupportActionBar(toolbar);
        sharedpreferences =getSharedPreferences("MyData1",this.MODE_PRIVATE);
        String plan;


        plan = sharedpreferences.getString("budget", "0");
        //Toast.makeText(User_Interface.this,plan,Toast.LENGTH_LONG).show();
        expense_plan=Integer.parseInt(plan);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        //.setAction("Action", null).show();
                Intent intent  = new Intent(User_Interface.this, add_expense.class);
                intent.putExtra("Parent","user_interface");
                startActivity(intent);
                finish();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        u_name = (TextView)navigationView.getHeaderView(0).findViewById(R.id.text_name);
        u_email = (TextView)navigationView.getHeaderView(0).findViewById(R.id.text_email);
        sharedpreferences =getSharedPreferences("MyData1",this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        Boolean entered = sharedpreferences.getBoolean("entered",false);
        Boolean first_date = sharedpreferences.getBoolean("first_date",false);

        Boolean isAppInstalled = sharedpreferences.getBoolean("isAppInstalled",false);
        if(isAppInstalled==false) {
            Intent shortcutIntent = new Intent(getApplicationContext(),User_Interface.class);
            shortcutIntent.setAction(Intent.ACTION_MAIN);
            Intent intent = new Intent();
            intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
            intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "Expense Manager");
            intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource
                    .fromContext(getApplicationContext(), R.drawable.app_icon));
            intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
            getApplicationContext().sendBroadcast(intent);

            //Make preference true
            editor.putBoolean("isAppInstalled", true);
            editor.commit();
        }

        ImageButton imageButton = (ImageButton)navigationView.getHeaderView(0).findViewById(R.id.user_profile_photo);
        if (sharedpreferences.contains("imagePreferance")){
            String dp = sharedpreferences.getString("imagePreferance","");
            Log.d("Image :----------",dp);

            //Toast.makeText(this,"kuch hai",Toast.LENGTH_SHORT).show();
            byte[] decodedByte = Base64.decode(dp, 0);
            Bitmap profile_pic= BitmapFactory
                    .decodeByteArray(decodedByte, 0, decodedByte.length);
            imageButton.setImageBitmap(profile_pic);
        }

        if (sharedpreferences.contains("user_name")){
           // Toast.makeText(this,sharedpreferences.getString("user_name",""),Toast.LENGTH_SHORT).show();
            u_name.setText(sharedpreferences.getString("user_name",""));
        }
        if (sharedpreferences.contains("user_email")){
            //Toast.makeText(this,sharedpreferences.getString("user_email",""),Toast.LENGTH_SHORT).show();
            u_email.setText(sharedpreferences.getString("user_email",""));
        }

        int date=cc.get(Calendar.DATE);
        //Toast.makeText(User_Interface.this,String.valueOf(date),Toast.LENGTH_SHORT).show();
        if (date==1 && !first_date){
            editor.putBoolean("entered",false);

        }
        if(date!=1){
            editor.putBoolean("first_date",false);
        }
        if(!entered){
            Intent set_amount = new Intent(this,MonthlyBudget.class);
            startActivity(set_amount);
            finish();
        }

        //dp.setImageBitmap(decodeBase64(getIntent().getExtras().getString("dp")));

        /*---------------------------------------------------------------------*/

        userList = (ListView) findViewById(R.id.List);
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.header, userList, false);
        userList.addHeaderView(header, null, false);

        mHelper = new DBHelper(this);
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(),"You Clicked "+names[i],Toast.LENGTH_SHORT).show();
                if(i==4){
                    Intent intent = new Intent(User_Interface.this, view_expense.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent = new Intent(User_Interface.this, edit_delete_expense.class);
                    Expense e=new Expense(expense_category.get(i-1), Integer.parseInt(expense_expense.get(i-1)), expense_date.get(i-1), expense_note.get(i-1), expense_merchant.get(i-1));
                    intent.putExtra("e",e);
                    intent.putExtra("id",expense_id.get(i-1));
                    startActivity(intent);
                    finish();
                }

            }
        });




        mChart = (PieChart) findViewById(R.id.chart1);

        //   mChart.setUsePercentValues(true);
        mChart.setDescription("");

        //mChart.setRotationEnabled(true);



        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                // display msg when value selected
                if (e == null)
                    return;

                Toast.makeText(User_Interface.this,
                        xValues[e.getXIndex()] + " is " + e.getVal() + "", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected() {

            }
        });

        // setting sample Data for Pie Chart
        setDataForPieChart();

    }
    /*public String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image_Log:", imageEncoded);
        return imageEncoded;
    }*/

    private void displayData() {
        dataBase = mHelper.getWritableDatabase();
        //the SQL command to fetched all records from the table
        Cursor mCursor = dataBase.rawQuery("SELECT * FROM "
                + DBHelper.TABLE_NAME + " ORDER BY "+ DBHelper.expense_Date + " DESC", null);

        //reset variables
        expense_category.clear();
        expense_expense.clear();
        expense_date.clear();
        expense_merchant.clear();
        expense_note.clear();
        expense_id.clear();
        int count=0;

        //fetch each record
        if (mCursor.moveToFirst()) {
            do {
                if(count<3){
                    //get data from field
                    expense_category.add((mCursor.getString(mCursor.getColumnIndex(DBHelper.expense_Select_Category))));
                    expense_expense.add(mCursor.getString(mCursor.getColumnIndex(DBHelper.expense_Expense)));
                    expense_date.add(mCursor.getString(mCursor.getColumnIndex(DBHelper.expense_Date)));
                    expense_note.add(mCursor.getString(mCursor.getColumnIndex(DBHelper.expense_Note)));
                    expense_merchant.add(mCursor.getString(mCursor.getColumnIndex(DBHelper.expense_Merchant)));
                    expense_id.add(mCursor.getInt(mCursor.getColumnIndex(DBHelper.expense_id)));
                }
                else if(count==3){

                }
                else{
                    break;
                }
                count=count+1;
            } while (mCursor.moveToNext());
            //do above till data exhausted
            if (count ==4) {
                expense_category.add("      See All");
                expense_expense.add("");
                expense_date.add("");
                expense_note.add("");
                expense_merchant.add("");
                expense_id.add(0);
            }
        }

        DisplayAdapter disadpt = new DisplayAdapter(User_Interface.this, expense_category, expense_expense, expense_date, expense_note, expense_merchant);
        userList.setAdapter(disadpt);
        mCursor.close();
    }

    public void update(View view){
        Intent intent  =new Intent(User_Interface.this, Profile.class);
        startActivity(intent);
        finish();
        //Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
    }


    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);

    }


    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void onResume() {
        //refresh data for screen is invoked/displayed
        displayData();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog diaBox = AskOption();
            diaBox.show();
            //Toast.makeText(this,"Back Pressed",Toast.LENGTH_SHORT).show();
            /*finish();*/
        }
    }

    private android.support.v7.app.AlertDialog AskOption()
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
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user__interface, menu);
        return true;
    }
*//*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            case R.id.nav_aboutUs:
            default:
                return super.onOptionsItemSelected(item);
        }
        //noinspection SimplifiableIfStatement

    }
*/
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_aboutUs) {
            Intent next = new Intent(this,AboutUs.class);
            startActivity(next);
        } else if (id == R.id.nav_generateReport) {
            Intent next = new Intent(this,generate_report.class);
            startActivity(next);

        } else if (id == R.id.nav_update){
            Intent next = new Intent(this,MonthlyBudget.class);
            startActivity(next);
            finish();
        }else if (id == R.id.nav_change_pass){
            Intent next = new Intent(this,Password.class);
            startActivity(next);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setDataForPieChart() {
        dataBase = mHelper.getWritableDatabase();
        /*Cursor mCursor = dataBase.rawQuery("SELECT * FROM "
                + DBHelper.TABLE_NAME + " ORDER BY "+ DBHelper.expense_id+ " DESC", null);*/
        /*Cursor mCursor = dataBase.rawQuery("SELECT * FROM "
                + DBHelper.TABLE_NAME + " WHERE "+  DBHelper.expense_Date+ " BETWEEEN  STR_TO_DATE('1-2-2017', '%d-%m-%Y') AND STR_TO_DATE('28-2-2017', '%d-%m-%Y')" , null);  */

        int mMonth = cc.get(Calendar.MONTH);
        String m=(mMonth<=9?"0"+String.valueOf(mMonth):String.valueOf(mMonth));
        //Toast.makeText(User_Interface.this,String.valueOf(mMonth),Toast.LENGTH_LONG).show();
        Cursor mCursor = dataBase.rawQuery("SELECT * FROM "
                + DBHelper.TABLE_NAME + " WHERE strftime('%Y',Date) = strftime('%Y',datetime('now','localtime')) AND strftime('%m',Date) = strftime('%m',datetime('now','localtime')) "  , null);
        if(mCursor.moveToFirst()){
            do{
                expense_spent+=Integer.parseInt(mCursor.getString(mCursor.getColumnIndex(DBHelper.expense_Expense)));
            }while (mCursor.moveToNext());
        }
        if(expense_plan-expense_spent>=0){
            yValues[0]=expense_plan-expense_spent;
            yValues[1]=expense_spent;
            xValues[0]="Remaining";
            xValues[1]="Spent";
        }
        else{
            yValues[0]=expense_spent-expense_plan;
            yValues[1]=expense_plan;
            xValues[0]="Overspent";
            xValues[1]="Underspent";
        }

        int days = cc.get(Calendar.DATE);
        int t_days=30;
        if (cc.get(Calendar.MONTH)==Calendar.JANUARY){
            t_days=31;
        }
        else if ((cc.get(Calendar.MONTH)==Calendar.FEBRUARY)&& cc.get(Calendar.YEAR)%4==0){
            t_days=29;
        }
        else if ((cc.get(Calendar.MONTH)==Calendar.FEBRUARY)&& cc.get(Calendar.YEAR)%4!=0){
            t_days=28;
        }
        else if (cc.get(Calendar.MONTH)==Calendar.MARCH){
            t_days=31;
        }
        else if (cc.get(Calendar.MONTH)==Calendar.APRIL){
            t_days=30;
        }
        else if (cc.get(Calendar.MONTH)==Calendar.MAY){
            t_days=31;
        }
        else if (cc.get(Calendar.MONTH)==Calendar.JUNE){
            t_days=30;
        }
        else if (cc.get(Calendar.MONTH)==Calendar.JULY){
            t_days=31;
        }
        else if (cc.get(Calendar.MONTH)==Calendar.AUGUST){
            t_days=31;
        }
        else if (cc.get(Calendar.MONTH)==Calendar.SEPTEMBER){
            t_days=30;
        }
        else if (cc.get(Calendar.MONTH)==Calendar.OCTOBER){
            t_days=31;
        }
        else if (cc.get(Calendar.MONTH)==Calendar.NOVEMBER){
            t_days=30;
        }
        else if (cc.get(Calendar.MONTH)==Calendar.DECEMBER){
            t_days=31;
        }
        //Toast.makeText(this,"Month: "+t_days+" Day: "+days+"",Toast.LENGTH_SHORT).show();

        days=t_days-days+1;
        mChart.setCenterText(""+xValues[0]+"\n INR: "+yValues[0]+"\n "+days+" days left ");
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < yValues.length; i++)
            yVals1.add(new Entry(yValues[i], i));

        ArrayList<String> xVals = new ArrayList<String>();


        for (int i = 0; i < xValues.length; i++)
            xVals.add(xValues[i]);


        // create pieDataSet
        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        // adding colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        // Added My Own colors
        for (int c : MY_COLORS)
            colors.add(c);


        dataSet.setColors(colors);

        //  create pie data object and set xValues and yValues and set it to the pieChart
        PieData data = new PieData(xVals, dataSet);
        //   data.setValueFormatter(new DefaultValueFormatter());
        //   data.setValueFormatter(new PercentFormatter());

        data.setValueFormatter(new MyValueFormatter());
        data.setValueTextSize(0f);
        data.setValueTextColor(Color.BLACK);

        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        // refresh/update pie chart
        mChart.invalidate();

        // animate piechart
        mChart.animateXY(1400, 1400);
        //mChart.setBackgroundColor(Color.GREEN);


        // Legends to show on bottom of the graph
        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);
    }


    public class MyValueFormatter implements ValueFormatter {

        private DecimalFormat mFormat;

        public MyValueFormatter() {
            mFormat = new DecimalFormat("###,###,##0"); // use one decimal if needed
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            // write your logic here
            return mFormat.format(value) + ""; // e.g. append a dollar-sign
        }
    }
}
