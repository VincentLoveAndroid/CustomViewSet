package com.example.mingren.customviewset;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MingRen on 2016/9/24.
 */
public class Sign {

    public static List<String> list = new ArrayList<>();

    public static void main(String arg[]) {
        System.out.println("用int" + 1000 * 60 * 60 * 24 * 7 * 4);
        System.out.println("用long"+1000 * 60 * 60 * 24 * 7 * 4l);
        System.out.println("int的最大值约为二进制的32位/2" + Integer.MAX_VALUE + "");
        System.out.println("long的最大值约为二进制的64位/2" + Long.MAX_VALUE + "");
    }
}
