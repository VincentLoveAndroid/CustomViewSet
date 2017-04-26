package com.example.mingren.customviewset;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.example.mingren.customviewset.activity.BezierActivity;
import com.example.mingren.customviewset.activity.ClockSurfaceActivity;
import com.example.mingren.customviewset.activity.ColorMatrixFilterActivity;
import com.example.mingren.customviewset.activity.CouponActivity;
import com.example.mingren.customviewset.activity.DragSortItemActivity;
import com.example.mingren.customviewset.activity.GodActivity;
import com.example.mingren.customviewset.activity.LuckDishActivity;
import com.example.mingren.customviewset.activity.ParallaxActivity;
import com.example.mingren.customviewset.activity.PayEditTextActivity;
import com.example.mingren.customviewset.activity.RadarActivity;
import com.example.mingren.customviewset.activity.RopeBallActivity;
import com.example.mingren.customviewset.activity.ScaleActivity;
import com.example.mingren.customviewset.activity.WaveActivity;
import com.example.mingren.customviewset.activity.Win10LaunchActivity;
import com.example.mingren.customviewset.fragment.OrientationFragment;


public class MainActivity extends FragmentActivity {
    protected FragmentManager mFragmentManager = null;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void RadarView(View view) {
        startActivity(new Intent(this, RadarActivity.class));
    }

    public void MatrixView(View view) {
        startActivity(new Intent(this, ColorMatrixFilterActivity.class));
    }

    public void RopeBallView(View view) {
        startActivity(new Intent(this, RopeBallActivity.class));
    }

    public void ParallaxListView(View view) {
        startActivity(new Intent(this, ParallaxActivity.class));
    }

    public void PayEditText(View view) {
        startActivity(new Intent(this, PayEditTextActivity.class));
    }

    public void CouponView(View view) {
        startActivity(new Intent(this, CouponActivity.class));
    }

    public void DragSortItem(View view) {
        startActivity(new Intent(this, DragSortItemActivity.class));
    }

    public void waveView(View view) {
        startActivity(new Intent(this, WaveActivity.class));
    }

    public void bezierView(View view) {
        startActivity(new Intent(this, BezierActivity.class));
    }

    public void clockSurfaceView(View view) {
        startActivity(new Intent(this, ClockSurfaceActivity.class));
    }

    public void GodView(View view) {
        startActivity(new Intent(this, GodActivity.class));
    }

    public void LuckyDishView(View view) {
        startActivity(new Intent(this, LuckDishActivity.class));
    }

    public void Window10LaunchView(View view) {
        startActivity(new Intent(this, Win10LaunchActivity.class));
    }

    public void ScaleView(View view) {
        startActivity(new Intent(this, ScaleActivity.class));
    }
}
