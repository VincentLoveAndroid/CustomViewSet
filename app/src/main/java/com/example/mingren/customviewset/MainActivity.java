package com.example.mingren.customviewset;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.mingren.customviewset.activity.DragPhotoActivity;

import java.io.BufferedReader;
import java.nio.Buffer;

import uk.co.senab.photoview.Compat;

public class MainActivity extends Activity {

    private ImageView mIv;
    private static final String TAG = "MainActivity";
    private WindowManager windowManager;
    private ImageView iv;
    private int left;
    private int top;
    private float l;
    private float t;
    private long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        DiskCacheUtil diskCacheUtil = DiskCacheUtil.newInstance(this);
//        diskCacheUtil.open(1024 * 1024, "bitmap");
////        for (int i = 0; i < UrlUtil.arrUrl.length; i++) {
////            diskCacheUtil.cache(UrlUtil.arrUrl[i]);
////        }
//        ImageView ivTest = (ImageView) findViewById(R.id.iv_test);
//        long size = diskCacheUtil.size();
//        InputStream inputStream = diskCacheUtil.get(UrlUtil.arrUrl[0]);
//        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//        ivTest.setImageBitmap(bitmap);
//
//        System.out.println(size / 1024 + "kb");
        mIv = (ImageView) findViewById(R.id.iv);
        mIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DragPhotoActivity.class);
                intent.putExtra("l", mIv.getLeft());
                intent.putExtra("t", mIv.getTop());
                intent.putExtra("width", mIv.getWidth());
                intent.putExtra("height", mIv.getHeight());

                startActivityForResult(intent, 1);
                mIv.setVisibility(View.INVISIBLE);
//                System.out.println("点击啦");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (data != null) {
                l = data.getFloatExtra("l", 0);
                t = data.getFloatExtra("t", 0);
//
//                mIv.setVisibility(View.VISIBLE);
//
//                PropertyValuesHolder pvhx = PropertyValuesHolder.ofFloat("translationX", l - mIv.getLeft(), 0);
//                PropertyValuesHolder pvhy = PropertyValuesHolder.ofFloat("translationY", t - mIv.getTop(), 0);
//                ObjectAnimator.ofPropertyValuesHolder(mIv, pvhx, pvhy).start();

//                TranslateAnimation translateAnimation = new TranslateAnimation(l, mIv.getLeft(), t, mIv.getTop());
//                translateAnimation.setDuration(2000);
//                mIv.startAnimation(translateAnimation);

                windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                final WindowManager.LayoutParams params = new WindowManager.LayoutParams(mIv.getWidth(), mIv.getWidth(), 0, 0, PixelFormat.TRANSLUCENT);
                params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                params.gravity = Gravity.LEFT | Gravity.TOP;
                params.x = (int) l;
                params.y = (int) t;

                iv = new ImageView(this);
                left = mIv.getLeft();
                top = mIv.getTop();
                iv.setImageResource(R.drawable.ic_launcher);
                windowManager.addView(iv, params);
//                iv.post(new ReverseRunnable());
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(l, left);
                valueAnimator.setDuration(5000);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                    private long time;

                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float fraction = animation.getAnimatedFraction();
                        params.x = (int) (l + (left - l) * fraction);
                        params.y = (int) (t + (top - t) * fraction);
                        windowManager.updateViewLayout(iv, params);
                        if (fraction == 1) {
                            windowManager.removeView(iv);
                            mIv.setVisibility(View.VISIBLE);
                        }
                        Log.i(TAG, "onAnimationUpdate: x" + params.x);
                        Log.i(TAG, "duration: " + (System.currentTimeMillis() - time));
                        time = System.currentTimeMillis();
                    }
                });
                valueAnimator.start();

//                ObjectAnimator translationX = ObjectAnimator.ofFloat(iv, "translationX", l, left);
//                translationX.setDuration(500);
//                ObjectAnimator translationY = ObjectAnimator.ofFloat(iv, "translationY", t, top);
//                translationY.setDuration(500);
//
//                AnimatorSet animatorSet = new AnimatorSet();
//                animatorSet.playTogether(translationX, translationY);
//                animatorSet.start();
            }
        }
    }

    private class ReverseRunnable implements Runnable {

        @Override
        public void run() {
            WindowManager.LayoutParams params = (WindowManager.LayoutParams) iv.getLayoutParams();
            params.x -= (l - left) * 1.0f / 1000;
            params.y -= (t - top) * 1.0f / 1000;
            windowManager.updateViewLayout(iv, params);
            ViewCompat.postOnAnimation(iv, this);

            Log.i(TAG, "run: " + params.x + " " + params.y);
            Log.i(TAG, "duration: " + (System.currentTimeMillis() - time));
            time = System.currentTimeMillis();
        }
    }
}
