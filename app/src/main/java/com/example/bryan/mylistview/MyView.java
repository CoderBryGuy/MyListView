package com.example.bryan.mylistview;

import android.content.Context;
import android.graphics.Point;
import android.nfc.Tag;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;

/**
 * Created by Bryan on 5/7/2016.
 */
public class MyView extends ViewGroup {

    //private ScrollView scrollView;
    private LinearLayout linearLayout;
    private ImageView imageView;

    private Context context;

    private int deviceWidth;
    private final static int STAR_COUNT = 5;
    //private ArrayList<ImageView> starList;
    private final ImageView[] startList = new ImageView[STAR_COUNT];

    public MyView(Context context) {
        super(context);
        this.context = context;
        init(this.context);

        linearLayout = new LinearLayout(this.context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        for (int i = 0; i < STAR_COUNT; i++) {
            imageView = new ImageView(this.context);
            imageView.setImageResource(R.drawable.star_checkbox_unchecked1);
            imageView.setTag(i+1);
           // Tag myTag = (Tag)imageView.getTag();
            startList[i] = imageView;
            MyOnClickListener myOnClickListener = new MyOnClickListener(this.context, i+1 , startList, linearLayout);
            imageView.setOnClickListener(myOnClickListener);
            linearLayout.addView(imageView);
        }
        this.addView(linearLayout);



        //scrollView = new ScrollView(this.context);
       // scrollView.addView(linearLayout);
        //scrollView.layout(0,0,linearLayout.getMeasuredWidth(), linearLayout.getMeasuredHeight());
        //this.addView(scrollView);
       }//MyView()constructor

    private void init(Context context) {
        final Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point deviceDisplay = new Point();
        display.getSize(deviceDisplay);
        deviceWidth = deviceDisplay.x;
    }
    public static class MyOnClickListener implements View.OnClickListener{

        private Context context;
        private int tagKey;
        private LinearLayout linearLayout;
        private ImageView[] starsList;

        public MyOnClickListener(Context context, int tagKey, ImageView[] starsList, LinearLayout linearLayout){
            this.context = context;
            this.tagKey = tagKey;
            this.linearLayout = linearLayout;
            this.starsList = starsList;

        }
        @Override
        public void onClick(View v) {
//=========================================================
            //the code below is experimental
//=========================================================
            for (int i = 0; i < STAR_COUNT; i++) {
                starsList[i].setVisibility(INVISIBLE);
            }

            for (int i = 0; i < tagKey; i++) {
                ImageView imageView = new ImageView(context);
                imageView.setImageResource(R.drawable.star_checkbox_checked1);
                imageView.setTag(i+1);
                linearLayout.addView(imageView);
            }
            for (int i = 0; i < STAR_COUNT - tagKey; i++) {
                ImageView imageView = new ImageView(context);
                imageView.setImageResource(R.drawable.star_checkbox_unchecked1);
                imageView.setTag(i+1);
                linearLayout.addView(imageView);
            }

//=========================================================
            //the code below works
//=========================================================
   /*         for (int i = 0; i < STAR_COUNT; i++) {
                starsList[i].setVisibility(INVISIBLE);
            }

            for (int i = 0; i < tagKey; i++) {
               ImageView imageView = new ImageView(context);
                imageView.setImageResource(R.drawable.star_checkbox_checked1);
                imageView.setTag(i+1);
                linearLayout.addView(imageView);
                }
            for (int i = 0; i < STAR_COUNT - tagKey; i++) {
                ImageView imageView = new ImageView(context);
                imageView.setImageResource(R.drawable.star_checkbox_unchecked1);
                imageView.setTag(i+1);
                linearLayout.addView(imageView);
            }*/

//=========================================================
        }//end onClick(View v)
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        int curWidth, curHeight, curLeft, curTop, maxHeight;

        //get the available size of child view
        final int childLeft = this.getPaddingLeft();
        final int childTop = this.getPaddingTop();
        final int childRight = this.getMeasuredWidth() - this.getPaddingRight();
        final int childBottom = this.getMeasuredHeight() - this.getPaddingBottom();
        final int childWidth = childRight - childLeft;
        final int childHeight = childBottom - childTop;

        maxHeight = 0;
        curLeft = childLeft;
        curTop = childTop;

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);

            if (child.getVisibility() == GONE)
                return;

            //Get the maximum size of the child
            child.measure(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.AT_MOST));
            curWidth = child.getMeasuredWidth();
            curHeight = child.getMeasuredHeight();
            //wrap is reach to the end
            if (curLeft + curWidth >= childRight) {
                curLeft = childLeft;
                curTop += maxHeight;
                maxHeight = 0;
            }
            //do the layout
            child.layout(curLeft, curTop, curLeft + curWidth, curTop + curHeight);
            //store the max height
            if (maxHeight < curHeight)
                maxHeight = curHeight;
            curLeft += curWidth;
        }
    }//onLayout()
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        // Measurement will ultimately be computing these values.
        int maxHeight = 0;
        int maxWidth = 0;
        int childState = 0;
        int mLeftWidth = 0;
        int rowCount = 0;

        // Iterate through all children, measuring them and computing our dimensions
        // from their size.
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);

            if (child.getVisibility() == GONE)
                continue;

            // Measure the child.
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            maxWidth += Math.max(maxWidth, child.getMeasuredWidth());
            mLeftWidth += child.getMeasuredWidth();

            if ((mLeftWidth / deviceWidth) > rowCount) {
                maxHeight += child.getMeasuredHeight();
                rowCount++;
            } else {
                maxHeight = Math.max(maxHeight, child.getMeasuredHeight());
            }
            childState = combineMeasuredStates(childState, child.getMeasuredState());
        }

        // Check against our minimum height and width
        maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());
        maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth());

        // Report our final dimensions.
        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, childState),
                resolveSizeAndState(maxHeight, heightMeasureSpec, childState << MEASURED_HEIGHT_STATE_SHIFT));

}//onMeasure()

}//end MyView() class
