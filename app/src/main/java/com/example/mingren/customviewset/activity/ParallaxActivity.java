package com.example.mingren.customviewset.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mingren.customviewset.R;
import com.example.mingren.customviewset.view.ParallaxListView;

public class ParallaxActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parallax);
        ParallaxListView lvTest = (ParallaxListView) findViewById(R.id.lv_list);
        View header = View.inflate(this, R.layout.view_parallax_header, null);
        lvTest.addHeaderView(header);
        ImageView ivBg = (ImageView) header.findViewById(R.id.iv_bg);
        lvTest.setmHeaderbg(ivBg);
        lvTest.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 13;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tv = new TextView(ParallaxActivity.this);
                tv.setText("hello" + position);
                tv.setGravity(Gravity.CENTER_VERTICAL);
                tv.setTextSize(20);
                return tv;
            }
        });
    }
}
