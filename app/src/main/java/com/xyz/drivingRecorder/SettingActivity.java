package com.xyz.drivingRecorder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity {
    private SeekBar seekBarProgressSensitivity;
    private TextView textViewSensitivity;

    private SeekBar seekBarProgressVideoSize;
    private TextView textViewVideoSize;

    private SeekBar seekBarProgressVideoStorageSize;
    private TextView textViewVideoStorageSize;

    private Button btnClean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
    }

    private void initView() {
        seekBarProgressSensitivity = (SeekBar) findViewById(R.id.progress_Sensitivity);
        textViewSensitivity = (TextView) findViewById(R.id.text_Sensitivity);

        seekBarProgressVideoSize = (SeekBar) findViewById(R.id.progress_video_size);
        textViewVideoSize = (TextView) findViewById(R.id.text_video_size);

        seekBarProgressVideoStorageSize = (SeekBar) findViewById(R.id.progress_storage_size);
        textViewVideoStorageSize = (TextView) findViewById(R.id.text_storage_size);

        int collisionDetectionSensitivity = SettingDataModel.instance().getCollisionDetectionSensitivity();
        seekBarProgressSensitivity.setProgress(collisionDetectionSensitivity);
        textViewSensitivity.setText(Integer.toString(collisionDetectionSensitivity));

        seekBarProgressSensitivity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress >= 10) {
                    textViewSensitivity.setText(Integer.toString(progress));
                    SettingDataModel.instance().setCollisionDetectionSensitivity(progress);
                } else {
                    if (seekBar.getProgress() != 10) {
                        Toast.makeText(SettingActivity.this, "最小10", Toast.LENGTH_SHORT).show();
                        seekBar.setProgress(10);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        int videoFileTimeSize = SettingDataModel.instance().getVideoFileTimeSize();
        seekBarProgressVideoSize.setProgress(videoFileTimeSize);
        textViewVideoSize.setText(Integer.toString(videoFileTimeSize));
        seekBarProgressVideoSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewVideoSize.setText(Integer.toString(progress));
                SettingDataModel.instance().setVideoFileTimeSize(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        int videoStorageSize = SettingDataModel.instance().getVideoStorageSize();
        seekBarProgressVideoStorageSize.setProgress(videoStorageSize);
        textViewVideoStorageSize.setText(Integer.toString(videoStorageSize));
        seekBarProgressVideoStorageSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewVideoStorageSize.setText(Integer.toString(progress));
                SettingDataModel.instance().setVideoStorageSize(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        btnClean = (Button)findViewById(R.id.button_clean);
        btnClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = VideoRecordeActivity.getStoragePathBase();
                DeleteFileUtil.deleteDirectory(path);

                VideoDataModel videoDataModel = new VideoDataModel();
                videoDataModel.setContext(SettingActivity.this);
                VideoDataModel.VideoMetaData videoMetaData = new VideoDataModel.VideoMetaData();
                ArrayList<VideoDataModel.VideoMetaData> list = videoDataModel.queryAll();
                for (VideoDataModel.VideoMetaData data : list) {
                    videoDataModel.deleteVideoMetaData(data.getId());
                }

                Toast.makeText(SettingActivity.this, "清空完毕", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
