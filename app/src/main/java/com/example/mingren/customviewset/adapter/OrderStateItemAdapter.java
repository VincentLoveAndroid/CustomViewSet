package com.example.mingren.customviewset.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mingren.customviewset.R;
import com.example.mingren.customviewset.activity.info.OrderStateActivity;

/**
 * Created by vincent on 2017/6/9.
 */

public class OrderStateItemAdapter extends BaseAdapter {

    public void setState(@OrderStateActivity.OrderState int state) {
        switch (state) {
            case OrderStateActivity.STATE_ACTIVATED:

                break;
            case OrderStateActivity.STATE_WAIT_SUBMIT:

                break;
            case OrderStateActivity.STATE_BEING_REVIEWED:

                break;
            case OrderStateActivity.STATE_WAIT_RECEIVE:

                break;
            case OrderStateActivity.STATE_REVIEWED_REJECT:

                break;
            case OrderStateActivity.STATE_LOSE_EFFICACY:

                break;
        }
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_state, parent);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.tvPhone.setText("");
        viewHolder.tvName.setText("");
        viewHolder.tvTips.setText("");
        viewHolder.tvInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }

    static class ViewHolder {
        TextView tvPhone;
        TextView tvName;
        TextView tvInfo;
        TextView tvTips;

        ViewHolder(View view) {
            tvPhone = (TextView) view.findViewById(R.id.tv_customer_phone);
            tvName = (TextView) view.findViewById(R.id.tv_customer_name);
            tvInfo = (TextView) view.findViewById(R.id.tv_info);
            tvTips = (TextView) view.findViewById(R.id.tv_tips);
        }
    }
}
