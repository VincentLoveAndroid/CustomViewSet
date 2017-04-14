package com.example.mingren.customviewset;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.mingren.customviewset.activity.DragPhotoActivity;
import com.example.mingren.customviewset.fragment.OrientationFragment;

import java.io.BufferedReader;
import java.nio.Buffer;

import uk.co.senab.photoview.Compat;

public class MainActivity extends FragmentActivity {
    protected FragmentManager mFragmentManager = null;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragmentManager = getSupportFragmentManager();

        findViewById(R.id.tv_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(new OrientationFragment(), R.id.fl_container);
            }
        });

    }


    public void showFragment(Fragment fragment, int id) {
        if (isFinishing()) {
            return;
        }
        if (fragment.isAdded()) {
            mFragmentManager.beginTransaction().show(fragment).commitAllowingStateLoss();
        } else {
            mFragmentManager.beginTransaction().add(id, fragment, fragment.getClass().toString()).commitAllowingStateLoss();
        }
    }

    public void hideFragment(Fragment fragment) {
        if (fragment.isAdded() && !isFinishing()) {
            mFragmentManager.beginTransaction().hide(fragment).commitAllowingStateLoss();
        }

    }
}
