package com.example.mingren.customviewset;

import android.app.Application;
import android.content.Context;


/**
 * Created by vincent on 2017/5/27.
 */

public class MyApplication extends Application {
    public static Context context;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        this.context = base;
    }

    public MyApplication() {

    }
}
