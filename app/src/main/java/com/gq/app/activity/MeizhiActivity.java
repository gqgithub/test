package com.gq.app.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.gq.app.R;
import com.gq.app.base.BaseActivity;
import com.gq.app.http.meizhi.IMeizhiView;
import com.gq.app.http.meizhi.MeizhiPresenter;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

/**
 * 妹纸实现
 */
public class MeizhiActivity extends BaseActivity<IMeizhiView,MeizhiPresenter> implements IMeizhiView{

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private SpringView mSpringView;


    @Override
    protected MeizhiPresenter setPresenter() {
        return new MeizhiPresenter(this);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_meizhi;
    }

    @Override
    protected void initView() {
        mToolbar= (Toolbar) findViewById(R.id.toolbar_meizhi);
        mRecyclerView= (RecyclerView) findViewById(R.id.recycler_view_meizhi);
        mSpringView= (SpringView) findViewById(R.id.springview_meizhi);


        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        /**
         * 添加上拉刷新，下拉加载更多功能
         */
        //DefaultHeader/Footer是SpringView已经实现的默认头/尾之一
        //更多还有MeituanHeader、AliHeader、RotationHeader等如上图7种
        mSpringView.setHeader(new DefaultHeader(this));
        mSpringView.setFooter(new DefaultFooter(this));
        mSpringView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                //获取数据
                mPresenter.refreshData();
            }

            @Override
            public void onLoadmore() {
                mPresenter.loadMoreData();
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter.getmeizhiList();
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    public SpringView getSpringView() {
        return mSpringView;
    }
}
