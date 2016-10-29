package com.example.mingren.customviewset.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mingren.customviewset.Ob.QQMessageBean;
import com.example.mingren.customviewset.R;
import com.example.mingren.customviewset.Utils.ColorUtil;
import com.example.mingren.customviewset.Utils.DensityUtil;
import com.example.mingren.customviewset.Utils.ScreenUtils;
import com.example.mingren.customviewset.interfaces.ItemDragCallback;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by vincent on 2016/10/26.
 * email-address:674928145@qq.com
 * description:为了解耦实现了ItemDragCallback
 */

public class DragMessageItemAdapter extends RecyclerView.Adapter<DragMessageItemAdapter.MyViewHolder> implements ItemDragCallback {

    List<QQMessageBean> dataList = new ArrayList<>();


    public DragMessageItemAdapter(List dataList) {
        this.dataList = dataList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.view_qq_message_item, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        QQMessageBean messageBean = dataList.get(holder.getAdapterPosition());
        holder.ivHead.setImageResource(messageBean.headResId);
        holder.tvName.setText(messageBean.name);
        holder.tvContent.setText(messageBean.content);
        holder.tvTime.setText(messageBean.time);
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public void onItemMove(int oldPosition, int targetPosition) {
        Collections.swap(dataList, oldPosition, targetPosition);
        notifyItemMoved(oldPosition, targetPosition);
    }

    @Override
    public void onItemSwiped(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView ivHead;
        public TextView tvName;
        public TextView tvContent;
        public TextView tvTime;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivHead = (CircleImageView) itemView.findViewById(R.id.iv_head);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }
}
