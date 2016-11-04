package com.example.mingren.customviewset.ndk;

/**
 * Created by vincent on 2016/11/4.
 * email-address:674928145@qq.com
 * description:
 */

public class NdkImageUtil {
    static {
        System.loadLibrary("myImageLib");
    }

    public static native void getImage();
}
