package com.gq.app.base;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

public class BasePresenter <V> {

//    public static final GankApi gankApi = ApiFactory.getGankApiSingleton();

//    public static final DailyApi dailyApi = ApiFactory.getDailyApiSingleton();

    protected Reference<V> mViewRef;

    public void attachView(V view){
        mViewRef = new WeakReference<V>(view);
    }

    protected V getView(){
        return mViewRef.get();
    }

    public boolean isViewAttached(){
        return mViewRef != null&&mViewRef.get()!=null;
    }

    public void detachView(){
        if(mViewRef!=null){
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
