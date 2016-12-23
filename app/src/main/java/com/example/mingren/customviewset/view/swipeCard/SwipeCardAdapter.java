package com.example.mingren.customviewset.view.swipeCard;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mingren.customviewset.R;
import com.example.mingren.customviewset.Utils.ColorUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vincent on 2016/12/20.
 * email-address:674928145@qq.com
 * description:
 */

public class SwipeCardAdapter extends RecyclerView.Adapter<SwipeCardAdapter.MyViewHolder> implements ItemRemoveListener {


    private List<String> dataList = new ArrayList<>();


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.view_swip_card_item, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        View itemView = holder.itemView;
        TextView tvTest = (TextView) itemView.findViewById(R.id.tv_test);
        tvTest.setText(dataList.get(position));
        itemView.setBackgroundColor(ColorUtil.getRandomColor());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public void onItemRemove() {
        removeTopItem();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }


    public void removeTopItem() {
        dataList.remove(dataList.size() - 1);
        notifyItemRemoved(dataList.size());
    }


    public void setDataList() {
        for (int i = 0; i < 100; i++) {
            dataList.add("滑我辣" + i);

        }
    }
}
