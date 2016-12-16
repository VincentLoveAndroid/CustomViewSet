package com.example.mingren.customviewset.activity;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.mingren.customviewset.R;

public class BitmapActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        LayoutInflaterCompat.setFactory(LayoutInflater.from(this), new LayoutInflaterFactory() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                int count = attrs.getAttributeCount();
                for (int i = 0; i < count; i++) {
                    String attributeName = attrs.getAttributeName(i);
                    String attributeValue = attrs.getAttributeValue(i);
                    System.out.println("parent:" + parent + "," + "name:" + name + "," + "attributeName:" + attributeName + "," + "attributeValue:" + attributeValue);
                    //将TextView替换成Button
                    if (name.equals("TextView")) {
                        Button button = new Button(context, attrs);
                        return button;
                    }
                }
                return null;
            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);
    }
}
