package com.example.akshay_agrawal.btp_2;

/**
 * Created by Akshay_agrawal on 01-03-2017.
 */

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayAdapter extends BaseAdapter {
    private Context mContext;
    String [] category={"Food","Drinks","Education","Entertainment","Medical","Music","Shopping","Sports","Travel"};
    Integer [] imageId={R.drawable.food, R.drawable.drinks, R.drawable.education, R.drawable.entertainment, R.drawable.medical, R.drawable.music, R.drawable.shopping, R.drawable.sports, R.drawable.travel};
    //list fields to be displayed
    private ArrayList<String> expense_category;
    private ArrayList<String> expense_expense;
    private ArrayList<String> expense_date;
    private ArrayList<String> expense_note;
    private ArrayList<String> expense_merchant;


    public DisplayAdapter(Context c, ArrayList<String> expense_category, ArrayList<String> expense_expense, ArrayList<String> expense_date, ArrayList<String> expense_note, ArrayList<String> expense_merchant) {
        this.mContext = c;
        //transfer content from database to temporary memory
        this.expense_category = expense_category;
        this.expense_expense = expense_expense;
        this.expense_date = expense_date;
        this.expense_note= expense_note;
        this.expense_merchant= expense_merchant;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return expense_expense.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public View getView(int pos, View child, ViewGroup parent) {
        Holder mHolder;
        LayoutInflater layoutInflater;
        if (child == null) {
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.lc, null);
            mHolder = new Holder();

            //link to TextView
            mHolder.merchant = (TextView) child.findViewById(R.id.textView);
            mHolder.category = (TextView) child.findViewById(R.id.textView2);
            mHolder.note = (TextView) child.findViewById(R.id.textView3);
            mHolder.expense = (TextView) child.findViewById(R.id.textView5);
            mHolder.date = (TextView) child.findViewById(R.id.textView6);
            mHolder.image= (ImageView) child.findViewById(R.id.imageView);
            child.setTag(mHolder);
        } else {
            mHolder = (Holder) child.getTag();
        }
        //transfer to TextView in screen
        if(expense_category.get(pos) != ("      See All")){
            Integer cat=Integer.valueOf(expense_category.get(pos));
            mHolder.category.setText(category[cat]);
            mHolder.image.setImageResource(imageId[cat]);
        }
        else{
            mHolder.category.setText(expense_category.get(pos));
            mHolder.image.setImageResource(0);

        }


        //mHolder.category.setText(expense_category.get(pos));
        mHolder.expense.setText(expense_expense.get(pos));
        mHolder.date.setText(expense_date.get(pos));
        mHolder.note.setText(expense_note.get(pos));
        mHolder.merchant.setText(expense_merchant.get(pos));



        return child;
    }

    public class Holder {
        TextView category;
        TextView expense;
        TextView date;
        TextView note;
        TextView merchant;
        ImageView image;
    }

}
