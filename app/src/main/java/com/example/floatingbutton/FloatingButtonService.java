package com.example.floatingbutton;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import androidx.annotation.Nullable;

public class FloatingButtonService extends Service {

    private WindowManager windowManager;
    private Button floatingButton;
    private WindowManager.LayoutParams params;
    private float initialX, initialY;
    private float initialTouchX, initialTouchY;
    private Handler handler = new Handler();

    private ButtonItem buttonItem;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        buttonItem = new ButtonItem(
                intent.getStringExtra("buttonText"),
                intent.getStringExtra("keyCode"),
                intent.getBooleanExtra("holdEnabled", false),
                intent.getIntExtra("repeatInterval", 500)
        );

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        floatingButton = new Button(this);
        floatingButton.setText(buttonItem.name);

        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );
        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 100;
        params.y = 300;

        floatingButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        params.x = initialX + (int)(event.getRawX() - initialTouchX);
                        params.y = initialY + (int)(event.getRawY() - initialTouchY);
                        windowManager.updateViewLayout(floatingButton, params);
                        return true;
                }
                return false;
            }
        });

        windowManager.addView(floatingButton, params);

        if(buttonItem.holdEnabled) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    performKeyAction(buttonItem.keyCode); // функция эмуляции клавиши
                    handler.postDelayed(this, buttonItem.repeatInterval);
                }
            }, buttonItem.repeatInterval);
        }

        return START_STICKY;
    }

    private void performKeyAction(String keyCode) {
        // Тут можно реализовать эмуляцию нажатия
        // Для игр без root — через Accessibility Service
        // Для теста можно добавить лог:
        System.out.println("Pressed: " + keyCode);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(floatingButton != null) windowManager.removeView(floatingButton);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
