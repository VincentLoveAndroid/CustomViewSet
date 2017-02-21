package com.example.mingren.customviewset.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.mingren.customviewset.R;
import com.example.mingren.customviewset.view.dragPhoto.DragPhotoView;
import com.example.mingren.customviewset.view.dragPhoto.ReverseListener;

/**
 * Created by vincent on 2017/1/3.
 * email-address:674928145@qq.com
 * description:
 */

public class DragPhotoActivity extends Activity implements ReverseListener {

    private static final String TAG = "DragPhotoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_photo);
        DragPhotoView dragPhotoView = (DragPhotoView) findViewById(R.id.photo_view);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        int l = extras.getInt("l");
        int t = extras.getInt("t");
        int width = extras.getInt("width");
        int height = extras.getInt("height");

        dragPhotoView.setL(l);
        dragPhotoView.setT(t);


        Log.i(TAG, "onCreate: " + "l:" + l + " t:" + t + " width:" + width + " height:" + height);

        dragPhotoView.setZoomable(true);

        dragPhotoView.setOnReverseListener(this);
    }

    @Override
    public void onPhotoReverse() {

    }

    @Override
    public void onFinish(float l, float t) {
        Intent intent = getIntent();
        intent.putExtra("l", l);
        intent.putExtra("t", t);
        setResult(RESULT_OK, intent);
        finish();

    }
}
