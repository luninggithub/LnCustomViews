package com.ln.custom;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.ln.custom.library.widget.alpha.AlphaImageView;
import com.ln.custom.library.widget.alpha.AlphaRelativeLayout;
import com.ln.custom.library.widget.alpha.AlphaTextView;
import com.ln.custom.library.widget.floating.FloatingAppButton;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VideoView videoView = findViewById(R.id.corner_video);
        // 拼出在资源文件夹下的视频文件路径String字符串
        String url = "android.resource://" + getPackageName() + "/" + R.raw.fire_place;
        Uri uri = Uri.parse(url);
        videoView.setVideoURI(uri);
        videoView.setMediaController(new MediaController(this));
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        videoView.start();

        AlphaImageView btnImg = findViewById(R.id.btn_img);
        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Image button is clicked!");
            }
        });

        AlphaTextView btnTxt = findViewById(R.id.btn_txt);
        btnTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Text button is clicked!");
            }
        });

        AlphaRelativeLayout btnLayout = findViewById(R.id.btn_rl);
        btnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "RelativeLayout button is clicked!");
            }
        });

    }
}