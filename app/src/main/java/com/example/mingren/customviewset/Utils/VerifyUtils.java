package com.example.mingren.customviewset.Utils;

import android.content.Context;
import android.text.TextUtils;


/**
 * Created by wenghua on 16/8/18.
 * 功能：字段校验类
 */
public class VerifyUtils {
    /**
     * 姓名格式的校验，允许输入 ·
     * @param context
     * @param name
     * @return
     */
    public static boolean checkName(Context context, String name, String toastStr){
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showToast(context, toastStr+"不能为空");
            return false;
        } else {
            String strName = name;
            if(name.contains("·")){
                strName = name.replaceAll("·","");
            }
            String reg = "[\\u4e00-\\u9fa5]+";
            if (!strName.matches(reg) || strName.length() < 2) {
                ToastUtils.showToast(context, "请填写正确的"+toastStr);
                return false;
            }
        }
        return true;
    }

    /**
     * 身份证号码的校验
     * @param context
     * @param idCard
     * @return
     */
    public static boolean checkIdCard(Context context, String idCard){
        if (TextUtils.isEmpty(idCard)) {
            ToastUtils.showToast(context, "身份证号不能为空");
            return false;
        } else {
            IDCard idUtil = new IDCard();
            boolean result = idUtil.verify(idCard);
            if (!result) {  //客户段时间不准确,统一由后端校验
                ToastUtils.showToast(context, "请输入正确的身份证号");
                return false;
            }
        }
        return true;
    }

    /**
     * 校验详细地址
     * @param context
     * @param address
     * @return
     */
    public static boolean checkAddress(Context context, String address){
        if (TextUtils.isEmpty(address)) {
            ToastUtils.showToast(context, "请填写您的详细地址");
            return false;
        } else if(StringUtils.containUnsuppotChar(address)){
            ToastUtils.showToast(context, "详细地址不能包含特殊字符");
            return false;
        }
        return true;
    }

    /**
     * 校验qq号码
     * @param context
     * @param qq
     * @return
     */
    public static boolean checkQQ(Context context, String qq){
        if(TextUtils.isEmpty(qq)){
            ToastUtils.showToast(context, "请填写您的QQ号！");
            return false;
        }
        int length = qq.length();
        if (length < 6 || length > 18 || qq.startsWith("0")) {
            ToastUtils.showToast(context, "请填写正确的qq号码！");
            return false;
        }
        return true;
    }

    /**
     * 校验微信号
     * @param context
     * @param wechat
     * @return
     */
    public static boolean checkWeChat(Context context, String wechat){
        if(TextUtils.isEmpty(wechat)){
            ToastUtils.showToast(context, "请填写您的微信号");
            return false;
        }
        if(!StringUtils.checkWechat(wechat)){
            ToastUtils.showToast(context, "请填写正确的微信号码");
            return false;
        }
        return true;
    }

    public static boolean checkHomePhone(Context context, String homePhone){
        if (!TextUtils.isEmpty(homePhone)) {
            if (homePhone.contains("－")) {
                homePhone = homePhone.replace("－", "-");
            }
            if (!StringUtils.checkHomeNumber(homePhone)) {
                ToastUtils.showToast(context, "家庭电话请依照格式:xxxx-xxxxxxxx输入");
                return false;
            }
        }
        return true;
    }

    public static boolean checkStrEmpty(String str){
        return (TextUtils.isEmpty(str) || str.equals("null"));
    }
}
