package com.example.mingren.customviewset.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mingren.customviewset.Ob.CityBean;
import com.example.mingren.customviewset.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by vincent on 2017/5/27.
 */

public class CitySelectAdapter extends RecyclerView.Adapter<CitySelectAdapter.MyViewHolder> {

    private int itemType=TYPE_PROVINCE;
    public static final int TYPE_PROVINCE = 1 << 1;
    public static final int TYPE_CITY = 1 << 2;
    public static final int TYPE_COUNTY = 1 << 3;
    private ArrayList<CityBean> provinces;
    private ArrayList<ArrayList<CityBean>> cities;
    private ArrayList<ArrayList<ArrayList<CityBean>>> areas;
    private int provinceIndex = -1;
    private int cityIndex = -1;
    private int areaIndex = -1;


    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public void setCityData(ArrayList<CityBean> provinces, ArrayList<ArrayList<CityBean>> cities, ArrayList<ArrayList<ArrayList<CityBean>>> areas) {
        this.provinces = provinces;
        this.cities = cities;
        this.areas = areas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_city_select_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        System.out.println("position"+position);
        String text = "";
        switch (itemType) {
            case TYPE_PROVINCE:
                text = provinces.get(position).getCityName();
                holder.initItem(holder.tvProvince, text);
                break;
            case TYPE_CITY:
                text = cities.get(provinceIndex).get(position).getCityName();
                System.out.println(provinceIndex+ ""+position);
                holder.initItem(holder.tvCity, text);
                break;
            case TYPE_COUNTY:
                text = areas.get(provinceIndex).get(cityIndex).get(position).getCityName();
                holder.initItem(holder.tvCounty, text);
                break;
        }
        final String s = text;
        final int tempType = itemType;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick(s, tempType);
                refreshData(tempType, position);
            }
        });
    }

    private void refreshData(int tempType, int position) {
        switch (tempType) {
            case TYPE_PROVINCE:
                provinceIndex = position;
                itemType = TYPE_CITY;
                break;
            case TYPE_CITY:
                cityIndex = position;
                itemType = TYPE_COUNTY;
                break;
            case TYPE_COUNTY:
                areaIndex = position;
                itemType = -1;
                break;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        switch (itemType) {
            case TYPE_PROVINCE:
                return provinces == null ? 0 : provinces.size();
            case TYPE_CITY:
                return cities == null ? 0 : cities.get(provinceIndex).size();
            case TYPE_COUNTY:
                return areas == null ? 0 : areas.get(provinceIndex).get(cityIndex).size();
        }
        return 0;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvProvince, tvCity, tvCounty;

        MyViewHolder(View itemView) {
            super(itemView);
            tvProvince = (TextView) itemView.findViewById(R.id.tv_province);
            tvCity = (TextView) itemView.findViewById(R.id.tv_city);
            tvCounty = (TextView) itemView.findViewById(R.id.tv_county);
        }

        void initItem(TextView tvSelect, String text) {
            tvProvince.setVisibility(View.INVISIBLE);
            tvCity.setVisibility(View.INVISIBLE);
            tvCounty.setVisibility(View.INVISIBLE);
            tvSelect.setVisibility(View.VISIBLE);
            tvSelect.setText(text);
        }
    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(String text,int type);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void itemClick(String s, int tempType) {
        if (onItemClickListener != null) onItemClickListener.onItemClick(s,tempType);
    }
}
