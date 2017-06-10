package com.example.mingren.customviewset.activity.info;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mingren.customviewset.R;
import com.example.mingren.customviewset.adapter.OrderStateItemAdapter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.Bind;

public class OrderStateActivity extends Activity {

    @Bind(R.id.lv_list)
    ListView lv_list;

    OrderStateItemAdapter mAdapter;

    TextView tvTitle;

    public static final int STATE_ACTIVATED = 0x01;//已激活
    public static final int STATE_WAIT_SUBMIT = 0x02;//待提交
    public static final int STATE_BEING_REVIEWED = 0x03;//审核中
    public static final int STATE_WAIT_RECEIVE = 0x04;//待收货
    public static final int STATE_REVIEWED_REJECT = 0x05;//审核拒绝
    public static final int STATE_LOSE_EFFICACY = 0x06;//已失效

    @IntDef({STATE_ACTIVATED, STATE_WAIT_SUBMIT, STATE_BEING_REVIEWED, STATE_REVIEWED_REJECT, STATE_LOSE_EFFICACY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface OrderState {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_state);
        init();
    }

    private void init() {

    }

    public void setState(@OrderState int state) {
        switch (state) {
            case STATE_ACTIVATED:
                setTitle("已激活");
                break;
            case STATE_WAIT_SUBMIT:
                setTitle("待提交");
                break;
            case STATE_BEING_REVIEWED:
                setTitle("审核中");
                break;
            case STATE_WAIT_RECEIVE:
                setTitle("待收货");
                break;
            case STATE_REVIEWED_REJECT:
                setTitle("审核拒绝");
                break;
            case STATE_LOSE_EFFICACY:
                setTitle("已失效");
                break;
        }
    }

    private void setTitle(String text) {
        tvTitle.setText(text);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, OrderStateActivity.class);
        context.startActivity(intent);
    }

}
