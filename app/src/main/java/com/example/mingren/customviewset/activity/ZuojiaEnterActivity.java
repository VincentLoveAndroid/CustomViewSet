package com.example.mingren.customviewset.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.mingren.customviewset.R;
import com.example.mingren.customviewset.view.zuojia.ZuojiaEnterView;

public class ZuojiaEnterActivity extends AppCompatActivity {
    static int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zuojia_enter);
        final ZuojiaEnterView zuojiaEnterView = (ZuojiaEnterView) findViewById(R.id.zuojiaView);

        findViewById(R.id.tv_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               zuojiaEnterView.initMotoringMsg("鸣人开着兰博基尼来看你直播啦" + count++, "");
            }
        });

        findViewById(R.id.tv_end).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // zuojiaEnterView.cancelAnimator();
            }
        });
    }
}
