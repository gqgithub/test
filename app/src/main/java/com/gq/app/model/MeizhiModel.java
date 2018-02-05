package com.gq.app.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 妹纸数据
 */
public class MeizhiModel implements Serializable{

    @SerializedName("error")
    public boolean mError;
    @SerializedName("results")
    public List<Meizhi> mResults;

    public static List<MeizhiModel> arrayMeizhiModelFromData(String str) {

        Type listType = new TypeToken<ArrayList<MeizhiModel>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static class Meizhi {
        /**
         * _id : 5a431acd421aa90fe50c02a8
         * createdAt : 2017-12-27T12:00:13.664Z
         * desc : 12-27
         * publishedAt : 2017-12-27T12:13:22.418Z
         * source : chrome
         * type : 福利
         * url : http://7xi8d6.com1.z0.glb.clouddn.com/20171227115959_lmlLZ3_Screenshot.jpeg
         * used : true
         * who : daimajia
         */

        @SerializedName("_id")
        public String mId;
        @SerializedName("createdAt")
        public String mCreatedAt;
        @SerializedName("desc")
        public String mDesc;
        @SerializedName("publishedAt")
        public String mPublishedAt;
        @SerializedName("source")
        public String mSource;
        @SerializedName("type")
        public String mType;
        @SerializedName("url")
        public String mUrl;
        @SerializedName("used")
        public boolean mUsed;
        @SerializedName("who")
        public String mWho;

        public static List<Meizhi> arrayResultsBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<Meizhi>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }
    }
}
