package com.example.mingren.customviewset.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.mingren.customviewset.R;
import com.example.mingren.customviewset.Utils.ImageUtil;
import com.example.mingren.customviewset.Utils.PaintUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by vincent on 2016/12/8.
 * email-address:674928145@qq.com
 * description:
 */

public class BitmapView extends View {

    private Bitmap bitmap;
    private Paint mPaint;

    String path = "/storage/sdcard0/Yazhai/pic_dynamic/dynamic_11_1";
    private String filePath;

    public BitmapView(Context context) {
        super(context);
        init();
    }

    public BitmapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = PaintUtil.getDefaultPaint();
//        filePath = getContext().getExternalCacheDir() + "hongbao";
//        try {
//            InputStream is = getContext().getAssets().open("hongbao.png");
//            int len = 0;
//            byte[] buf = new byte[1024];
//            FileOutputStream fos = new FileOutputStream(filePath);
//            while ((len = is.read(buf)) != -1) {
//                fos.write(buf, 0, len);
//            }
//            fos.close();
//            is.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //bitmap = ImageUtil.decodeBitmapFromFile(path, 100, 100, Bitmap.Config.ARGB_8888, 320);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        bitmap = BitmapFactory.decodeFile(path);

        canvas.drawBitmap(bitmap, 100, 100, mPaint);
//        System.out.println("bitmap" + bitmap.getWidth() + " " + bitmap.getHeight());
//
//        Bitmap bitmapSrc = BitmapFactory.decodeResource(getResources(), R.mipmap.hongbao);
//        canvas.drawBitmap(bitmapSrc, 200, 200, mPaint);
//        System.out.println("bitmapSrc" + bitmapSrc.getWidth() + " " + bitmapSrc.getHeight());
    }

}
