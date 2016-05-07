package com.example.bryan.mylistview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by Bryan on 5/7/2016.
 */
public class MyView extends ViewGroup {

    private ScrollView scrollView;
    private LinearLayout linearLayout;
    private ImageView imageView;

    private Context context;

    public MyView(Context context) {
        super(context);
        this.context = context;

        linearLayout = new LinearLayout(this.context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        for (int i = 0; i < 5; i++) {
            imageView = new ImageView(this.context);
            imageView.setImageResource(R.drawable.star_checkbox_unchecked1);
            linearLayout.addView(imageView);
        }

        scrollView = new ScrollView(this.context);
        scrollView.addView(linearLayout);
        scrollView.layout(0,0,linearLayout.getMeasuredWidth(), linearLayout.getMeasuredHeight());

       }//MyView()constructor

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            //View child = getChildAt(i);
            getChildAt(i).layout(l, t, r, b);
        //      getChildAt(i).layout((int)child.getX(), (int)(child.getY()
            // + child.getMeasuredHeight()), (int) (child.getX()
            // + child.getMeasuredWidth()), (int)child.getY());
        }
    }//onLayout()
}//end MyView() class
