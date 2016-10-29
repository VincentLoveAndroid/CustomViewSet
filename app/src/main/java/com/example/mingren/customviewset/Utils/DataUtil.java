package com.example.mingren.customviewset.Utils;

import com.example.mingren.customviewset.Ob.QQMessageBean;
import com.example.mingren.customviewset.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vincent on 2016/10/26.
 * email-address:674928145@qq.com
 */

public class DataUtil {

    public static List getStringList(int count) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add("努力啊努力啊" + i);
        }
        return list;
    }

    public static List getQQMessageList() {
        List<QQMessageBean> list = new ArrayList<>();
        list.add(new QQMessageBean(R.mipmap.icon_head_2, "马云", "肯德基我买了", "昨天"));
        list.add(new QQMessageBean(R.mipmap.icon_head_3, "马化腾", "我觉得你也前途，好好干", "昨天"));
        list.add(new QQMessageBean(R.mipmap.icon_head_6, "一群逗逼", "...", "昨天"));
        list.add(new QQMessageBean(R.mipmap.icon_head_13, "尼莫", "我的借口没问题", "昨天"));
        list.add(new QQMessageBean(R.mipmap.icon_head_7, "三栗", "榜单那里又要改噢", "昨天"));
        list.add(new QQMessageBean(R.mipmap.icon_head_8, "梅梅", "感觉你瘦了哇", "昨天"));
        list.add(new QQMessageBean(R.mipmap.icon_head_9, "迦娜", "我家的猫又发作了", "昨天"));
        list.add(new QQMessageBean(R.mipmap.icon_head_10, "奶茶mm", "我家东哥今天不在家", "昨天"));
        list.add(new QQMessageBean(R.mipmap.icon_head_11, "刘强东", "今天出去偷食，要一起不", "昨天"));
        list.add(new QQMessageBean(R.mipmap.icon_head_12, "小王", "我又换了个女朋友", "昨天"));
        list.add(new QQMessageBean(R.mipmap.icon_head_4, "苍井空", "今晚，约么", "今天"));
        list.add(new QQMessageBean(R.mipmap.icon_head_5, "angelBaby", "在不?", "昨天"));
        list.add(new QQMessageBean(R.mipmap.icon_head_1, "xc", "你要产生价值", "昨天"));
        return list;
    }
}
