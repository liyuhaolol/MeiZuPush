package cn.lyh.spa.meizupush.applicatioin;

import android.app.Application;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.meizu.upspushsdklib.UpsPushManager;

import cn.lyh.spa.meizupush.push.PushUtil;


/**
 * Created by zhaolb on 2018/5/3.
 */

public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        UpsPushManager.register(getApplicationContext(),"1000357","e7e0e4ea5ce04fab8b83695059364983");
        //设置后台自启动
        PushUtil.setAutoRun(getApplicationContext());
    }
}
