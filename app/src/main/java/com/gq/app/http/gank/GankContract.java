package com.gq.app.http.gank;

import android.content.Context;

import com.gq.app.http.net2.OkHttpCallBack;
import com.gq.app.model.Gank;

import java.util.List;

/**
 * Created by Administrator on 2018/1/18.
 */
public class GankContract {

    /**
     * V视图层
     */
    public interface IPublicView {

//        void showBottom(int lastIndex);

        Context getCurContext();//获取上下文对象

//        void showProgress();//显示进度条
//
//        void hideProgress();//隐藏进度条

        void showData(List<Gank.ResultsBean> gankResult);//显示数据到View上

        void showInfo(String info);//提示用户,提升友好交互

    }

    /**
     * P视图与逻辑处理的连接层
     */
    public interface IPublicPresenter {

        void getGank();//获取数据

        void loadMoreMovie();//加载更多
    }

    /**
     * M逻辑(业务)处理层
     */
    public interface IPublicModel {
        void getGank(String type, int page, OkHttpCallBack<Gank> callBack);//获取信息
    }
}
