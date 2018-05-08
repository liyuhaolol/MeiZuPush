package cn.lyh.spa.meizupush.push;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Looper;

import com.meizu.upspushsdklib.UpsPushManager;

import cn.lyh.spa.meizupush.push.receiver.BootReceiver;

public class PushUtil {
    private static String appId = "1000357";
    private static String appKey = "e7e0e4ea5ce04fab8b83695059364983";


    /**
     * 设置后台自启动，前提是手机允许应用自启动
     * @param context
     */
    public static void setAutoRun(Context context){
        IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK);
        BootReceiver receiver = new BootReceiver();
        context.registerReceiver(receiver, filter);

        BootReceiver receiverCon = new BootReceiver();
        //创建意图过滤器
        IntentFilter filterCon=new IntentFilter();
        //添加动作，监听网络
        filterCon.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(receiverCon, filterCon);
    }

    /**
     * 注册推送
     * @param context
     */
    public static void registerPush(Context context){
        UpsPushManager.register(context,appId,appKey);
    }

    public static void registerActivityLife(){}

    /**
     * 反注册推送
     * @param context
     */
    public static void unregisterPush(Context context){
        UpsPushManager.unRegister(context);
    }

    /**
     * 订阅别名
     * @param context
     * @param alias
     */
    public static void setAlias(final Context context, final String alias){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                UpsPushManager.setAlias(context,alias);
            }
        },3000);
    }

    /**
     * 反注册别名
     * @param context
     * @param alias
     */
    public static void unSetAlias(Context context, String alias){
        UpsPushManager.unSetAlias(context,alias);
    }

}
