package cn.lyh.spa.meizupush.push.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * 目前没啥用，只为了拉起application
 */
public class BootService extends Service{

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
