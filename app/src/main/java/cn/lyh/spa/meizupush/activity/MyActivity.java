package cn.lyh.spa.meizupush.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import cn.lyh.spa.meizupush.R;

public class MyActivity extends AppCompatActivity{
    TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        tv = findViewById(R.id.tv);
        Intent i = getIntent();
        String msg = i.getStringExtra("msg");
        if (msg != null){
            tv.setText(msg);
        }
    }
}
