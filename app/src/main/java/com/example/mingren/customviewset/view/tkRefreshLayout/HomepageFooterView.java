package com.example.mingren.customviewset.view.tkRefreshLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mingren.customviewset.R;
import com.lcodecore.tkrefreshlayout.IBottomView;

/**
 * Created by MingRen on 2016/4/25.
 */
public class HomepageFooterView extends RelativeLayout implements IFootView, IBottomView {

    private LinearLayout llGettingData;
    private TextView tvNoMore;
    private TextView tvGettingData;
    private TextView tvGetDataFail;
    private TextView tvnoList;

    public HomepageFooterView(Context context) {
        super(context);
        initView();
    }

    public HomepageFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        View view = View.inflate(getContext(), R.layout.view_homepage_footer, this);
        llGettingData = (LinearLayout) view.findViewById(R.id.ll_foot_getting_data);
        tvGettingData = (TextView) view.findViewById(R.id.tv_foot_getting_data);
        tvNoMore = (TextView) view.findViewById(R.id.tv_foot_no_more);
        tvGetDataFail = (TextView) view.findViewById(R.id.tv_foot_get_data_fail);
        tvnoList = (TextView) view.findViewById(R.id.tv_foot_no_list);
        setVisibility(INVISIBLE);
    }

    private void changeState(View showView) {
        this.setVisibility(View.VISIBLE);
        llGettingData.setVisibility(View.INVISIBLE);
        tvNoMore.setVisibility(View.INVISIBLE);
        tvGetDataFail.setVisibility(View.INVISIBLE);
        tvnoList.setVisibility(View.INVISIBLE);
        showView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showGettingData() {
        changeState(llGettingData);
    }

    @Override
    public void showNoMore() {
        changeState(tvNoMore);
    }

    @Override
    public void showGetDataFail() {
        changeState(tvGetDataFail);
    }

    @Override
    public void showNoList() {
        changeState(tvnoList);
    }

    @Override
    public void setGettingDataText(String text) {
        tvGettingData.setText(text);
    }

    @Override
    public void setNoMoreText(String text) {
        tvNoMore.setText(text);
    }

    @Override
    public void setGetDataFailText(String text) {
        tvGetDataFail.setText(text);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onPullingUp(float fraction, float maxHeadHeight, float headHeight) {

    }

    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {

    }

    @Override
    public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void reset() {

    }
}
