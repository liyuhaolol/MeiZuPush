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

/**
 * @author liyuhao
 * push消息分发
 */

public class SyncActivity extends Activity{
    private static final String TAG = "PushMessageHandler";


    private static final int NOTIFICATION_MSG = 0;

    private static final int SILENCE_MSG = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        Uri uri = i.getData();
        String json;
        if (uri != null){
            // 获取uri中携带的参数，多个参数都可以这样获取
            json = uri.getQueryParameter("json");
            if (json != null){
                //交付统一消息处理出口
                syncMessage(getApplicationContext(),json);
                finish();
            }else {
                Intent intent = new Intent(SyncActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }


    /**
     * 初步解析得到消息
     * @param context 上下文
     * @param msg json
     */
    public static void syncMessage(Context context, String msg){
        try {
            PushThrowMessage pMsg = JSONObject.parseObject(msg,PushThrowMessage.class);
            switch (pMsg.type){
                case NOTIFICATION_MSG:
                    notification(context,msg);
                    break;
                case SILENCE_MSG:
                    //silence(context,msg);
                    //暂时不处理透传，埋点以防后期需求
                    break;
            }

        }catch (Exception e){
            Log.e(TAG,"wrong json");
        }
    }


    /**
     * 显示对应通知
     * @param context 上下文
     * @param msg json
     */
    private static void notification(Context context,String msg){
        try {
            PushThrowMessage<NotificationSample> pMsg = JSONObject
                    .parseObject(msg,new TypeReference<PushThrowMessage<NotificationSample>>(){});
            if (pMsg.extras != null){
                if (pMsg.extras.target.equals("")){
                    //没有附带转跳参数，启动app初始页面
                }else {
                    try {
                        if (pMsg.extras.target.equals(ActivityList.mainActivity)){
                            Class clazz = Class.forName(ActivityList.mainActivity);
                            Intent i = new Intent(context,clazz);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);
                        }else if (pMsg.extras.target.equals(ActivityList.myActivity)){
                            PushThrowMessage<NotificationSample<String>> tMsg = JSONObject
                                    .parseObject(msg,new TypeReference<PushThrowMessage<NotificationSample<String>>>(){});
                            Class clazz = Class.forName(pMsg.extras.target);
                            Intent i = new Intent(context,clazz);
                            i.putExtra("msg",tMsg.extras.params);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(context,i);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        //解析失败，打开主界面
                        Class clazz = Class.forName(ActivityList.mainActivity);
                        Intent i = new Intent(context,clazz);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                }
            }else {
                //解析失败，打开主界面
                Class clazz = Class.forName(ActivityList.mainActivity);
                Intent i = new Intent(context,clazz);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }

        }catch (Exception e){
            Log.e(TAG,"wrong json");
        }
    }

    private static void startActivity(Context context , Intent intent){
        //判断应用是否在运行
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        boolean isAppRunning = false;
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(ActivityList.MY_PKG_NAME) || info.baseActivity.getPackageName().equals(ActivityList.MY_PKG_NAME)) {
                isAppRunning = true;
                break;
            }
        }

        if (isAppRunning){
            context.startActivity(intent);
        }else {
            try {
                Class clazz = Class.forName(ActivityList.mainActivity);
                Intent m = new Intent(context,clazz);
                m.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Intent[] intents = new Intent[]{m,intent};
                context.startActivities(intents);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }


    }
}
