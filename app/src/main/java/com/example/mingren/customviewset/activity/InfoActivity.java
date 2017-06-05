package com.example.mingren.customviewset.activity;

import android.app.Activity;
import android.os.Bundle;

import com.example.mingren.customviewset.R;

import butterknife.ButterKnife;

public class InfoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_info);
        ButterKnife.bind(this);
    }
}
