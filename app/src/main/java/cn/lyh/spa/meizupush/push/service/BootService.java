package cn.lyh.spa.meizupush.push.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;


import cn.lyh.spa.meizupush.ActivityList;
import cn.lyh.spa.meizupush.push.ActivityLifecycleListener;
import cn.lyh.spa.meizupush.push.model.NotificationSample;
import cn.lyh.spa.meizupush.push.model.base.PushThrowMessage;

/**
 * @author liyuhao
 * @date 2018.5.7
 * 自启动的service
 * 1，拉起application、
 * 2，接受推送消息转跳对应的页面
 */
public class BootService extends Service{
    private MyReceiver receiver;
    public final static String ACTION_ACTIVITY = "goActivity";

    private static final String TAG = "PushMessageHandler";


    private static final int NOTIFICATION_MSG = 0;

    private static final int SILENCE_MSG = 1;



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_ACTIVITY);
        registerReceiver(receiver,filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiver != null){
            unregisterReceiver(receiver);
        }
    }


    //内部类实现广播接收者
    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String json = intent.getStringExtra("json");
            syncMessage(context,json);
        }
    }

    /**
     * 初步解析得到消息
     * @param context 上下文
     * @param msg json
     */
    public void syncMessage(Context context, String msg){
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
    private void notification(Context context,String msg){
        try {
            PushThrowMessage<NotificationSample> pMsg = JSONObject
                    .parseObject(msg,new TypeReference<PushThrowMessage<NotificationSample>>(){});
            if (pMsg.extras != null){
                if (pMsg.extras.target.equals("")){
                    //没有附带转跳参数，启动app初始页面
                }else {
                    try {
                        if (pMsg.extras.target.equals(ActivityList.INDEX_PAGE)){
                            Class clazz = Class.forName(ActivityList.INDEX_PAGE);
                            Intent i = new Intent(context,clazz);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);

                            ////下方为修改位置，各自项目要转跳的页面逻辑
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
                        Class clazz = Class.forName(ActivityList.INDEX_PAGE);
                        Intent i = new Intent(context,clazz);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                }
            }else {
                //解析失败，打开主界面
                Class clazz = Class.forName(ActivityList.INDEX_PAGE);
                Intent i = new Intent(context,clazz);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }

        }catch (Exception e){
            Log.e(TAG,"wrong json");
        }
    }

    private void startActivity(Context context , Intent intent){
        //判断应用是否在运行
        if (ActivityLifecycleListener.isApplicationInForeground()){//程序正在运行
            Log.e("liyuhao","前台运行");
            context.startActivity(intent);
        }else {
            Log.e("liyuhao","后台运行");
            try {
                Class clazz = Class.forName(ActivityList.INDEX_PAGE);
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
