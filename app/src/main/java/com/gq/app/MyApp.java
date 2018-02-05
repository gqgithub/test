package com.gq.app;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.gq.app.utils.ACache;
import com.gq.app.utils.db.DBOpenHelper;

/**
 * Created by Administrator on 2017/12/29.
 */
public class MyApp extends Application {

    public static final  String DB_NAME = "gang.db";
    public static DBOpenHelper dbOpenHelper;
    public static final int version = 4;

    public static Context mContext;

    public static ACache getACache() {
        return ACache.get(mContext);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        //初始化数据库
        dbOpenHelper = new DBOpenHelper(mContext,DB_NAME,null,version);
    }

    //版本名
    public static String getVersionName() {
        return getPackageInfo().versionName;
    }

    //版本号
    public static int getVersionCode() {
        return getPackageInfo().versionCode;
    }

    private static PackageInfo getPackageInfo() {
        PackageInfo pi = null;
        try {
            PackageManager pm = mContext.getPackageManager();
            pi = pm.getPackageInfo(mContext.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pi;
    }
}
