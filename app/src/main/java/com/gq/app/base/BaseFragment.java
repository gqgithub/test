package com.gq.app.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/12/29.
 */
public abstract  class BaseFragment <V,T extends BasePresenter<V>> extends Fragment {

    protected T mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (setPresenter()!=null) {
            mPresenter = setPresenter();
            mPresenter.attachView((V) this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(setContentViewId(),container,false);

        initView(rootView);
        initData();

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter!=null) {
            mPresenter.detachView();
        }
    }

    protected abstract T setPresenter();

    protected abstract int setContentViewId();

    protected abstract void initView(View rootView);

    protected abstract void initData();

}
