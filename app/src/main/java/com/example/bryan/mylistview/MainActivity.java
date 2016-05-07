package com.example.bryan.mylistview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.relativeLayout);
        MyView myView = new MyView(this);
        relativeLayout.addView(myView);

    }
}
