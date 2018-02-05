package com.gq.app.http.weather;

import com.gq.app.http.net2.OkHttpCallBack;
import com.gq.app.model.WeatherEntity;
import com.gq.app.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/4.
 */
public class WeatherPresenter implements WeatherContract.IWeatherPresenter{

    WeatherContract.IWeatherModel mIWeatherModel;//M层
    WeatherContract.IWeatherView mIWeatherView;//V层

    public String mkey = Constants.CITYKEY;//请求多少个
    String mcity;
    String mprovince;
    List<WeatherEntity.ResultBean> mWeathers = new ArrayList<>();//请求到的城市信息对象集合

    public WeatherPresenter(WeatherContract.IWeatherView iWeatherView, String key,String city,String province) {
        this.mIWeatherView = iWeatherView;
        mIWeatherModel = new WeatherModel();
        mkey = key;
        mcity=city;
        mprovince=province;
    }

    @Override
    public void getWeather() {
        mIWeatherModel.getweather(mkey,mcity,mprovince, new OkHttpCallBack<WeatherEntity>() {
            @Override
            public void onSuccessful(WeatherEntity weather) {
                mWeathers.addAll(weather.mResult);//追加数据
                mIWeatherView.showWeatherData(mWeathers);//将获取到的信息显示到界面之前
            }

            @Override
            public void onFaild(String errorMsg) {
                mIWeatherView.showInfo(errorMsg);//通知V层显示错误信息
            }
        });
    }
}
