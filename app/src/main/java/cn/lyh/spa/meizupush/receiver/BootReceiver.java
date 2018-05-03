package cn.lyh.spa.meizupush.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.lyh.spa.meizupush.service.BootService;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            //启动任意的service都会启动application并启动推送
            Intent newIntent = new Intent(context,BootService.class);
            context.startService(newIntent);
        }
    }
}
