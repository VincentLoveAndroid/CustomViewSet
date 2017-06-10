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
//        for (int i = 0; i < 0; i++) {
//            System.out.println(i + "");
//        }
        System.out.println(test());
    }

    @Override
    public String toString() {
        return super.toString();
    }

    static String test() {
        try {
            System.out.println("先执行这里");
            return "return啦";
        } finally {
            System.out.println("finally");
            System.out.println("暂存一下");
            System.out.println("develop");
        }
    }
}   
