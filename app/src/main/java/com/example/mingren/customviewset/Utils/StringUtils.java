package com.example.mingren.customviewset.Utils;


import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * 字符串操作工具包
 * @author DavikChen
 */
public class StringUtils {

    private final static Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*"); // 邮箱验证正则表达式
//    private final static Pattern phone = Pattern.compile("^[1][3,4,5,6,7,8,9][0-9]{9}$");         //手机号正则表达式
    private final static Pattern homePhone = Pattern.compile("^(0\\d{2,3}-)\\d{7,8}$");         //家庭正则表达式
    private final static Pattern homePhone_l = Pattern.compile("^(0\\d{2,3}-)\\d{7,11}$");         //家庭正则表达式10-15位
    private final static Pattern extension= Pattern.compile("(^\\d{1,4}$)");//分机号
    private final static Pattern wechat= Pattern.compile("^[a-zA-Z0-9_-]+$");//微信号
    private final static Pattern letterAndNumbers= Pattern.compile("^[a-zA-Z0-9]+$");   //字母数字组合
    private final static Pattern letters= Pattern.compile("^[a-zA-Z]+$");   //字母
    private final static Pattern numbers= Pattern.compile("^[0-9]+$");  //数字
    private final static Pattern unsupportChar= Pattern.compile("[|'\"]+");  //不允许输入的特殊字符
    private final static Pattern chinese= Pattern.compile("[\\u4e00-\\u9fa5]+");  //不允许输入的特殊字符
    private final static Pattern imei= Pattern.compile("^[0-9]{15}$");  //不允许输入的特殊字符
    private final static Pattern mobile = Pattern.compile("^1(3[0-9]|4[57]|5[0-35-9]|8[0-9]|7[0-9])\\d{8}$"); //精确的手机号码
    private final static Pattern letterAndNumbersAndChinese= Pattern.compile("^[\\u4e00-\\u9fa5a-zA-z0-9\\(\\)（）]+$");   //字母数字汉字组合

    private final static SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final static SimpleDateFormat dateFormater2 = new SimpleDateFormat("yyyy-MM-dd");


    /**
     * 将字符串转位日期类型
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        try {
            return dateFormater.parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 将日期类型转位字符串
     * @param sdate
     * @return
     */
    public static String toYYYYMMDD(Date sdate) {
        try {
            return dateFormater2.format(sdate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将日期类型转位字符串
     * @param sdate
     * @return
     */
    public static String toYYYYMMDDHHMMSS(Date sdate) {
        try {
            return dateFormater.format(sdate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断给定字符串时间是否为今日
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(String sdate) {
        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater2.format(today);
            String timeDate = dateFormater2.format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0)
            return false;
        return emailer.matcher(email).matches();
    }

    /**
     * 字符串转整数
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 对象转整数
     * @param obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null)
            return 0;
        return toInt(obj.toString(), 0);
    }

    /**
     * 对象转整数
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 字符串转布尔值
     * @param b
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 检测是否合法手机号
     * @return
     */
    public static  boolean checkPhoneNumber(String phoneStr){
        if(TextUtils.isEmpty(phoneStr)){
           return false;
        } else {
            return mobile.matcher(phoneStr).find();
        }
    }
    public static  boolean checkHomeNumber(String phoneStr){
        if(TextUtils.isEmpty(phoneStr)){
            return false;
        } else {
            return homePhone.matcher(phoneStr).find();
        }
    }

    public static  boolean checkHomeNumberLong(String phoneStr){
        if(TextUtils.isEmpty(phoneStr)){
            return false;
        } else {
            return homePhone_l.matcher(phoneStr).find();
        }
    }

    public static  boolean checkExtension(String phoneStr){
        if(TextUtils.isEmpty(phoneStr)){
            return false;
        } else {
            return extension.matcher(phoneStr).find();
        }
    }

    /**
     * 校验微信号
     * @param wc
     * @return
     */
    public static  boolean checkWechat(String wc){
        if(TextUtils.isEmpty(wc)){
            return false;
        } else {
            return wechat.matcher(wc).find();
        }
    }
    /**
     * 校验字母\数字,汉字
     * @param wc
     * @return
     */
    public static  boolean checkLetterAndNumAndChinese(String wc){
        if(TextUtils.isEmpty(wc)){
            return false;
        } else {
            return letterAndNumbersAndChinese.matcher(wc).find();
        }
    }

    /**
     * 校验字母\数字
     * @param wc
     * @return
     */
    public static  boolean checkLetterAndNum(String wc){
        if(TextUtils.isEmpty(wc)){
            return false;
        } else {
            return letterAndNumbers.matcher(wc).find();
        }
    }

    /**
     * 校验数字
     * @param wc
     * @return
     */
    public static  boolean checkNum(String wc){
        if(TextUtils.isEmpty(wc)){
            return false;
        } else {
            return numbers.matcher(wc).find();
        }
    }

    /**
     * 校验imei号
     * @param wc
     * @return
     */
    public static  boolean checkIMEI(String wc){
        if(TextUtils.isEmpty(wc)){
            return false;
        } else {
            return imei.matcher(wc).matches();
        }
    }

    /**
     * 检测输入中是否含有指定特殊字符
     * @param str
     * @return
     */
    public static boolean containUnsuppotChar(String str){
        if(TextUtils.isEmpty(str)){
            return true;
        } else {
            return unsupportChar.matcher(str).find();
        }
    }

    /**
     * 检测输入中是否中文
     * @param str
     * @return
     */
    public static boolean isChinese(String str){
        if(TextUtils.isEmpty(str)){
            return true;
        } else {
            return chinese.matcher(str).matches();
        }
    }

    public static String hideMobileMiddle(String mobile){
        if(TextUtils.isEmpty(mobile)){
            return mobile;
        }
        if(mobile.length() != 11){
            return mobile;
        }
        return mobile.substring(0,3)+"****"+mobile.substring(7,11);
    }

    public static String replaceBlank(String input){
        if(!TextUtils.isEmpty(input)){
            return input.replace("\r", "").replace("\n", "").replace("\t","");
        }
        return input;
    }

//    public static void main(String[] arg){
//        String addr = "1234123412313q4";
//        System.out.print("++++"+checkIMEI(addr));
//    }
}