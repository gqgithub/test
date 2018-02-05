package com.gq.app.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by Administrator on 2017/12/29.
 */
public abstract class BaseActivity <V, T extends BasePresenter<V>> extends AppCompatActivity {

    protected T mPresenter;

    protected Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (setPresenter()!=null) {
            mPresenter = setPresenter();
            mPresenter.attachView((V) this);
        }
        setContentView(setContentViewId());//布局

        initView();
        initData();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter!=null) {
            mPresenter.detachView();
        }
    }

    /**
     * Presenter 设置处理逻辑类
     * @return
     */
    protected abstract T setPresenter();

    /**
     * 加载布局文件
     * @return
     */
    protected abstract int setContentViewId();

    protected abstract void initView();

    protected abstract void initData();

}
