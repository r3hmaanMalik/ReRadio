package com.example.reradio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
    Context context;
    int logos[];
    String  names[];
    LayoutInflater inflter;
    public CustomAdapter(Context applicationContext, int[] logos , String[] names) {
        this.context = applicationContext;
        this.logos = logos;
        this.names = names;
        inflter = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
        return logos.length;
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.activity_gridview, null); // inflate the layout
        ImageView icon = view.findViewById(R.id.icon);
        TextView tv = view.findViewById(R.id.tv);// get the reference of ImageView

        icon.setImageResource(logos[i]); // set logo images
        tv.setText(names[i]);

        return view;

    }
}