package com.example.mingren.customviewset.activity;

import android.app.Activity;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mingren.customviewset.R;

public class TabLayoutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tl_tab);
        tabLayout.addTab(tabLayout.newTab().setText("哈哈"));
        tabLayout.addTab(tabLayout.newTab().setText("卡卡哈哈哈"));
        tabLayout.addTab(tabLayout.newTab().setText("卡卡哈哈哈"));
        tabLayout.addTab(tabLayout.newTab().setText("卡卡"));
        tabLayout.addTab(tabLayout.newTab().setText("卡卡"));
        tabLayout.addTab(tabLayout.newTab().setText("嘻嘻"));
        tabLayout.addTab(tabLayout.newTab().setText("嘻嘻"));
        tabLayout.addTab(tabLayout.newTab().setText("嘻嘻"));
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//设置模式，可滚动
        tabLayout.setSelectedTabIndicatorHeight(2);//设置指示器高度
        tabLayout.setSelectedTabIndicatorColor(Color.RED);//设置指示器的颜色
        tabLayout.setBackgroundColor(Color.GREEN);//设置背景色
    }
}
