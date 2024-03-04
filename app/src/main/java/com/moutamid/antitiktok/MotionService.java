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
import android.view.MotionEvent;
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
    boolean homeScroll = true;
    boolean isClicked = false;
    int distance, lastDistance = 0;
    int counter = 0;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getSource() == null) {
            return;
        }
        if (String.valueOf(event.getPackageName()).equals(TIKTOK) && event.getEventType() == AccessibilityEvent.TYPE_VIEW_SCROLLED && continueBool && homeScroll) {
            Log.d(TAG, "onAccessibilityEvent: Started");
            if (!Constants.isServiceRunningInForeground(getApplicationContext(), ForegroundService.class)) {
                startService();
            }
            continueBool = false;
            clickWindow();
            new Handler().postDelayed(() -> continueBool = true, 1000);

            new Handler().postDelayed(() -> {
                if (Window.counter != null) {
                    counter++;
                    String[] s = Window.counter.getText().toString().split(" - "); // 0 - 0
                    int i = Integer.parseInt(s[0]);
                    if (counter < 5) {
                        Window.counter.setVisibility(View.GONE);
                    } else {
                        Window.counter.setVisibility(View.VISIBLE);
                        int count = i + 1;
                        int cen = (count * 3);
                        Window.counter.setText(count + " - " + cen + " centimeter");
                    }
                }

                Window.canva.setOnTouchListener((v, e) -> {
                    if (e.getAction() == MotionEvent.ACTION_DOWN) {
                        location[0] = (int) e.getX();
                        location[1] = (int) e.getY();
                        Log.d(TAG, "onTouch: X " + location[0]);
                        Log.d(TAG, "onTouch: Y " + location[1]);
                        Window.canva.setVisibility(View.GONE);
                        isClicked = true;
                    }
                    if (e.getAction() == MotionEvent.ACTION_MOVE) {
                        Log.d(TAG, "onMOVE: X " + location[0]);
                        Log.d(TAG, "onMOVE: Y " + location[1]);
                        isClicked = false;
                        Window.canva.setVisibility(View.GONE);
                    }
                    if (e.getAction() == MotionEvent.ACTION_UP) {
                        Log.d(TAG, "onUP: X " + location[0]);
                        Log.d(TAG, "onUP: Y " + location[1]);
                        Window.canva.performClick();
                        if (isClicked) {
                            new Handler().postDelayed(() -> clickButton(), 300);
                        } else {
                            distance = location[1] - (int) e.getY();
                            Log.d(TAG, "distance: Y " + distance);
                            scrollWindow(event);
                        }
                        new Handler().postDelayed(() -> {
                            Window.canva.setVisibility(View.VISIBLE);
                        }, 2000);
                    }
                    if (e.getAction() == MotionEvent.ACTION_CANCEL) {
                        Log.d(TAG, "onAccessibilityEvent: Cancel");
                    }
                    return true;
                });

                Window.btn1.setOnClickListener(v -> {
                    homeScroll = true;
                    Window.btn1.getLocationOnScreen(location);
                    Window.bottomNavView.setVisibility(View.GONE);
                    Window.counterView.setVisibility(View.GONE);
                    new Handler().postDelayed(this::clickButton, 300);
                    new Handler().postDelayed(() -> {
                        Window.bottomNavView.setVisibility(View.VISIBLE);
                        Window.counterView.setVisibility(View.VISIBLE);
                    }, 2000);
                });
                Window.btn2.setOnClickListener(v -> {
                    homeScroll = false;
                    Window.btn2.getLocationOnScreen(location);
                    location[0] = location[0] + 50;
                    Window.bottomNavView.setVisibility(View.GONE);
                    Window.counterView.setVisibility(View.GONE);
                    new Handler().postDelayed(this::clickButton, 300);
                    new Handler().postDelayed(() -> {
                        Window.bottomNavView.setVisibility(View.VISIBLE);
                    }, 2000);
                });
                Window.btn3.setOnClickListener(v -> {
                    homeScroll = false;
                    Window.btn3.getLocationOnScreen(location);
                    location[0] = location[0] + 50;
                    Window.bottomNavView.setVisibility(View.GONE);
                    Window.counterView.setVisibility(View.GONE);
                    new Handler().postDelayed(this::clickButton, 300);
                    new Handler().postDelayed(() -> {
                        Window.bottomNavView.setVisibility(View.VISIBLE);
                    }, 2000);
                });
                Window.btn4.setOnClickListener(v -> {
                    homeScroll = false;
                    Window.btn4.getLocationOnScreen(location);
                    location[0] = location[0] + 50;
                    Window.bottomNavView.setVisibility(View.GONE);
                    Window.counterView.setVisibility(View.GONE);
                    new Handler().postDelayed(this::clickButton, 300);
                    new Handler().postDelayed(() -> {
                        Window.bottomNavView.setVisibility(View.VISIBLE);
                    }, 2000);
                });
                Window.btn5.setOnClickListener(v -> {
                    homeScroll = false;
                    Window.btn5.getLocationOnScreen(location);
                    location[0] = location[0] + 50;
                    Window.bottomNavView.setVisibility(View.GONE);
                    Window.counterView.setVisibility(View.GONE);
                    new Handler().postDelayed(this::clickButton, 300);
                    new Handler().postDelayed(() -> {
                        Window.bottomNavView.setVisibility(View.VISIBLE);
                    }, 2000);
                });
            }, 1000);

        } else {
/*            if (serviceIntent != null && ForegroundService.window != null) {
                ForegroundService.window.close();
            }*/
        }
        // TODO  Stop Foreground Service
    }

    Intent serviceIntent;

    public void startService() {
        if (Settings.canDrawOverlays(this)) {
            serviceIntent = new Intent(this, ForegroundService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(serviceIntent);
            } else {
                startService(serviceIntent);
            }
        }
    }

    public boolean isTikTokInForeground() {
        return tikTokInForeground;
    }

    public void clickButton() {
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

    private void clickWindow() {
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
        if (canScroll) {
            for (int i = 0; i < event.getSource().getChildCount(); i++) {
                AccessibilityNodeInfo child = event.getSource().getChild(i);
                if (child != null && canChildScroll(child)) {
                    Point position = new Point();
                    position.x = 5;
//                    position.x = getResources().getDisplayMetrics().widthPixels / 2;
                    position.y = getResources().getDisplayMetrics().heightPixels / 2;
                    GestureDescription.Builder builder = new GestureDescription.Builder();
                    Path path = new Path();
                    path.moveTo(position.x, position.y);
                    path.lineTo(position.x, position.y - (distance - lastDistance));
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

    private boolean canChildScroll(AccessibilityNodeInfo view) {
        return view.isScrollable();
    }


    @Override
    public void onInterrupt() {

    }
}
