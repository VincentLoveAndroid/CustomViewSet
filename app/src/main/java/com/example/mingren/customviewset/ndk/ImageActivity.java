package com.example.mingren.customviewset.ndk;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mingren.customviewset.R;
import com.example.mingren.customviewset.Utils.DensityUtil;

public class ImageActivity extends Activity {

    private ImageView imageView;
    private Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        imageView = (ImageView) findViewById(R.id.iv_test);

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.girl);
    }

    public void source(View view) {
        imageView.setImageResource(R.mipmap.girl);

    }

    public void java(View view) {
        long lastTime = System.currentTimeMillis();
        Bitmap newBitmap = JavaImageUtil.getBitmap(bitmap);
        Toast.makeText(this, "java处理耗时" + (System.currentTimeMillis() - lastTime) + "ms", Toast.LENGTH_SHORT).show();
        imageView.setImageBitmap(newBitmap);
    }

    public void ndk(View view) {

    }
}
