package cn.lyh.spa.meizupush.push.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.List;

import cn.lyh.spa.meizupush.ActivityList;
import cn.lyh.spa.meizupush.activity.MainActivity;
import cn.lyh.spa.meizupush.push.model.NotificationSample;
import cn.lyh.spa.meizupush.push.model.base.PushThrowMessage;
import cn.lyh.spa.meizupush.push.service.BootService;

/**
 * @author liyuhao
 * push消息分发
 */

public class SyncActivity extends Activity{

    private String json;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        Uri uri = i.getData();
        if (uri != null){
            // 获取uri中携带的参数，多个参数都可以这样获取
            json = uri.getQueryParameter("json");
        }
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (json != null){
            //启动服务，执行对应操作
            Intent serviceIntent = new Intent(getApplicationContext(), BootService.class);
            startService(serviceIntent);
            Intent msg = new Intent();
            msg.setAction(BootService.ACTION_ACTIVITY);
            msg.putExtra("json",json);
            sendBroadcast(msg);
        }else {
            Intent intent = new Intent(SyncActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
