package com.example.mingren.customviewset.view.info;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vincent on 2017/5/31.
 */

public class ShadeItemAdapter extends RecyclerView.Adapter<ShadeItemAdapter.MyViewHolder> {

    List<InfoSelectBean> dataList = new ArrayList<>();
    SelectItemView.OperateListener operateListener;


    public ShadeItemAdapter(List dataList, SelectItemView.OperateListener operateListener) {
        this.dataList = dataList;
        this.operateListener = operateListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ShadeItemView view = new ShadeItemView(parent.getContext());
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final InfoSelectBean infoSelectBean = dataList.get(position);
        if (infoSelectBean == null) return;
        ShadeItemView itemView = (ShadeItemView) holder.itemView;
        itemView.setSelected(infoSelectBean.isSelected());
        itemView.setName(infoSelectBean.getItemName());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (InfoSelectBean bean: dataList) {
                    bean.setSelected(false);
                }
                infoSelectBean.setSelected(true);
                notifyDataSetChanged();
                if(operateListener!=null) operateListener.onSelect(infoSelectBean.getItemName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
