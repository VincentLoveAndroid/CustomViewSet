package com.example.mingren.customviewset.view.zuojia;

import android.animation.TimeInterpolator;

/**
 * Created by vincent on 2016/12/21.
 * email-address:674928145@qq.com
 * description:先加速后加速，再匀速插值器
 */

public class MyInterpolator implements TimeInterpolator {

    private static float fraction = 0.8f;
    private static float begin = 0.9045085f;

    @Override
    public float getInterpolation(float input) {
        if (input < fraction) {
            return (float) (Math.cos((input + 1) * Math.PI) / 2.0f) + 0.5f;
        } else {
            return (float) (begin + (input - fraction) / (1-fraction) * (1.0 - begin));
        }
    }


    public static void main(String arg[]) {
        float a = (float) (Math.cos((fraction + 1) * Math.PI) / 2.0f) + 0.5f;
        System.out.println(a + "");
    }
}
