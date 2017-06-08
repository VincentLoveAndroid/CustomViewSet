package com.example.mingren.customviewset.activity.info;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.mingren.customviewset.R;

/**
 * Created by vincent on 2017/6/5.
 */

public class WorkInfoActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_info);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, WorkInfoActivity.class);
        context.startActivity(intent);
    }
}
