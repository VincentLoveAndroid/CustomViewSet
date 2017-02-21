package com.example.mingren.customviewset.view.window;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mingren.customviewset.R;
import com.example.mingren.customviewset.Utils.ScreenUtils;

import org.w3c.dom.Text;

public class WindowActivity extends Activity implements View.OnTouchListener {

    private TextView tvTest;
    private WindowManager windowManager;
    float downX;
    float downY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        setContentView(R.layout.activity_window2);

        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        tvTest = new TextView(this);
        tvTest.setBackgroundColor(Color.BLUE);
        tvTest.setText("哈哈");
        tvTest.setTextSize(100);
        tvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WindowActivity.this, "点击window", Toast.LENGTH_LONG).show();
            }
        });
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, 0, 0,
                PixelFormat.TRANSPARENT);
//        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        params.flags = WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.gravity = Gravity.LEFT | Gravity.TOP;
        params.x = 100;
        params.y = 300;
        windowManager.addView(tvTest, params);
        tvTest.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
                KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("");
                keyguardLock.disableKeyguard();
                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float x = event.getRawX();//得到屏幕的坐标原点，从状态栏开始
                float y = event.getRawY();
                WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) tvTest.getLayoutParams();
                layoutParams.x = (int) (x - downX);
                layoutParams.y = (int) (y - downY) - ScreenUtils.getStatusHeight(this);//Activity里面的window，相当于View，原点从除开状态栏开始
                windowManager.updateViewLayout(tvTest, layoutParams);
                break;
        }
        return false;
    }
}
