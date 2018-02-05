package com.gq.app.http.weather;

import com.gq.app.http.net2.OkHttpCallBack;
import com.gq.app.model.WeatherEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/2/4.
 */
public class WeatherContract {

    /**
     * V视图层
     */
    public interface IWeatherView {

        void showWeatherData(List<WeatherEntity.ResultBean> weatherResult);//显示数据到View上
//        void showWeatherData(List<WeatherEntity.ResultBean> weatherResult);//显示数据到View上

        void showInfo(String info);//提示用户,提升友好交互

    }

    /**
     * P视图与逻辑处理的连接层
     */
    public interface IWeatherPresenter {

        void getWeather();//获取数据
    }

    /**
     * M逻辑(业务)处理层
     */
    public interface IWeatherModel {
//        void getcity(String key, OkHttpCallBack<CitysEntity> callBack);//获取信息

        void getweather(String key,String city,String province, OkHttpCallBack<WeatherEntity> callBack);//获取信息
    }
}
