package com.example.mingren.customviewset.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mingren.customviewset.Ob.CityBean;
import com.example.mingren.customviewset.R;
import com.example.mingren.customviewset.adapter.CityParserTask;
import com.example.mingren.customviewset.adapter.CitySelectAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by vincent on 2017/5/25.
 */

public class SelectCityView extends LinearLayout implements View.OnClickListener,CitySelectAdapter.OnItemClickListener,CityParserTask.CityParserCallBack{
    View v_indicator;
    TextView tv_province, tv_city, tv_county;
    RecyclerView rcv_select_list;
    private ArrayList<CityBean> provinces;
    private ArrayList<ArrayList<CityBean>> citys;
    private ArrayList<ArrayList<ArrayList<CityBean>>> areas;
    private CitySelectAdapter citySelectAdapter;

    public SelectCityView(Context context) {
        this(context, null);
    }

    public SelectCityView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        View.inflate(context, R.layout.view_info_city_pop_window, this);
        tv_province = (TextView) findViewById(R.id.tv_province);
        tv_city = (TextView) findViewById(R.id.tv_city);
        tv_county = (TextView) findViewById(R.id.tv_county);
        v_indicator = findViewById(R.id.v_indicator);
        rcv_select_list = (RecyclerView) findViewById(R.id.rcv_select_list);
        rcv_select_list.setLayoutManager(new LinearLayoutManager(context));
        citySelectAdapter = new CitySelectAdapter();
        citySelectAdapter.setOnItemClickListener(this);
        tv_province.setOnClickListener(this);
        tv_city.setOnClickListener(this);
        tv_county.setOnClickListener(this);

        new CityParserTask().setCityParserCallBack(this).execute();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_province:
                v_indicator.animate().x(tv_province.getLeft()).setDuration(500);
                break;
            case R.id.tv_city:
                v_indicator.animate().x(tv_city.getLeft()).setDuration(500);
                break;
            case R.id.tv_county:
                v_indicator.animate().x(tv_county.getLeft()).setDuration(500);
                break;

        }

    }

    @Override
    public void onItemClick(String text, int type) {
        if (type == CitySelectAdapter.TYPE_PROVINCE) {
            tv_province.setText(text);
        } else if (type == CitySelectAdapter.TYPE_CITY) {
            tv_city.setText(text);
        } else {
            tv_county.setText(text);
        }
    }

    @Override
    public void onParseCitys(CityParserTask.CitysEntity citysEntity) {
        provinces = citysEntity.getProvinces();
        citys = citysEntity.getCitys();
        areas = citysEntity.getAreas();
        citySelectAdapter.setCityData(provinces, citys, areas);
        rcv_select_list.setAdapter(citySelectAdapter);
    }
}
