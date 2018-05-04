package cn.lyh.spa.meizupush.push.receiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import cn.lyh.spa.meizupush.push.service.BootService;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            //启动任意的service都会启动application并启动推送
            Intent newIntent = new Intent(context,BootService.class);
            context.startService(newIntent);
        }
        if(intent.getAction().equals(Intent.ACTION_TIME_TICK)){
            //利用系统广播Intent.ACTION_TIME_TICK，这个广播每分钟发送一次，我们可以每分钟检查一次Service的运行状态，如果已经被结束了，就重新启动Service
            /*boolean isServiceRunning = false;
            ActivityManager manager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningServiceInfo service :manager.getRunningServices(Integer.MAX_VALUE)) {
                if("cn.lyh.spa.meizupush.push.service.BootService".equals(service.service.getClassName()))
                //Service的类名
                {
                    isServiceRunning = true;
                }

            }
            if (!isServiceRunning) {*/
                Intent i = new Intent(context, BootService.class);
                context.startService(i);
            //}
        }
        if(intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)){
            //利用系统广播Intent.ACTION_TIME_TICK，这个广播每分钟发送一次，我们可以每分钟检查一次Service的运行状态，如果已经被结束了，就重新启动Service
            Intent i = new Intent(context, BootService.class);
            context.startService(i);
        }
    }
}
