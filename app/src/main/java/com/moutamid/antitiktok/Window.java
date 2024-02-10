package com.moutamid.antitiktok;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Window extends ContextWrapper {

    // declaring required variables
    public static View counterView;
    public static View bottomNavView;
    public static View mainView;
    public static View canva;
    public static TextView counter;
    public static Button btn1,btn2,btn3,btn4,btn5;
    private WindowManager.LayoutParams mParams;
    private WindowManager.LayoutParams mParamsB;
    private WindowManager.LayoutParams mParamsC;
    private WindowManager mWindowManager;
    private WindowManager mWindowManagerB;
    private WindowManager mWindowManagerC;
    private LayoutInflater layoutInflater;

    public Window(Context context){
        super(context);

        setParams(context);

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // inflating the view with the custom layout we created
        counterView = layoutInflater.inflate(R.layout.counter, null);
        bottomNavView = layoutInflater.inflate(R.layout.bottom_nav, null);
        mainView = layoutInflater.inflate(R.layout.windiw, null);

        counter = counterView.findViewById(R.id.counter);
        canva = mainView.findViewById(R.id.canva);

        btn1 = bottomNavView.findViewById(R.id.btn1);
        btn2 = bottomNavView.findViewById(R.id.btn2);
        btn3 = bottomNavView.findViewById(R.id.btn3);
        btn4 = bottomNavView.findViewById(R.id.btn4);
        btn5 = bottomNavView.findViewById(R.id.btn5);

        mParams.gravity = Gravity.TOP | Gravity.START;
        mWindowManager = (WindowManager)context.getSystemService(WINDOW_SERVICE);

        mParamsC.gravity = Gravity.CENTER;
        mWindowManagerC = (WindowManager)context.getSystemService(WINDOW_SERVICE);

        mParamsB.gravity = Gravity.BOTTOM | Gravity.START ;
        mWindowManagerB = (WindowManager)context.getSystemService(WINDOW_SERVICE);
    }

    private void setParams(Context context) {
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
                    // than filling the screen.
                    WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT,
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            mParamsC = new WindowManager.LayoutParams(
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
            mParamsC = new WindowManager.LayoutParams(
                    // Shrink the window to wrap the content rather
                    // than filling the screen
                    WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
                    // Display it on top of other application windows
                    WindowManager.LayoutParams.TYPE_SYSTEM_ALERT |
                            WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
                            WindowManager.LayoutParams.FLAG_FULLSCREEN |
                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
        }

/*        mParams.gravity = Gravity.TOP | Gravity.START;
        mWindowManager = (WindowManager)context.getSystemService(WINDOW_SERVICE);

        mParamsC.gravity = Gravity.CENTER;
        mWindowManagerC = (WindowManager)context.getSystemService(WINDOW_SERVICE);

        mParamsB.gravity = Gravity.BOTTOM | Gravity.START ;
        mWindowManagerB = (WindowManager)context.getSystemService(WINDOW_SERVICE);*/

    }


    public void open() {

        try {
            // check if the view is already
            // inflated or present in the window
            if(counterView.getWindowToken()==null && bottomNavView.getWindowToken()==null && mainView.getWindowToken()==null) {
                if(counterView.getParent()==null && bottomNavView.getParent()==null && mainView.getParent()==null) {
                    mWindowManager.addView(counterView, mParams);
                    mWindowManagerB.addView(bottomNavView, mParamsB);
                    mWindowManagerC.addView(mainView, mParamsC);
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
