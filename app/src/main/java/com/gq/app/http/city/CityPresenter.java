package com.gq.app.http.city;

import android.widget.TextView;

import com.gq.app.http.city.CityContract.ICityPresenter;
import com.gq.app.http.net2.APIService;
import com.gq.app.http.net2.OkHttpCallBack;
import com.gq.app.http.net2.RetrofitUtils;
import com.gq.app.model.CitysEntity;
import com.gq.app.model.WeatherEntity;
import com.gq.app.utils.Constants;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/2/4.
 */
public class CityPresenter implements ICityPresenter {

    CityContract.ICityModel mICityModel;//M层
    CityContract.ICityView mICityView;//V层

    public String mkey = Constants.CITYKEY;//请求多少个
    List<CitysEntity.ResultBean> mCitys = new ArrayList<>();//请求到的城市信息对象集合

    public CityPresenter(CityContract.ICityView iCityView, String key) {
        this.mICityView = iCityView;
        mICityModel = new CityModel();
        mkey = key;
    }

    @Override
    public void getCity() {
        mICityModel.getcity(mkey, new OkHttpCallBack<CitysEntity>() {
            @Override
            public void onSuccessful(CitysEntity city) {
                mCitys.addAll(city.mResult);//追加数据
                mICityView.showCityData(mCitys);//将获取到的信息显示到界面之前
            }

            @Override
            public void onFaild(String errorMsg) {
                mICityView.showInfo(errorMsg);//通知V层显示错误信息
            }
        });

    }


    public static void getWeather(final TextView temperature, final TextView other,String key, String city, String province) {
        RetrofitUtils.newInstence(Constants.MOB)//获取请求接口
                .create(APIService.class)
                .getWeather(key, city, province)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WeatherEntity>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(WeatherEntity weather) {
                        WeatherEntity.ResultBean resultW = weather.mResult.get(0);
                        temperature.setText(resultW.mTemperature);
                        other.setText(resultW.mWeather + " " + resultW.mAirCondition);
                    }
                });
    }

    public static void getWeather2(String key, String city, String province,
                                   final OkHttpCallBack<WeatherEntity> callBack) {
        RetrofitUtils.newInstence(Constants.MOB)//获取请求接口
                .create(APIService.class)
                .getWeather(key, city, province)
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

                    }
                });
    }

}
