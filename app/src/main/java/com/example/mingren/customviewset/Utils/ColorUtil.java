package com.example.mingren.customviewset.Utils;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by MingRen on 2016/9/23.
 */
public class ColorUtil {

    public static int getRandomColor() {
        Random random = new Random();
        int alpha = 255;// 设置透明度
        int red = random.nextInt(190) + 30;// 30-220
        int green = random.nextInt(190) + 30;
        int blue = random.nextInt(190) + 30;
        int argb = Color.argb(alpha, red, green, blue);
        return argb;

    }
}
