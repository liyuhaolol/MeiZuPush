package cn.lyh.spa.meizupush.push.receiver;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.meizu.upspushsdklib.UpsCommandMessage;
import com.meizu.upspushsdklib.UpsPushMessage;
import com.meizu.upspushsdklib.UpsPushMessageReceiver;

public class UpsReceiver extends UpsPushMessageReceiver{
    @Override
    public void onThroughMessage(Context context, UpsPushMessage upsPushMessage) {

    }

    @Override
    public void onNotificationClicked(Context context, UpsPushMessage upsPushMessage) {

    }

    @Override
    public void onNotificationArrived(Context context, UpsPushMessage upsPushMessage) {

    }

    @Override
    public void onNotificationDeleted(Context context, UpsPushMessage upsPushMessage) {

    }

    @Override
    public void onUpsCommandResult(Context context, UpsCommandMessage upsCommandMessage) {
       switch (upsCommandMessage.getCommandType()){
           case REGISTER://注册
               SharedPreferences sp = context.getSharedPreferences("pushInfo",Context.MODE_PRIVATE);
               SharedPreferences.Editor ed = sp.edit();
               String pushid = sp.getString("pushid","");
               if (!pushid.equals(upsCommandMessage.getCommandResult())){
                   //如果是个新的pushid
                   //保存pushid
                   ed.putString("pushid",upsCommandMessage.getCommandResult());
                   ed.apply();
                   //执行对应的网络请求
                   Log.e("liyuhao",upsCommandMessage.getCommandResult());
               }
               break;
           case UNREGISTER://反注册
               break;
           case SUBALIAS://设置别名
               break;
           case UNSUBALIAS://反注册别名
               break;
           case ERROR://有错误
               break;
        }
    }
}
