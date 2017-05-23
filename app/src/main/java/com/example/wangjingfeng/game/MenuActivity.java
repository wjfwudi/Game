package com.example.wangjingfeng.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

public class MenuActivity extends Activity {

    ImageButton start,score;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.menu);
        start = (ImageButton) findViewById(R.id.start);
        score = (ImageButton) findViewById(R.id.add);
        start.setImageResource(R.drawable.start_button);
        score.setImageResource(R.drawable.score_button);
        start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.setClass(MenuActivity.this, PlayActivity.class);
                MenuActivity.this.startActivity(intent);
            }

        });
        score.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.setClass(MenuActivity.this, AddGesture.class);
                MenuActivity.this.startActivity(intent);
            }

        });


    }
}

