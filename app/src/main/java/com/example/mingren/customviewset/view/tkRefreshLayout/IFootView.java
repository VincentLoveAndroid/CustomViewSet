package com.example.mingren.customviewset.view.tkRefreshLayout;

import android.view.View;

import com.lcodecore.tkrefreshlayout.IBottomView;

/**
 * Created by vincent on 2017/1/5.
 * email-address:674928145@qq.com
 * description:自定义FootView需要实现的接口
 */

public interface IFootView {

    void showGettingData();

    void showNoMore();

    void showGetDataFail();

    void showNoList();

    void setGettingDataText(String text);

    void setNoMoreText(String text);

    void setGetDataFailText(String text);

}
