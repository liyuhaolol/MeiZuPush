package cn.lyh.spa.meizupush.push.receiver;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.meizu.cloud.pushsdk.handler.MzPushMessage;
import com.meizu.upspushsdklib.UpsCommandMessage;
import com.meizu.upspushsdklib.UpsPushMessage;
import com.meizu.upspushsdklib.UpsPushMessageReceiver;
import com.xiaomi.mipush.sdk.MiPushMessage;

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
    public void onUpsCommandResult(final Context context, UpsCommandMessage upsCommandMessage) {
       switch (upsCommandMessage.getCommandType()){
           case REGISTER://注册
               //本地化最好放到后台请求保存成功之后
               //这里由于官方SDK的BUG，在非渠道的手机上，注册成功会回调2次，所以需要对pushId进行去重处理
               //然后再发起网络请求，如果网络请求失败，一定要将pushId置空，否则会出现以后再也无法注册的情况
               Log.e("liyuhao",upsCommandMessage.getCommandResult());
               SharedPreferences sp = context.getSharedPreferences("pushInfo",Context.MODE_PRIVATE);
               SharedPreferences.Editor ed = sp.edit();
               String pushid = sp.getString("pushid","");
               if (!pushid.equals(upsCommandMessage.getCommandResult())){
                   //如果是个新的pushid
                   //保存pushid
                   ed.putString("pushid",upsCommandMessage.getCommandResult());
                   ed.apply();
                   //执行对应的网络请求


               }
               new Handler(Looper.getMainLooper()).post(new Runnable() {
                   @Override
                   public void run() {
                       Toast.makeText(context,"注册服务成功",Toast.LENGTH_SHORT).show();
                   }
               });
               break;
           case UNREGISTER://反注册
               new Handler(Looper.getMainLooper()).post(new Runnable() {
                   @Override
                   public void run() {
                       Toast.makeText(context,"关闭服务成功",Toast.LENGTH_SHORT).show();
                   }
               });
               break;
           case SUBALIAS://设置别名
               /*new Handler(Looper.getMainLooper()).post(new Runnable() {
                   @Override
                   public void run() {
                       Toast.makeText(context,"设置别名成功",Toast.LENGTH_SHORT).show();
                   }
               });*/
               break;
           case UNSUBALIAS://反注册别名
               /*new Handler(Looper.getMainLooper()).post(new Runnable() {
                   @Override
                   public void run() {
                       Toast.makeText(context,"删除别名成功",Toast.LENGTH_SHORT).show();
                   }
               });*/
               break;
           case ERROR://有错误
               /*new Handler(Looper.getMainLooper()).post(new Runnable() {
                   @Override
                   public void run() {
                       Toast.makeText(context,"有错误",Toast.LENGTH_SHORT).show();
                   }
               });*/
               break;
        }
    }
}
