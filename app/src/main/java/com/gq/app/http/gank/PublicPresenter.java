package com.gq.app.http.gank;

import android.app.Activity;
import android.content.Intent;

import com.gq.app.activity.WebActivity;
import com.gq.app.http.net2.OkHttpCallBack;
import com.gq.app.model.Gank;
import com.gq.app.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * P层
 * Created by HDL on 2016/8/3.
 */
public class PublicPresenter implements GankContract.IPublicPresenter {

    GankContract.IPublicModel mIPublicModel;//M层
    GankContract.IPublicView mIPublicView;//V层

    public String mType = "Android";//从那个个开始
    public int page = 1;//请求多少个
    List<Gank.ResultsBean> mGanks = new ArrayList<>();//请求到的电影信息对象集合
    Activity mActivity;

    public PublicPresenter(GankContract.IPublicView mIPublicView,String type,Activity activity) {
        this.mActivity=activity;
        this.mIPublicView = mIPublicView;
        mIPublicModel = new PublicModel();
        mType=type;
    }

    @Override
    public void getGank() {

        mIPublicModel.getGank(mType, page, new OkHttpCallBack<Gank>() {
            @Override
            public void onSuccessful(Gank gank) {
                mGanks.addAll(gank.mResults);//追加数据
                mIPublicView.showData(mGanks);//将获取到的信息显示到界面之前
            }

            @Override
            public void onFaild(String errorMsg) {
                mIPublicView.showInfo(errorMsg);//通知V层显示错误信息
            }
        });

        page=page+1;//下一页
    }

    @Override
    public void loadMoreMovie() {
        getGank();
    }

    public void itemClick(int position) {
        Gank.ResultsBean result = mGanks.get(position);
        Intent intent = new Intent(mActivity, WebActivity.class);
        intent.putExtra(Constants.WebTitle, result.mDesc);
        intent.putExtra(Constants.WebUrl, result.mUrl);
        mActivity.startActivity(intent);

//        GankEntity resultsEntity = publicList.get(position);
//        IntentUtils.startToWebActivity(context, flagFragment, resultsEntity.getDesc(), resultsEntity.getUrl());
    }
}
