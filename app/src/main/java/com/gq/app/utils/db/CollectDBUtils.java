package com.gq.app.utils.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gq.app.model.Gank;

import java.util.ArrayList;
import java.util.List;

/**
 * 收藏的数据库操作
 */
public class CollectDBUtils {

//    "_id integer primary key autoincrement," +
//    "gankID text," +
//            "createdAt text" +
//            "desc text," +
//            "publishedAt text," +
//            "source text," +
//            "type text," +
//            "url text," +
//            "used text," +
//            "who text," +
//            "images text)"

    /**
     * 往数据库中插入一条收藏数据
     *
     * @param gankResult
     * @return 是否插入成功
     */
    public synchronized boolean insertOneCollect(DBOpenHelper helper,Gank.ResultsBean gankResult) {
        SQLiteDatabase db = helper.getWritableDatabase();  // 打开读写的数据库
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.GankID, gankResult.mId);
        values.put(DBOpenHelper.createdAt, gankResult.mCreatedAt);
        values.put(DBOpenHelper.desc, gankResult.mDesc);
        values.put(DBOpenHelper.publishedAt, gankResult.mPublishedAt);
        values.put(DBOpenHelper.source, gankResult.mSource);
        values.put(DBOpenHelper.type, gankResult.mType);
        values.put(DBOpenHelper.url, gankResult.mUrl);
        values.put(DBOpenHelper.used, gankResult.mUsed);
        values.put(DBOpenHelper.who, gankResult.mWho);

        String imageUrl = "";
        if (gankResult.mImages != null && gankResult.mImages.size() > 0) {
            imageUrl = gankResult.mImages.get(0);
        }
        values.put(DBOpenHelper.imageUrl, imageUrl);

        long insert = db.insert(DBOpenHelper.COLLECTDB, null, values);
        db.close();
        return insert != (-1);
    }

    /**
     * 删除一条数据
     *
     * @param ganID
     * @return
     */
    public synchronized boolean deleteOneCollect(DBOpenHelper helper,String ganID) {
        SQLiteDatabase db = helper.getWritableDatabase(); // 打开读写的数据库
        int i = db.delete(DBOpenHelper.COLLECTDB, DBOpenHelper.GankID+"=?", new String[]{ganID});
        db.close();
        return i > 0;
    }


    /**
     * 查询是否存在
     *
     * @param GankID
     * @return
     */
    public synchronized boolean queryOneCollectByID(DBOpenHelper helper,String GankID) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(DBOpenHelper.COLLECTDB, null, DBOpenHelper.GankID+"=?", new String[]{GankID}, null, null, null);
        boolean result = false;
        if (cursor.moveToNext()) {
            result = true;
        }
        cursor.close();
        db.close();
        return result;
    }

    /**
     * 查询每个标签的收藏数据
     *
     * @param type
     * @return 集合数据
     */
    public synchronized List<Gank.ResultsBean> queryAllCollectByType(DBOpenHelper helper,String type) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(DBOpenHelper.COLLECTDB, null, DBOpenHelper.type+"=?", new String[]{type}, null, null, null);

        List<Gank.ResultsBean> collectList = new ArrayList<>();
        Gank.ResultsBean collect;
        while (cursor.moveToNext()) {
            //查询
            String createdAt = cursor.getString(cursor.getColumnIndex(DBOpenHelper.createdAt));
            String desc = cursor.getString(cursor.getColumnIndex(DBOpenHelper.desc));
            String GankID = cursor.getString(cursor.getColumnIndex(DBOpenHelper.GankID));
            String publishedAt = cursor.getString(cursor.getColumnIndex(DBOpenHelper.publishedAt));
            String source = cursor.getString(cursor.getColumnIndex(DBOpenHelper.source));
            String url = cursor.getString(cursor.getColumnIndex(DBOpenHelper.url));
            String who = cursor.getString(cursor.getColumnIndex(DBOpenHelper.who));
            String imageUrl = cursor.getString(cursor.getColumnIndex(DBOpenHelper.imageUrl));

            collect = new Gank.ResultsBean();
            collect.mId=GankID;
            collect.mCreatedAt=createdAt;
            collect.mDesc=desc;
            collect.mPublishedAt=publishedAt;
            collect.mSource=source;
            collect.mUrl=url;
            collect.mWho=who;

            List<String> images = new ArrayList<>();
            images.add(imageUrl);
            collect.mImages=images;

            collectList.add(collect);
        }
        //关闭游标
        cursor.close();
        db.close();
        return collectList;
    }

}
