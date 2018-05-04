package cn.lyh.spa.meizupush.push;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import cn.lyh.spa.meizupush.push.receiver.BootReceiver;

public class PushUtil {


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
}
