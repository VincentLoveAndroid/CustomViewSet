package com.example.mingren.customviewset.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mingren.customviewset.Ob.FamilyRankBean;
import com.example.mingren.customviewset.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vincent on 2016/10/13.
 * email-address:674928145@qq.com
 */

public class ScrollTabAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FamilyRankBean> textList = new ArrayList<>();
    private Context mContext;
    private boolean firstInit = true;

    public ScrollTabAdapter(Context context) {
        mContext = context;
        for (int i = 0; i < 30; i++) {
            FamilyRankBean bean = new FamilyRankBean();
            bean.text = i + "期";
            textList.add(bean);
        }

    }

    public void addData() {
        for (int i = 29; i < 100; i++) {
            FamilyRankBean bean = new FamilyRankBean();
            bean.text = i + "期";
            textList.add(bean);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View content = View.inflate(mContext, R.layout.view_scroll_tab_content, null);
        return new MyViewHolder(content);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final View rlContent = holder.itemView;
        final TextView tvTxt = (TextView) rlContent.findViewById(R.id.tv_text);
        final ImageView ivArrow = (ImageView) rlContent.findViewById(R.id.iv_arrow);
        final FamilyRankBean rankBean = textList.get(position);

        if (rankBean.currentStage) {
            ivArrow.setVisibility(View.VISIBLE);
            tvTxt.setTextColor(Color.BLUE);
        } else {
            ivArrow.setVisibility(View.INVISIBLE);
            tvTxt.setTextColor(Color.BLACK);
        }
        String text = rankBean.text;
        tvTxt.setText(text);

        rlContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTabClickListener != null) {
                    for (FamilyRankBean text : textList) {
                        text.currentStage = false;
                    }
                    rankBean.currentStage = true;
                    onTabClickListener.onTabClick(rlContent.getLeft(), position, ivArrow, tvTxt);
                }
            }
        });
        if (position == 0 && firstInit) {
            rlContent.performClick();
            firstInit = false;
        }
    }

    @Override
    public int getItemCount() {
        return textList.isEmpty() ? 0 : textList.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }

    private OnTabClickListener onTabClickListener;

    public interface OnTabClickListener {
        void onTabClick(int tabLeft, int position, ImageView ivArrows, TextView tvTxt);
    }

    public void setOnTabClickListener(OnTabClickListener listener) {
        this.onTabClickListener = listener;
    }
}
