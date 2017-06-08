package com.example.mingren.customviewset.activity.info;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.mingren.customviewset.R;
import com.example.mingren.customviewset.view.info.InfoSelectBean;

/**
 * Created by vincent on 2017/6/5.
 */

public class ContactInfoActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, ContactInfoActivity.class);
        context.startActivity(intent);
    }

}
