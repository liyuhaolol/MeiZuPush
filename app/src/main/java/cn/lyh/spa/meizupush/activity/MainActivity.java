package cn.lyh.spa.meizupush.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.meizu.upspushsdklib.UpsPushManager;

import cn.lyh.spa.meizupush.R;
import cn.lyh.spa.meizupush.push.PushUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.start:
                PushUtil.registerPush(getApplicationContext());
                PushUtil.setAlias(getApplicationContext(),"coolpad");
                break;
            case R.id.stop:
                PushUtil.unregisterPush(getApplicationContext());
                break;
        }
    }
}
