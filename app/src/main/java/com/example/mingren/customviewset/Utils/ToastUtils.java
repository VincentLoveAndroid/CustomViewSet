package com.example.mingren.customviewset.Utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by sunzhiyong on 15/12/2.
 * 通用Toast工具
 */
public class ToastUtils {
    public static void showToast(Context context, String toast){
        if(!TextUtils.isEmpty(toast)){
            Toast t = Toast.makeText(context, toast, Toast.LENGTH_SHORT);
            t.setGravity(Gravity.CENTER,0,0);
            t.show();
        }
    }

//    public static void showTast(Context context, int resId){
//        try {
//            Toast.makeText(context, context.getResources().getString(resId), Toast.LENGTH_SHORT).show();
//        } catch (Resources.NotFoundException e) {
//            e.printStackTrace();
//        }
//    }
}
