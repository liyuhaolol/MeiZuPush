package cn.lyh.spa.meizupush;

public class ActivityList {
    //项目包名
    public static final String MY_PKG_NAME = "cn.lyh.spa.meizupush";
    //接收推送的activity
    public static final String SYNC_ACTIVITY = MY_PKG_NAME+".push.activity.SyncActivity";
    //默认跳转的页面，一般为app的入口页
    public static final String INDEX_PAGE = MY_PKG_NAME+".activity.MainActivity";
    //////////其他要跳转的页面
    public static final String myActivity = MY_PKG_NAME+".activity.MyActivity";
}
