package com.gq.app.http.weather;

import com.gq.app.http.net2.APIService;
import com.gq.app.http.net2.OkHttpCallBack;
import com.gq.app.http.net2.RetrofitUtils;
import com.gq.app.model.WeatherEntity;
import com.gq.app.utils.Constants;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/2/4.
 */
public class WeatherModel implements WeatherContract.IWeatherModel{
    @Override
    public void getweather(String key, String city, String province, final OkHttpCallBack<WeatherEntity
                > callBack) {

        RetrofitUtils.newInstence(Constants.MOB)//获取请求接口
                .create(APIService.class)
                .getWeather(key,city,province)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WeatherEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        //失败的时候回调-----一下可以忽略 直接 callBack.onFaild("请求失败");
                        if (e instanceof HttpException) {
                            HttpException httpException = (HttpException) e;
                            //httpException.response().errorBody().string()
                            int code = httpException.code();
                            if (code == 500 || code == 404) {
                                callBack.onFaild("服务器出错");
                            }
                        } else if (e instanceof ConnectException) {
                            callBack.onFaild("网络断开,请打开网络!");
                        } else if (e instanceof SocketTimeoutException) {
                            callBack.onFaild("网络连接超时!!");
                        } else {
                            callBack.onFaild("发生未知错误" + e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(WeatherEntity weather) {
                        callBack.onSuccessful(weather);//请求成功---回调
                    }
                });
    }
}
