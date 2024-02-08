package com.moutamid.antitiktok;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Window extends ContextWrapper {

    // declaring required variables
    private Context context;
    public static View counterView;
    public static View bottomNavView;
    public static TextView counter;
    public static Button btn1,btn2,btn3,btn4,btn5;
    private WindowManager.LayoutParams mParams;
    private WindowManager.LayoutParams mParamsB;
    private WindowManager mWindowManager;
    private WindowManager mWindowManagerB;
    private LayoutInflater layoutInflater;

    public Window(Context context){
        super(context);
        this.context=context;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // set the layout parameters of the window
            mParams = new WindowManager.LayoutParams(
                    // Shrink the window to wrap the content rather
                    // than filling the screen
                    WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
                    //      WindowManager.LayoutParams.TYPE_SYSTEM_ALERT |
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
                            WindowManager.LayoutParams.FLAG_FULLSCREEN |
                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    // through any transparent parts
                    PixelFormat.TRANSLUCENT);
        }else {
            mParams = new WindowManager.LayoutParams(
                    // Shrink the window to wrap the content rather
                    // than filling the screen
                    WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_SYSTEM_ALERT |
                            WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
                            WindowManager.LayoutParams.FLAG_FULLSCREEN |
                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    // through any transparent parts
                    PixelFormat.TRANSLUCENT);
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            mParamsB = new WindowManager.LayoutParams(
                    // Shrink the window to wrap the content rather
                    // than filling the screen
                    WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
                    // Display it on top of other application windows
                    //        WindowManager.LayoutParams.TYPE_SYSTEM_ALERT |
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
                            WindowManager.LayoutParams.FLAG_FULLSCREEN |
                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
        }else {
            mParamsB = new WindowManager.LayoutParams(
                    // Shrink the window to wrap the content rather
                    // than filling the screen
                    WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT,
                    // Display it on top of other application windows
                    WindowManager.LayoutParams.TYPE_SYSTEM_ALERT |
                            WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
                            WindowManager.LayoutParams.FLAG_FULLSCREEN |
                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
        }

        // getting a LayoutInflater
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // inflating the view with the custom layout we created
        counterView = layoutInflater.inflate(R.layout.counter, null);
        bottomNavView = layoutInflater.inflate(R.layout.bottom_nav, null);
        // set onClickListener on the remove button, which removes
        // the view from the window
        mWindowManager = (WindowManager)context.getSystemService(WINDOW_SERVICE);

        counter = counterView.findViewById(R.id.counter);

        btn1 = bottomNavView.findViewById(R.id.btn1);
        btn2 = bottomNavView.findViewById(R.id.btn2);
        btn3 = bottomNavView.findViewById(R.id.btn3);
        btn4 = bottomNavView.findViewById(R.id.btn4);
        btn5 = bottomNavView.findViewById(R.id.btn5);

        mParams.gravity = Gravity.TOP | Gravity.START;
        mParams.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_USER;

        mParamsB.gravity = Gravity.BOTTOM | Gravity.START ;
        mWindowManagerB = (WindowManager)context.getSystemService(WINDOW_SERVICE);
    }


    public void open() {

        try {
            // check if the view is already
            // inflated or present in the window
            if(counterView.getWindowToken()==null && bottomNavView.getWindowToken()==null) {
                if(counterView.getParent()==null && bottomNavView.getParent()==null) {
                    mWindowManager.addView(counterView, mParams);
                    mWindowManagerB.addView(bottomNavView, mParamsB);
                }
            }
        } catch (Exception e) {
            Log.d("Error1",e.toString());
        }

    }

    public void close() {

        try {
            // remove the view from the window
            ((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(counterView);
            ((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(bottomNavView);
            // invalidate the view
            counterView.invalidate();
            bottomNavView.invalidate();
            // remove all views
            ((ViewGroup) counterView.getParent()).removeAllViews();
            ((ViewGroup) bottomNavView.getParent()).removeAllViews();
            stopService(new Intent(this, ForegroundService.class));

            // the above steps are necessary when you are adding and removing
            // the view simultaneously, it might give some exceptions
        } catch (Exception e) {
            Log.d("Error2", e.toString());
        }
    }

}
