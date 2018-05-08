package cn.lyh.spa.meizupush.applicatioin;

import android.app.Application;

import cn.lyh.spa.meizupush.push.PushUtil;


/**
 * Created by zhaolb on 2018/5/3.
 */

public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        PushUtil.registerPush(getApplicationContext());
        PushUtil.setAlias(getApplicationContext(),"meizu");
        //设置后台自启动
        PushUtil.setAutoRun(getApplicationContext());
    }
}
