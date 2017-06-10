package com.example.mingren.customviewset.activity.info;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.mingren.customviewset.R;
import com.example.mingren.customviewset.Utils.StringUtils;
import com.example.mingren.customviewset.Utils.ToastUtils;
import com.example.mingren.customviewset.Utils.VerifyUtils;
import com.example.mingren.customviewset.view.info.HomePhoneItemView;
import com.example.mingren.customviewset.view.info.InfoItemView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by vincent on 2017/6/5.
 */

public class WorkInfoActivity extends BaseSubmitInfoActivity {
    @Bind(R.id.infoItemView_company)
    InfoItemView infoItemView_company;
    @Bind(R.id.infoItemView_industry)
    InfoItemView infoItemView_industry;
    @Bind(R.id.infoItemView_city)
    InfoItemView infoItemView_city;
    @Bind(R.id.infoItemView_address)
    InfoItemView infoItemView_address;
    @Bind(R.id.infoItemView_house)
    InfoItemView infoItemView_house;
    @Bind(R.id.infoItemView_extension_phone)
    InfoItemView infoItemView_extension_phone;
    @Bind(R.id.homePhoneItemView_home)
    HomePhoneItemView homePhoneItemView_home;
    @Bind(R.id.ll_container)
    LinearLayout ll_container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_info);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, WorkInfoActivity.class);
        context.startActivity(intent);
    }

    @OnClick(R.id.tv_next)
    public void onClick(View view) {
        String company = infoItemView_company.getInputText();
        String industry = infoItemView_industry.getInputText();
        String city = infoItemView_city.getInputText();
        String address = infoItemView_address.getInputText();
        String house = infoItemView_house.getInputText();
        String homePhone = homePhoneItemView_home.getHomePhone();
        String extension_phone = infoItemView_extension_phone.getInputText();
        if (StringUtils.containUnsuppotChar(company)) {
            ToastUtils.showToast(this, "单位名称不能包含特殊字符！");
            return;
        }
        if (StringUtils.containUnsuppotChar(address)) {
            ToastUtils.showToast(this, "单位详细地址不能包含特殊字符！");
            return;
        }

        if (!VerifyUtils.checkHomePhone(this, homePhone)) {
            ToastUtils.showToast(this, "请填写正确的家庭电话号码！");
            return;
        }
        if (!StringUtils.checkExtension(extension_phone)) {
            ToastUtils.showToast(this, "请输入1-4位分机号！");
            return;
        }

        // TODO: 2017/6/9 网络请求
    }


    @Override
    public void submitData() {

    }

    @Override
    public ViewGroup getContainer() {
        return ll_container;
    }


}
