package com.example.mingren.customviewset;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import com.example.mingren.customviewset.fragment.OrientationFragment;


public class MainActivity extends FragmentActivity {
    protected FragmentManager mFragmentManager = null;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragmentManager = getSupportFragmentManager();

        findViewById(R.id.tv_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(new OrientationFragment(), R.id.fl_container);
            }
        });

    }


    public void showFragment(Fragment fragment, int id) {
        if (isFinishing()) {
            return;
        }
        if (fragment.isAdded()) {
            mFragmentManager.beginTransaction().show(fragment).commitAllowingStateLoss();
        } else {
            mFragmentManager.beginTransaction().add(id, fragment, fragment.getClass().toString()).commitAllowingStateLoss();
        }
    }

    public void hideFragment(Fragment fragment) {
        if (fragment.isAdded() && !isFinishing()) {
            mFragmentManager.beginTransaction().hide(fragment).commitAllowingStateLoss();
        }

    }
}
