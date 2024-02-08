package com.moutamid.antitiktok;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.accessibilityservice.GestureDescription.StrokeDescription;
import android.content.Intent;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

public class MotionService extends AccessibilityService {
    private boolean canScroll = true;
    private boolean tikTokInForeground = false;
    private static final String TAG = "MotionService";
    public static final String TIKTOK = "com.zhiliaoapp.musically";
    public static final String PLAYSTORE = "com.android.vending";

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Toast.makeText(getApplicationContext(), "Connected!", Toast.LENGTH_SHORT).show();
    }
    int[] location = new int[2];
    boolean continueBool = true;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d(TAG, "Received event: " + event.toString());
        Log.d(TAG, "getPackageName: " + event.getPackageName());
        if (event.getSource() == null) {
            Toast.makeText(this, "Returnd", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.d(TAG, "onAccessibilityEvent: triggered");
        if (String.valueOf(event.getPackageName()).equals(TIKTOK) && event.getEventType() == AccessibilityEvent.TYPE_VIEW_SCROLLED && continueBool) {
            Log.d(TAG, "onAccessibilityEvent: Started");
            if (!Constants.isServiceRunningInForeground(getApplicationContext(), ForegroundService.class)){
                startService();
            }
            continueBool = false;
            clickWindow(event);
            new Handler().postDelayed(()-> continueBool = true, 1000);
            Log.d(TAG, "onAccessibilityEvent/34: scrollY : " + event.getScrollY());

            new Handler().postDelayed(() -> {
                if (Window.counter != null) {
                    int i = Integer.parseInt(Window.counter.getText().toString());
                    Window.counter.setText(String.valueOf(i + 1));
                }
                Window.btn1.setOnClickListener(v -> {
                    Window.btn1.getLocationOnScreen(location);
                    Window.bottomNavView.setVisibility(View.GONE);
                    new Handler().postDelayed(() -> {
                        clickButton(v);
                    }, 300);
                    new Handler().postDelayed(() -> {
                        Window.bottomNavView.setVisibility(View.VISIBLE);
                    }, 2000);
                });
                Window.btn2.setOnClickListener(v -> {
                    Window.btn2.getLocationOnScreen(location);
                    location[0] = location[0] + 50;
                    Window.bottomNavView.setVisibility(View.GONE);
                    new Handler().postDelayed(() -> {
                        clickButton(v);
                    }, 300);
                    new Handler().postDelayed(() -> {
                        Window.bottomNavView.setVisibility(View.VISIBLE);
                    }, 2000);
                });
                Window.btn3.setOnClickListener(v -> {
                    Window.btn3.getLocationOnScreen(location);
                    location[0] = location[0] + 50;
                    Window.bottomNavView.setVisibility(View.GONE);
                    new Handler().postDelayed(() -> {
                        clickButton(v);
                    }, 300);
                    new Handler().postDelayed(() -> {
                        Window.bottomNavView.setVisibility(View.VISIBLE);
                    }, 2000);
                });
                Window.btn4.setOnClickListener(v -> {
                    Window.btn4.getLocationOnScreen(location);
                    location[0] = location[0] + 50;
                    Window.bottomNavView.setVisibility(View.GONE);
                    new Handler().postDelayed(() -> {
                        clickButton(v);
                    }, 300);
                    new Handler().postDelayed(() -> {
                        Window.bottomNavView.setVisibility(View.VISIBLE);
                    }, 2000);
                });
                Window.btn5.setOnClickListener(v -> {
                    Window.btn5.getLocationOnScreen(location);
                    location[0] = location[0] + 50;
                    Window.bottomNavView.setVisibility(View.GONE);
                    new Handler().postDelayed(() -> {
                        clickButton(v);
                    }, 300);
                    new Handler().postDelayed(() -> {
                        Window.bottomNavView.setVisibility(View.VISIBLE);
                    }, 2000);
                });
            }, 1000);

        }
        // TODO  Stop Foreground Service
        // TODO Home Scroll Only
        // TODO Counter Visibility off
    }

    public void startService() {
        if (Settings.canDrawOverlays(this)) {
            Intent i = new Intent(this, ForegroundService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(i);
            } else {
                startService(i);
            }
        }
    }

    public boolean isTikTokInForeground() {
        return tikTokInForeground;
    }

    public void clickButton(View v){
        Point position = new Point();
        position.x = location[0];
        position.y = location[1];
        GestureDescription.Builder builder = new GestureDescription.Builder();
        Path path = new Path();
        path.moveTo(position.x, position.y);
        builder.addStroke(new StrokeDescription(path, 50L, 1L));
        GestureDescription gesture = builder.build();
        boolean despatch = dispatchGesture(gesture, new GestureResultCallback() {
            @Override
            public void onCompleted(GestureDescription gestureDescription) {
                super.onCompleted(gestureDescription);
                Log.d("Gesture", "Completed222");
            }
        }, null);

        Log.d(TAG, "clickButton: despatch : " + despatch);

    }

    private void clickWindow(AccessibilityEvent event) {
        Point position = new Point();
        position.x = getResources().getDisplayMetrics().widthPixels / 2;
        position.y = getResources().getDisplayMetrics().heightPixels / 2;
        GestureDescription.Builder builder = new GestureDescription.Builder();
        Path path = new Path();
        path.moveTo(position.x, position.y);
        builder.addStroke(new StrokeDescription(path, 50L, 1L));
        GestureDescription gesture = builder.build();
        boolean dispatched = dispatchGesture(gesture, new GestureResultCallback() {
            @Override
            public void onCompleted(GestureDescription gestureDescription) {
                super.onCompleted(gestureDescription);
                Log.d("Gesture", "Completed");
            }
        }, null);
    }

    private void scrollWindow(AccessibilityEvent event) {
        if (event.getPackageName().equals(PLAYSTORE)) {
            if (canScroll) {
                for (int i = 0; i < event.getSource().getChildCount(); i++) {
                    AccessibilityNodeInfo child = event.getSource().getChild(i);
                    if (child != null && canChildScroll(child)) {
                        Point position = new Point();
                        position.x = getResources().getDisplayMetrics().widthPixels / 2;
                        position.y = getResources().getDisplayMetrics().heightPixels / 2;
                        GestureDescription.Builder builder = new GestureDescription.Builder();
                        Path path = new Path();
                        path.moveTo(position.x, position.y);
                        path.lineTo(position.x, position.y - 200);
                        builder.addStroke(new StrokeDescription(path, 0L, 50L));
                        GestureDescription gesture = builder.build();
                        boolean dispatched = dispatchGesture(gesture, new GestureResultCallback() {
                            @Override
                            public void onCompleted(GestureDescription gestureDescription) {
                                super.onCompleted(gestureDescription);
                                Log.d("Gesture", "Completed");
                                canScroll = true;
                            }
                        }, null);
                        canScroll = false;
                    }
                }
            }
        }
    }

    private boolean canChildScroll(AccessibilityNodeInfo view) {
        return view.isScrollable();
    }


    @Override
    public void onInterrupt() {

    }
}
