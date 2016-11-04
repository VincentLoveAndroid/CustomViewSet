package com.example.mingren.customviewset.ndk;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

/**
 * Created by vincent on 2016/11/4.
 * email-address:674928145@qq.com
 * description:
 */

public class JavaImageUtil {
    public static float WhiteDegree = 0.2f;

    public static Bitmap getBitmap(Bitmap originalBitmap) {
        int width = originalBitmap.getWidth();
        int height = originalBitmap.getHeight();
        Bitmap newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        for (int i = 0; i < width - 1; i++) {
            for (int j = 0; j < height - 1; j++) {
                int color = originalBitmap.getPixel(i, j);
                int a = (int) (Color.alpha(color) + WhiteDegree * 255);
                int r = (int) (Color.red(color) + WhiteDegree * 255);
                int g = (int) (Color.green(color) + WhiteDegree * 255);
                int b = (int) (Color.blue(color) + WhiteDegree * 255);
                //边界处理
                a = a > 255 ? 255 : (a < 0 ? 0 : a);
                r = r > 255 ? 255 : (r < 0 ? 0 : r);
                g = g > 255 ? 255 : (g < 0 ? 0 : g);
                b = b > 255 ? 255 : (b < 0 ? 0 : b);
                int newColor = Color.argb(a, r, g, b);
                newBitmap.setPixel(i, j, newColor);
            }
        }
        return newBitmap;
    }
}
