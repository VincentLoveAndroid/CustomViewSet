package com.example.mingren.customviewset.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;

import com.example.mingren.customviewset.R;
import com.example.mingren.customviewset.view.RadarView;

public class RadarActivity extends Activity implements SeekBar.OnSeekBarChangeListener {

    private RadarView radarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar);

        radarView = (RadarView) findViewById(R.id.radar_view);

        ((SeekBar) findViewById(R.id.sb_behavior)).setOnSeekBarChangeListener(new SeekBarListener(RadarView.TYPE_BEHAVIOR));
        ((SeekBar) findViewById(R.id.sb_contacts)).setOnSeekBarChangeListener(new SeekBarListener(RadarView.TYPE_CONTACTS));
        ((SeekBar) findViewById(R.id.sb_history)).setOnSeekBarChangeListener(new SeekBarListener(RadarView.TYPE_HISTORY));
        ((SeekBar) findViewById(R.id.sb_identity)).setOnSeekBarChangeListener(new SeekBarListener(RadarView.TYPE_IDENTITY));
        ((SeekBar) findViewById(R.id.sb_performance)).setOnSeekBarChangeListener(new SeekBarListener(RadarView.TYPE_PERFORMANCE));

    }

    private class SeekBarListener implements SeekBar.OnSeekBarChangeListener {

        private int type;

        public SeekBarListener(int type) {
            this.type = type;
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            radarView.refreshScore(type, progress);

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
