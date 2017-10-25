package com.example.akshay_agrawal.btp_2;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayAdapter2 extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] mTestArray;
    private final Integer[] imageId;
    public DisplayAdapter2(Activity context,
                           String[] mTestArray, Integer[] imageId) {
        super(context, R.layout.activity_display_adapter2, mTestArray);
        this.context = context;
        this.mTestArray = mTestArray;
        this.imageId = imageId;
        //this.imageId = imageId;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.activity_display_adapter2, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(mTestArray[position]);

        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}