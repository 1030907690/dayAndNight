/*
package com.dan.dayandnight.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.dan.dayandnight.R;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class SimpleMediaActivity extends AppCompatActivity   implements View.OnClickListener,
        SurfaceHolder.Callback, IMediaPlayer.OnPreparedListener,
        IMediaPlayer.OnCompletionListener,IMediaPlayer.OnSeekCompleteListener,
        IMediaPlayer.OnBufferingUpdateListener,SeekBar.OnSeekBarChangeListener
 {
    IjkMediaPlayer mediaPlayer;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private Button mButtonone;
    private Button mButtontwo;
    private SeekBar seekbar;
    int currentP;
    boolean isSeek;
    boolean isprd;

     @Override
     public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i) {

     }

     @Override
     public void onSeekComplete(IMediaPlayer iMediaPlayer) {

     }

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_media_main);
        initView();

    }
    private String mUriTwo = "https://youku.rebo5566.com/20190808/QCgZI4gD/index.m3u8";

    private void initView() {
        mButtonone =(Button)findViewById(R.id.buttonOne);
        mButtontwo =(Button)findViewById(R.id.buttonTwo);
        seekbar = (SeekBar) findViewById(R.id.seekbar);
        seekbar.setOnSeekBarChangeListener(this);
        mButtonone.setOnClickListener(this);
        mButtontwo.setOnClickListener(this);
        surfaceView= (SurfaceView)findViewById(R.id.surfaceView);
        surfaceHolder=surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        mediaPlayer=new IjkMediaPlayer();//直接new个IjkMediaPlayer 就行
        try {
            mediaPlayer.setDataSource(mUriTwo);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //mediaPlayer准备工作
        mediaPlayer.setOnPreparedListener(this);
//MediaPlayer完成
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);

    }


     @Override
     public void onCompletion(IMediaPlayer iMediaPlayer) {

     }

     @Override
     public void surfaceCreated(SurfaceHolder holder) {
         //连接ijkPlayer 和surfaceHOLDER
         mediaPlayer.setDisplay(holder);
         //开启异步准备
         mediaPlayer.prepareAsync();
     }

     @Override
     public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

     }

     @Override
     public void surfaceDestroyed(SurfaceHolder holder) {
         if (mediaPlayer.isPlaying()){
             mediaPlayer.pause();
         }

     }

     */
/**
      * 当异步开启后 执行这个方法
      * @param iMediaPlayer
      *//*

     @Override
     public void onPrepared(IMediaPlayer iMediaPlayer) {

         isprd = true;
         //设置seekbar的长度为视频播放的长度
         seekbar.setMax((int)iMediaPlayer.getDuration());
         //开启异步
         handler.post(runnable);
         //开启异步后视频会自动播放 所以需要暂停
         if (mediaPlayer.isPlaying()){
             mediaPlayer.pause();
         }

     }
     Handler handler = new Handler();
     Runnable runnable = new Runnable() {
         @Override
         public void run() {
             //加个开关 在刷进度条的时候先判断是不是正在拖动
             if (isSeek)
                 return;
             //获取当前视频播放的位置
             int cur = (int)mediaPlayer.getCurrentPosition();
             if(cur > 0 && cur != currentP){
                 currentP = cur;
                 seekbar.setProgress(currentP);//设置进度条
             }
             //每50毫秒刷新一下进度条
             handler.postDelayed(runnable,50);
         }
     };





     @Override
     public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

     }

     @Override
     public void onStartTrackingTouch(SeekBar seekBar) {
         //一个开关 当开始拖动时改变成true
         isSeek = true;
         //判断是不是正在播放 如果正在播放就暂停
         if (mediaPlayer.isPlaying()){
             mediaPlayer.pause();
         }

     }

     @Override
     public void onStopTrackingTouch(SeekBar seekBar) {
         //一个开关 当开始拖动完成时改变乘false
         isSeek = false;
         //设置到当前拖动进度条的位置 然后start
         mediaPlayer.seekTo(seekBar.getProgress());
         mediaPlayer.start();
     }
    // 播放暂停按钮:
     @Override
     public void onClick(View v) {
         switch (v.getId()) {
             case R.id.buttonOne:
                 mediaPlayer.pause();
                 break;
             case R.id.buttonTwo:
                 if (!isprd) {
                     return;
                 }
                 if (!mediaPlayer.isPlaying()) {
                     mediaPlayer.start();
                 }
                 break;
         }


     }


     @Override
     public void finish() {
         super.finish();
     }

     @Override
     protected void onDestroy() {
         super.onDestroy();
     }
 }
*/
