package com.gq.app.http.gank;

import com.gq.app.http.net2.APIService;
import com.gq.app.http.net2.OkHttpCallBack;
import com.gq.app.http.net2.RetrofitUtils;
import com.gq.app.model.Gank;
import com.gq.app.utils.Constants;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * M层：
 * 具体的逻辑(业务)处理了
 * Created by HDL on 2016/8/3.
 */
public class PublicModel implements GankContract.IPublicModel{

    @Override
    public void getGank(String type, int page, final OkHttpCallBack<Gank> callBack) {
        RetrofitUtils.newInstence(Constants.BASEURL)//获取请求接口
                .create(APIService.class)
                .getGanks(type,page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Gank>() {
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
                    public void onNext(Gank movies) {
                        callBack.onSuccessful(movies);//请求成功---回调
                    }
                });
    }


}
