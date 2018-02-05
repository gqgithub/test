package com.gq.app.http.city;

import android.content.Context;

import com.gq.app.http.net2.OkHttpCallBack;
import com.gq.app.model.CitysEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/2/4.
 */
public class CityContract {

    /**
     * V视图层
     */
    public interface ICityView {

//        void showBottom(int lastIndex);

        Context getCurContext();//获取上下文对象

//        void showProgress();//显示进度条
//
//        void hideProgress();//隐藏进度条

        void showCityData(List<CitysEntity.ResultBean> cityResult);//显示数据到View上
//        void showWeatherData(List<WeatherEntity.ResultBean> weatherResult);//显示数据到View上

        void showInfo(String info);//提示用户,提升友好交互

    }

    /**
     * P视图与逻辑处理的连接层
     */
    public interface ICityPresenter {

        void getCity();//获取数据
//        void getWeather();//获取数据

//        void loadMoreMovie();//加载更多
    }

    /**
     * M逻辑(业务)处理层
     */
    public interface ICityModel {
        void getcity(String key, OkHttpCallBack<CitysEntity> callBack);//获取信息

//        void getweather(String key,String city,String province, OkHttpCallBack<WeatherEntity> callBack);//获取信息
    }
}
