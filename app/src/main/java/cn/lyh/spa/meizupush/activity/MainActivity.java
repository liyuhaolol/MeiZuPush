package cn.lyh.spa.meizupush.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.meizu.upspushsdklib.UpsPushManager;

import cn.lyh.spa.meizupush.R;
import cn.lyh.spa.meizupush.push.ActivityLifecycleListener;
import cn.lyh.spa.meizupush.push.PushUtil;
import cn.lyh.spa.meizupush.push.service.BootService;

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
                if (ActivityLifecycleListener.isApplicationInForeground()){
                    Toast.makeText(getApplicationContext(),"前台",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"后台",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.stop:
                PushUtil.unSetAlias(getApplicationContext(),"xiaomi");
                PushUtil.unregisterPush(getApplicationContext());
                break;
        }
    }
}
