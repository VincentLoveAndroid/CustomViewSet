package com.example.mingren.customviewset.activity.info;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mingren.customviewset.R;
import com.example.mingren.customviewset.Utils.StringUtils;
import com.example.mingren.customviewset.Utils.ToastUtils;
import com.example.mingren.customviewset.Utils.VerifyUtils;
import com.example.mingren.customviewset.view.info.HomePhoneItemView;
import com.example.mingren.customviewset.view.info.InfoItemView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vincent on 2017/6/5.
 */

public class BaseInfoActivity extends Activity {
    private static final String TAG = "BaseInfoActivity";
    @Bind(R.id.infoItemView_chat)
    InfoItemView infoItemView_chat;
    @Bind(R.id.infoItemView_account)
    InfoItemView infoItemView_account;
    //    @Bind(R.id.infoItemView_city)
//    InfoItemView infoItemView_city;
    @Bind(R.id.infoItemView_address)
    InfoItemView infoItemView_address;
    @Bind(R.id.infoItemView_house)
    InfoItemView infoItemView_house;
    @Bind(R.id.infoItemView_education)
    InfoItemView infoItemView_education;
    @Bind(R.id.infoItemView_married)
    InfoItemView infoItemView_married;
    @Bind(R.id.infoItemView_income)
    InfoItemView infoItemView_income;
    @Bind(R.id.tv_next)
    TextView tv_next;
    @Bind(R.id.homePhoneItemView_home)
    HomePhoneItemView homePhoneItemView_home;
    @Bind(R.id.ll_root)
    LinearLayout ll_root;
    boolean isFill;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_info);
        ButterKnife.bind(this);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, BaseInfoActivity.class);
        context.startActivity(intent);
    }

    @OnClick(R.id.tv_next)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_next:
                // if (isFill) {
                boolean isAllItemFill = checkAllItemFillAndTips();
                if (isAllItemFill) {
                    submitData();
                }
                // }
                break;
        }
    }

    private void submitData() {
        String chatWay = infoItemView_chat.getInputText();
        String account = infoItemView_account.getInputText();
//        String city = infoItemView_city.getInputText();
        String address = infoItemView_address.getInputText();
        String house = infoItemView_house.getInputText();
        String education = infoItemView_education.getInputText();
        String married = infoItemView_married.getInputText();
        String income = infoItemView_income.getInputText();
        String homePhone = homePhoneItemView_home.getHomePhone();
        if ("QQ".equals(chatWay)) {
            boolean isQQ = VerifyUtils.checkQQ(this, account);
            if (!isQQ) return;
        } else if ("微信".equals(chatWay)) {
            boolean isWechat = VerifyUtils.checkWeChat(this, account);
            if (!isWechat) return;
        }

        if (StringUtils.containUnsuppotChar(address)) {
            ToastUtils.showToast(this, "详细居住地址不能包含特殊符号！");
            return;
        }

        if (!VerifyUtils.checkHomePhone(this, homePhone)) {
            ToastUtils.showToast(this, "请填写正确的家庭电话号码！");
            return;
        }
        //网络请求
        Log.i(TAG, "提交数据" + chatWay + " " + account + " " + address + " " + house + " " + married + " " + income + " ");

    }

    @Override
    protected void onResume() {
        super.onResume();
        checkData();
    }

    private void checkData() {
        if (!isAllItemFill()) return;
        isFill = true;
        tv_next.setEnabled(true);
    }

    private boolean isAllItemFill() {
        if (!isAllInfoItemFill(false)) return false;
        //如需要，在这里检查没有实现ItemWatcher接口的控件
        return true;
    }

    /**
     * 遍历检查InfoItem是否未填写
     */
    private boolean isAllInfoItemFill(boolean showEmptyTip) {
        for (int i = 0; i < ll_root.getChildCount(); i++) {
            View child = ll_root.getChildAt(i);
            if (child.getVisibility() == View.VISIBLE && child instanceof ItemWatcher) {
                ItemWatcher watcher = ((ItemWatcher) child);
                if (!watcher.isCompleted()) {
                    if (showEmptyTip) watcher.showEmptyTip();
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkAllItemFillAndTips() {
        if (!isAllInfoItemFill(true)) return false;
        //如需要，在这里检查没有实现ItemWatcher接口的控件
        return true;
    }
}
