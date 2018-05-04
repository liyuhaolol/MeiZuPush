package cn.lyh.spa.meizupush.applicatioin;

import android.app.Application;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.meizu.upspushsdklib.UpsPushManager;

import cn.lyh.spa.meizupush.receiver.BootReceiver;

/**
 * Created by zhaolb on 2018/5/3.
 */

public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        UpsPushManager.register(getApplicationContext(),"1000357","e7e0e4ea5ce04fab8b83695059364983");

        IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK);
        BootReceiver receiver = new BootReceiver();
        registerReceiver(receiver, filter);

        BootReceiver receiverCon = new BootReceiver();
        //创建意图过滤器
        IntentFilter filterCon=new IntentFilter();
        //添加动作，监听网络
        filterCon.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiverCon, filterCon);
    }
}
