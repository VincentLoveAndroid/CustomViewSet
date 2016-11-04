package com.example.mingren.customviewset;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by MingRen on 2016/9/24.
 */
public class Sign {

    public static List<String> list = new ArrayList<>();
    static String[] a = new String[2];

    public static void main(String arg[]) {

        for(int i=0, j=100;i<j;i++,j--){
            System.out.println(i+" "+j);
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @MyInfo
    public void getName() {
        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);

    }
}   
