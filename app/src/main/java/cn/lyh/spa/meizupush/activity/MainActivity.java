package cn.lyh.spa.meizupush.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.meizu.upspushsdklib.UpsPushManager;

import cn.lyh.spa.meizupush.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //UpsPushManager.setAlias(getApplicationContext(),"samsung");

    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.start:
                break;
            case R.id.stop:
                break;
        }
    }
}
