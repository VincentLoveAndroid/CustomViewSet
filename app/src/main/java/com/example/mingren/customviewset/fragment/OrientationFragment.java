package com.example.mingren.customviewset.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mingren.customviewset.R;
import com.example.mingren.customviewset.helper.ListenerHelper;

import java.lang.ref.WeakReference;

/**
 * Created by vincent on 2017/2/25.
 * email-address:674928145@qq.com
 * description:
 */

public class OrientationFragment extends Fragment {

    private int a = 1;
    private TextView tvTest;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        initView(view);

        setRetainInstance(true);
        return view;
    }

    private void initView(View view) {
        tvTest = (TextView) view.findViewById(R.id.tv_test);
        tvTest.setText("哈哈我是1");
        tvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "哈哈" + ++a, Toast.LENGTH_SHORT).show();
                ListenerHelper.getInstance().doClick(new ListenerHelper.ClickListener() {
                    @Override
                    public void doClick() {

                    }
                });
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("a", a);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            a = savedInstanceState.getInt("a");
        }
    }
}
