package com.example.mingren.customviewset;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Interpolator;
import android.icu.text.DateFormat;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

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
//        System.out.println(test());
//        test2();
//        try {
//            Integer integer = Integer.valueOf("2017-10");
        String a = "128";
        String b = "128";
        System.out.println(a.equals(b));
        System.out.println("哈哈");

        Object o = 12.00;
        System.out.println(Double.parseDouble(o.toString()));
//        } catch (Exception e) {
//
//        }
//        test();
//        System.out.println("哈哈la ");
//        Integer b = 1;
//        System.out.println(String.valueOf(b
//        ));
    }

    public static void test2() {
        try {
            test3();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void test3() {
        int i = 6 / 0;
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
