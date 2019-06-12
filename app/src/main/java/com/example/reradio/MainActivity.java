package com.example.reradio;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.support.v7.app.ActionBar;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    GridView simpleGrid;
    private ActionBar toolbar;
    int logos[] = {R.mipmap.c1,R.mipmap.c2, R.mipmap.c3, R.mipmap.c4,
            R.mipmap.c5, R.mipmap.c6,R.mipmap.c7,R.mipmap.c8};
    String names[] = {"Chicago Bob’s Blues","Bulldog Country","Rampage","The Vintage Lounge","RDP","Spit Wyre","All Local Station","Music Box"};
    String links[] = {"http://17483.live.streamtheworld.com/SP_R2651227_SC",
            "http://17483.live.streamtheworld.com/SP_R2645155_SC",
            "http://17483.live.streamtheworld.com/SP_R2645155_SC",
            "http://17483.live.streamtheworld.com/SP_R2645155_SC",
            "http://17003.live.streamtheworld.com/SP_R2691875_SC",
            "http://14553.live.streamtheworld.com/SP_R2808508_SC",
            "http://17483.live.streamtheworld.com/SP_R2645155_SC",
            "http://17003.live.streamtheworld.com/SP_R2645109_SC"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("STATIONS");
        toolbar=getSupportActionBar();
        toolbar.setElevation(0);


        setContentView(R.layout.activity_main);
        simpleGrid = findViewById(R.id.simpleGridView); // init GridView
        // Create an object of CustomAdapter and set Adapter to GirdView
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), logos, names);
        simpleGrid.setAdapter(customAdapter);
        // implement setOnItemClickListener event on GridView
        simpleGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                 set an Intent to Another Activity
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("image", logos[position]); // put image data in Intent
                intent.putExtra("c_name",names[position]);
                intent.putExtra("c_link",links  [position]);
                startActivity(intent); // start Intent
            }
        });
    }
    public void setTitle(String title){
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView textView = new TextView(this);
        textView.setText(title);
        textView.setTextSize(20);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(getResources().getColor(R.color.black));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(textView);
    }
}