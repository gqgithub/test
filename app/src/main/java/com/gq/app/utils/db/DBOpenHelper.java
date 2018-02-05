package com.gq.app.utils.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gq.app.MyApp;

/**
 * 数据库
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    public static final String COLLECTDB = "collect";

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, MyApp.version);
    }

    public static final String GankID = "gankID";
    public static final String createdAt = "createdAt";
    public static final String desc = "desc";
    public static final String publishedAt = "publishedAt";
    public static final String source = "source";
    public static final String type = "type";
    public static final String url = "url";
    public static final String used = "used";
    public static final String who = "who";
    //版本2添加的新字段
    public static final String imageUrl = "imageUrl";

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table essay (_id integer primary key autoincrement,title text unique," +
                "author text,digest text,content text)");
        //收藏表
        db.execSQL("create table collect (" +
                        "_id integer primary key autoincrement," +
                        GankID+" text," +
                        createdAt+" text," +
                        desc+" text," +
                        publishedAt+" text," +
                        source+" text," +
                        type+" text," +
                        url+" text," +
                        used+" text," +
                        who+" text," +
                        imageUrl+" text default '')"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //收藏表
        db.execSQL("create table collect (" +
                        "_id integer primary key autoincrement," +
                        GankID+" text," +
                        createdAt+" text," +
                        desc+" text," +
                        publishedAt+" text," +
                        source+" text," +
                        type+" text," +
                        url+" text," +
                        used+" text," +
                        who+" text," +
                        imageUrl+" text default '')"
        );
    }
}
