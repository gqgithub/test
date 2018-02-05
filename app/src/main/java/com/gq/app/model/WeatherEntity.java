package com.gq.app.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/4.
 */
public class WeatherEntity implements Serializable{


    /**
     * msg : success
     * result : [{"airCondition":"轻度污染","city":"沈阳","coldIndex":"多发期","date":"2018-02-04",
     * "distrct":"沈阳","dressingIndex":"棉衣类","exerciseIndex":"不适宜","future":[{"date":"2018-02-04",
     * "dayTime":"晴","night":"晴","temperature":"-9°C / -23°C","week":"今天","wind":"北风 小于3级"},
     * {"date":"2018-02-05","dayTime":"晴","night":"晴","temperature":"-9°C / -23°C","week":"星期一",
     * "wind":"北风 小于3级"},{"date":"2018-02-06","dayTime":"晴","night":"多云","temperature":"-8°C /
     * -20°C","week":"星期二","wind":"东南风 小于3级"},{"date":"2018-02-07","dayTime":"多云","night":"多云",
     * "temperature":"-6°C / -18°C","week":"星期三","wind":"东北风 小于3级"},{"date":"2018-02-08",
     * "dayTime":"晴","night":"多云","temperature":"-3°C / -10°C","week":"星期四","wind":"西南风 小于3级"},
     * {"date":"2018-02-09","dayTime":"多云","night":"多云","temperature":"1°C / -12°C","week":"星期五",
     * "wind":"西南风 3～4级"},{"date":"2018-02-10","dayTime":"局部多云","night":"局部多云",
     * "temperature":"-7°C / -17°C","week":"星期六","wind":"西北风 3级"},{"date":"2018-02-11",
     * "dayTime":"少云","night":"少云","temperature":"-8°C / °C","week":"星期日","wind":"西北偏西风 3级"},
     * {"date":"2018-02-12","dayTime":"晴","night":"晴","temperature":"-4°C / -15°C","week":"星期一",
     * "wind":"西南风 2级"},{"date":"2018-02-13","dayTime":"少云","night":"局部多云","temperature":"-1°C /
     * -12°C","week":"星期二","wind":"南风 2级"}],"humidity":"湿度：39%","pollutionIndex":"115",
     * "province":"辽宁","sunrise":"06:56","sunset":"17:05","temperature":"-11℃","time":"13:20",
     * "updateTime":"20180204134357","washIndex":"不太适宜","weather":"晴","week":"周日","wind":"西风1级"}]
     * retCode : 200
     */

    @SerializedName("msg")
    public String mMsg;
    @SerializedName("retCode")
    public String mRetCode;
    @SerializedName("result")
    public List<ResultBean> mResult;

    public static List<WeatherEntity> arrayWeatherEntityFromData(String str) {

        Type listType = new TypeToken<ArrayList<WeatherEntity>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static class ResultBean implements Serializable{
        /**
         * airCondition : 轻度污染
         * city : 沈阳
         * coldIndex : 多发期
         * date : 2018-02-04
         * distrct : 沈阳
         * dressingIndex : 棉衣类
         * exerciseIndex : 不适宜
         * future : [{"date":"2018-02-04","dayTime":"晴","night":"晴","temperature":"-9°C / -23°C",
         * "week":"今天","wind":"北风 小于3级"},{"date":"2018-02-05","dayTime":"晴","night":"晴",
         * "temperature":"-9°C / -23°C","week":"星期一","wind":"北风 小于3级"},{"date":"2018-02-06",
         * "dayTime":"晴","night":"多云","temperature":"-8°C / -20°C","week":"星期二","wind":"东南风
         * 小于3级"},{"date":"2018-02-07","dayTime":"多云","night":"多云","temperature":"-6°C / -18°C",
         * "week":"星期三","wind":"东北风 小于3级"},{"date":"2018-02-08","dayTime":"晴","night":"多云",
         * "temperature":"-3°C / -10°C","week":"星期四","wind":"西南风 小于3级"},{"date":"2018-02-09",
         * "dayTime":"多云","night":"多云","temperature":"1°C / -12°C","week":"星期五","wind":"西南风
         * 3～4级"},{"date":"2018-02-10","dayTime":"局部多云","night":"局部多云","temperature":"-7°C /
         * -17°C","week":"星期六","wind":"西北风 3级"},{"date":"2018-02-11","dayTime":"少云","night":"少云",
         * "temperature":"-8°C / °C","week":"星期日","wind":"西北偏西风 3级"},{"date":"2018-02-12",
         * "dayTime":"晴","night":"晴","temperature":"-4°C / -15°C","week":"星期一","wind":"西南风 2级"},
         * {"date":"2018-02-13","dayTime":"少云","night":"局部多云","temperature":"-1°C / -12°C",
         * "week":"星期二","wind":"南风 2级"}]
         * humidity : 湿度：39%
         * pollutionIndex : 115
         * province : 辽宁
         * sunrise : 06:56
         * sunset : 17:05
         * temperature : -11℃
         * time : 13:20
         * updateTime : 20180204134357
         * washIndex : 不太适宜
         * weather : 晴
         * week : 周日
         * wind : 西风1级
         */

        @SerializedName("airCondition")
        public String mAirCondition;
        @SerializedName("city")
        public String mCity;
        @SerializedName("coldIndex")
        public String mColdIndex;
        @SerializedName("date")
        public String mDate;
        @SerializedName("distrct")
        public String mDistrct;
        @SerializedName("dressingIndex")
        public String mDressingIndex;
        @SerializedName("exerciseIndex")
        public String mExerciseIndex;
        @SerializedName("humidity")
        public String mHumidity;
        @SerializedName("pollutionIndex")
        public String mPollutionIndex;
        @SerializedName("province")
        public String mProvince;
        @SerializedName("sunrise")
        public String mSunrise;
        @SerializedName("sunset")
        public String mSunset;
        @SerializedName("temperature")
        public String mTemperature;
        @SerializedName("time")
        public String mTime;
        @SerializedName("updateTime")
        public String mUpdateTime;
        @SerializedName("washIndex")
        public String mWashIndex;
        @SerializedName("weather")
        public String mWeather;
        @SerializedName("week")
        public String mWeek;
        @SerializedName("wind")
        public String mWind;
        @SerializedName("future")
        public List<FutureBean> mFuture;

        public static List<ResultBean> arrayResultBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<ResultBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "mAirCondition='" + mAirCondition + '\'' +
                    ", mCity='" + mCity + '\'' +
                    ", mColdIndex='" + mColdIndex + '\'' +
                    ", mDate='" + mDate + '\'' +
                    ", mDistrct='" + mDistrct + '\'' +
                    ", mDressingIndex='" + mDressingIndex + '\'' +
                    ", mExerciseIndex='" + mExerciseIndex + '\'' +
                    ", mHumidity='" + mHumidity + '\'' +
                    ", mPollutionIndex='" + mPollutionIndex + '\'' +
                    ", mProvince='" + mProvince + '\'' +
                    ", mSunrise='" + mSunrise + '\'' +
                    ", mSunset='" + mSunset + '\'' +
                    ", mTemperature='" + mTemperature + '\'' +
                    ", mTime='" + mTime + '\'' +
                    ", mUpdateTime='" + mUpdateTime + '\'' +
                    ", mWashIndex='" + mWashIndex + '\'' +
                    ", mWeather='" + mWeather + '\'' +
                    ", mWeek='" + mWeek + '\'' +
                    ", mWind='" + mWind + '\'' +
                    ", mFuture=" + mFuture +
                    '}';
        }

        public static class FutureBean implements Serializable{
            /**
             * date : 2018-02-04
             * dayTime : 晴
             * night : 晴
             * temperature : -9°C / -23°C
             * week : 今天
             * wind : 北风 小于3级
             */

            @SerializedName("date")
            public String mDate;
            @SerializedName("dayTime")
            public String mDayTime;
            @SerializedName("night")
            public String mNight;
            @SerializedName("temperature")
            public String mTemperature;
            @SerializedName("week")
            public String mWeek;
            @SerializedName("wind")
            public String mWind;

            public static List<FutureBean> arrayFutureBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<FutureBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }
        }
    }
}
