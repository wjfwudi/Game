package com.example.wangjingfeng.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

/**
 * Created by wangjingfeng on 2017/5/22.
 */

public class AddGesture extends Activity{
    EditText editText;
    GestureOverlayView gestureView;
    Button button;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        // 获取文本编辑框
        editText = (EditText) findViewById(R.id.gesture_name);
        // 获取手势编辑视图
        gestureView = (GestureOverlayView) findViewById(R.id.gesture);
        // 设置手势的绘制颜色
        gestureView.setGestureColor(Color.RED);
        // 设置手势的绘制宽度
        gestureView.setGestureStrokeWidth(4);
        // 为gesture的手势完成事件绑定事件监听器
        gestureView.addOnGesturePerformedListener(
                new GestureOverlayView.OnGesturePerformedListener()
                {
                    @Override
                    public void onGesturePerformed(GestureOverlayView overlay,
                                                   final Gesture gesture)
                    {
                        //加载save.xml界面布局代表的视图
                        View saveDialog = getLayoutInflater().inflate(
                                R.layout.save, null);
                        // 获取saveDialog里的show组件
                        ImageView imageView = (ImageView) saveDialog
                                .findViewById(R.id.show);
                        // 获取saveDialog里的gesture_name组件
                        final EditText gestureName = (EditText) saveDialog
                                .findViewById(R.id.gesture_name);
                        // 根据Gesture包含的手势创建一个位图
                        Bitmap bitmap = gesture.toBitmap(128, 128, 10, 0xFFFF0000);
                        imageView.setImageBitmap(bitmap);
                        //使用对话框显示saveDialog组件
                        new AlertDialog.Builder(AddGesture.this)
                                .setView(saveDialog)
                                .setPositiveButton("保存", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which)
                                    {
                                        File g = new File(Environment.getExternalStorageDirectory(),"mygestures");
                                        // 获取指定文件对应的手势库
                                        try{
                                            if(!g.exists())
                                                g.createNewFile();
                                        }catch(IOException e){

                                        }
                                        GestureLibrary gestureLib = GestureLibraries
                                                .fromFile(g);
                                        // 添加手势
                                        gestureLib.addGesture(gestureName.getText().toString(),
                                                gesture);
                                        // 保存手势库
                                        gestureLib.save();
                                    }
                                })
                                .setNegativeButton("取消", null)
                                .show();
                    }
                });
        button = (Button)findViewById(R.id.r);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(AddGesture.this, MenuActivity.class);
                AddGesture.this.startActivity(intent);
            }
        });
    }
}
