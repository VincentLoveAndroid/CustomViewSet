package com.example.mingren.customviewset.Utils;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by vincent on 2016/11/17.
 * email-address:674928145@qq.com
 * description:
 */

public class PaintUtil {
    public static Paint getDefaultPaint() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        return paint;
    }
}
