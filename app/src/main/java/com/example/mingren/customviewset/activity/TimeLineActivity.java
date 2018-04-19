package com.example.mingren.customviewset.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mingren.customviewset.R;
import com.example.mingren.customviewset.view.TimeLineView;

public class TimeLineActivity extends Activity {
    TimeLineView timeLineView;
    private int radius = 10;
    private boolean change = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);
        timeLineView = (TimeLineView) findViewById(R.id.timeLineView);
        timeLineView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (change) {
                    timeLineView.setDotColor(getResources().getColor(R.color.blue), 30, getResources().getColor(R.color.green));
                    timeLineView.setDotSize(90);
                    change = false;
                } else {
                    timeLineView.setDotColor(getResources().getColor(R.color.blue), 0, getResources().getColor(R.color.red));
                    timeLineView.setDotSize(45);
                    change = true;
                }
            }
        });
        findViewById(R.id.tv_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextView) findViewById(R.id.tv_test)).setText("");
            }
        });
    }
}
