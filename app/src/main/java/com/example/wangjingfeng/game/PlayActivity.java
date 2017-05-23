package com.example.wangjingfeng.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wangjingfeng on 2017/5/20.
 */

public class PlayActivity extends Activity {
    SurfaceView sfv;
    SurfaceHolder sfh;
    Canvas canvas;
    private Paint paint = new Paint();
    private Timer timer;
    private TimerTask task;

    private float x = 50;
    private float y = 0;
    int kind = 0;
    // 定义手势编辑组件
    GestureOverlayView gestureView;
    // 记录手机上已有的手势库
    GestureLibrary gestureLibrary;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.play);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        sfv = (SurfaceView)findViewById(R.id.surfaceView);
        sfh = sfv.getHolder();
        sfh.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                startTimer();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {


            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

                stopTimer();
            }
        });
        gestureLibrary = GestureLibraries
                .fromFile("/sdcard/mygestures");
        gestureView = (GestureOverlayView) findViewById(R.id.gesture);
        gestureView.addOnGesturePerformedListener(
                new GestureOverlayView.OnGesturePerformedListener()
                {
                    @Override
                    public void onGesturePerformed(GestureOverlayView overlay,
                                                   Gesture gesture)
                    {
                        // 识别用户刚刚所绘制的手势
                        ArrayList<Prediction> predictions = gestureLibrary
                                .recognize(gesture);
                        ArrayList<String> result = new ArrayList<String>();
                        //遍历所有找到的Prediction对象
                        for (Prediction pred : predictions)
                        {
                            // 只有相似度大于2.0的手势才会被输出
                            if (pred.score > 2.0)
                            {
                                result.add("与手势【" + pred.name + "】相似度为"
                                        + pred.score);
                            }
                        }
                        if (result.size() > 0)
                        {
                            ArrayAdapter adapter = new ArrayAdapter(
                                    PlayActivity.this,
                                    android.R.layout.simple_dropdown_item_1line, result
                                    .toArray());
                            // 使用一个带List的对话框来显示所有匹配的手势
                            new AlertDialog.Builder(RecogniseGesture.this)
                                    .setAdapter(adapter, null)
                                    .setPositiveButton("确定", null).show();
                        }
                        else
                        {
                            Toast.makeText(RecogniseGesture.this,"无法找到能匹配的手势！" ,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void draw(int height){
        canvas = sfh.lockCanvas();
        canvas.drawColor(Color.WHITE);
        if(y > height){
            kind = (int)(Math.random() * 3);

            y = 0;
        }
        if(kind == 0){
            canvas.drawLine(x, y, x + 50, y, paint);
            canvas.drawLine(x, y, x, y + 100, paint);
            canvas.drawLine(x, y + 100, x + 50, y + 100, paint);
        }
        if(kind == 1){
            canvas.drawLine(x + 50, y, x, y + 100, paint);
            canvas.drawLine(x, y + 100, x + 50, y + 100,paint);
        }
        if(kind == 2){
            RectF rectF = new RectF(x, y, x + 100, y + 100);
            canvas.drawArc(rectF, 0, 180, false, paint);
        }


        y += 20;
        sfh.unlockCanvasAndPost(canvas);

    }
    public void startTimer() {
        timer = new Timer();
        DisplayMetrics dm = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);

        final int screenHeigh = dm.heightPixels;
        task = new TimerTask() {
            @Override
            public void run() {
                //在定时器线程中调用绘图方法
                draw(screenHeigh);
            }
        };
        //设置定时器每隔0.1秒启动这个task,实现动画效果
        timer.schedule(task, 100, 100);
    }
    public void stopTimer() {
        timer.cancel();
    }

}
