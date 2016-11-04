package com.example.mingren.customviewset.activity;

import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.mingren.customviewset.R;

public class VectorDrawableActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector_drawable);
        ImageView imageView = (ImageView) findViewById(R.id.image_view);
        Drawable drawable = imageView.getDrawable();
//AnimatedVectorDrawableCompat实现了Animatable接口
        if (drawable instanceof Animatable){
            ((Animatable) drawable).start();
        }
    }
}
