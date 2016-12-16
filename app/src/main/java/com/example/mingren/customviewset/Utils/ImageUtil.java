package com.example.mingren.customviewset.Utils;


import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;

import java.io.InputStream;

public class ImageUtil {

    /**
     * 压缩获取文件的Bitmap，并按要求压缩，防止OOM。
     */
    public static Bitmap decodeBitmapFromFile(String path, int reqWidth, int reqHeight) {
        return decodeBitmapFromFile(path, reqWidth, reqHeight, null);
    }

    /**
     * 压缩获取文件的Bitmap，并按要求压缩，防止OOM。
     */
    public static Bitmap decodeBitmapFromFile(String path, int reqWidth, int reqHeight, Bitmap.Config config) {
        return decodeBitmapFromFile(null, path, reqWidth, reqHeight, config, -1);
    }

    /**
     * 从文件中得到bitmap并根据屏幕密度进行缩放,解决屏幕适配问题
     */
    public static Bitmap decodeBitmapFromFile(Resources res, String path, int reqWidth, int reqHeight, Bitmap.Config config, int inDensity) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        if (config != null) options.inPreferredConfig = config;
        if (inDensity != -1) options.inDensity = inDensity;//xhdpi对应320
        if (res != null) {
            final int densityDpi = res.getDisplayMetrics().densityDpi;
            options.inTargetDensity = densityDpi == 0 ? DisplayMetrics.DENSITY_XHIGH : densityDpi;//根据屏幕密度缩放bitmap
        }
        return BitmapFactory.decodeFile(path, options);
    }


    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 适配屏幕解析stream
     */
    public static Bitmap decodeBitmapFromStream(Resources res, InputStream inputStream, int inDensity) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = null;
        if (inDensity != -1) options.inDensity = inDensity;//xhdpi对应320
        if (res != null) {
            final int densityDpi = res.getDisplayMetrics().densityDpi;
            options.inTargetDensity = densityDpi == 0 ? DisplayMetrics.DENSITY_XHIGH : densityDpi;//根据屏幕密度缩放bitmap
        }
        //if (config != null) options.inPreferredConfig = config;//用了inDensity和inTargetDensity不能使用config.ARGB_4444
        bitmap = BitmapFactory.decodeStream(inputStream, null, options);
        return bitmap;
    }

}