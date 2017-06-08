package com.example.mingren.customviewset.activity.info;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.view.View;
import android.widget.TextView;

import com.example.mingren.customviewset.R;
import com.example.mingren.customviewset.view.info.CompleteInfoItemView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vincent on 2017/6/5.
 */

public class CompleteInfoActivity extends Activity {
    @Bind(R.id.fl_goods_info) CompleteInfoItemView fl_goods_info;
    @Bind(R.id.fl_identity_authorize) CompleteInfoItemView fl_identity_authorize;
    @Bind(R.id.fl_face_authorize) CompleteInfoItemView fl_face_authorize;
    @Bind(R.id.fl_bank_card) CompleteInfoItemView fl_bank_card;
    @Bind(R.id.fl_group_photo) CompleteInfoItemView fl_group_photo;
    @Bind(R.id.fl_base_info) CompleteInfoItemView fl_base_info;
    @Bind(R.id.fl_work_info) CompleteInfoItemView fl_work_info;
    @Bind(R.id.fl_contact_info) CompleteInfoItemView fl_contact_info;
    @Bind(R.id.fl_face_grade) CompleteInfoItemView fl_face_grade;
    @Bind(R.id.tv_commit)TextView tv_commit;
    private static final int REQUEST_CODE_GOODS = 0x01;
    private static final int REQUEST_CODE_IDENTITY =0x02;
    private static final int REQUEST_CODE_FACE = 0x03;
    private static final int REQUEST_CODE_BANK =0x04;
    private static final int REQUEST_CODE_GROUP = 0x05;
    private static final int REQUEST_CODE_BASE = 0x06;
    private static final int REQUEST_CODE_WORK =0x07;
    private static final int REQUEST_CODE_CONTACT = 0x08;
    private static final int REQUEST_CODE_FACE_GRADE = 0x09;
    private  boolean isCompleted;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_info);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
    }

    @OnClick({R.id.fl_goods_info, R.id.fl_identity_authorize, R.id.fl_face_authorize, R.id.fl_bank_card, R.id.fl_group_photo, R.id.fl_base_info, R.id.fl_work_info, R.id.fl_contact_info, R.id.fl_face_grade,R.id.tv_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_goods_info:

                break;
            case R.id.fl_identity_authorize:

                break;
            case R.id.fl_face_authorize:

                break;
            case R.id.fl_bank_card:

                break;
            case R.id.fl_group_photo:

                break;
            case R.id.fl_base_info:
                BaseInfoActivity.start(this);
                break;
            case R.id.fl_work_info:
                WorkInfoActivity.start(this);
                break;
            case R.id.fl_contact_info:
                ContactInfoActivity.start(this);
                break;
            case R.id.fl_face_grade:
                FaceGradeActivity.start(this);
                break;
            case R.id.tv_commit:
                if (isCompleted) {
                    checkData();
                    System.out.println("提交信息");
                }
                break;
        }
    }

    private void checkData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            refreshView();
            switch (requestCode) {
                case REQUEST_CODE_GOODS:
                    break;
            }
        }
    }

    private void refreshView() {
        //检查是否所有已填写
        isCompleted = true;
        tv_commit.setEnabled(true);
    }

}
