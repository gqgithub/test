package com.gq.app.http.gank;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.gq.app.MyApp;
import com.gq.app.R;
import com.gq.app.adapter.PublicAdapter;
import com.gq.app.model.Gank;
import com.gq.app.utils.Constants;
import com.gq.app.utils.ToastUtils;
import com.gq.app.utils.db.CollectDBUtils;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.List;

/**
 * 公用的Fragment：Android，ios，休息视频，前端，拓展资源，瞎推荐，App
 */
public class PublicFragment extends Fragment implements GankContract.IPublicView {

    PublicPresenter mPublicPresenter;
    RecyclerView mRecyclerView;
    ProgressBar mProgressBar;
    private PublicAdapter mPublicAdapter;
    private SpringView springView;

    private String typeFragment;

    //2.添加显示收藏数据的按钮
    private FloatingActionButton mFloatingActionButton;

    public static Fragment newInstance(String title) {
        PublicFragment publicFragment = new PublicFragment();
        Bundle args = new Bundle();
        args.putString(Constants.typeFragment, title);
        publicFragment.setArguments(args);
        return publicFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            typeFragment = getArguments().getString(Constants.typeFragment);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_common, null);
        initView(view);
        mPublicPresenter = new PublicPresenter(this,typeFragment,getActivity());
        mPublicPresenter.getGank();//启动软件时默认加载

        return view;
    }

    private void initView(View view) {
        springView = (SpringView) view.findViewById(R.id.pf_springview);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.pf_rv_public_list);
        mProgressBar = (ProgressBar) view.findViewById(R.id.pf_pb);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));//设置为listview的布局
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置动画
        //添加滑动监听
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mFloatingActionButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        //2.
        mFloatingActionButton= (FloatingActionButton) view.findViewById(R.id.pf_collect);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //更新数据
                final List<Gank.ResultsBean> collectData=new CollectDBUtils().queryAllCollectByType(MyApp.dbOpenHelper, typeFragment);
                mProgressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showData(collectData);
                    }
                }, 1000);
            }
        });
    }


    @Override
    public Context getCurContext() {
        return getActivity();
    }

    @Override
    public void showData(List<Gank.ResultsBean> gankResult) {

        //隐藏加载圈
        mProgressBar.setVisibility(View.GONE);

        if (isLoreMore) {
            mPublicAdapter.setGank(gankResult);
            mPublicAdapter.notifyDataSetChanged();
        } else {
            mPublicAdapter = new PublicAdapter(getActivity());
            mPublicAdapter.setGank(gankResult);
            mRecyclerView.setAdapter(mPublicAdapter);
        }

        //添加刷新
        if(gankResult.size()>10){
            addFresh();
        }
        //添加item点击事件
        addItemClick();

    }

    private void addItemClick() {
        //1.5使用
        mPublicAdapter.setItemClickListener(new PublicAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ToastUtils.showToast(getActivity(), "第" + (position + 1) + "个");
                mPublicPresenter.itemClick(position);
            }
        });
    }


    boolean isLoreMore = false;

    private void addFresh() {
        //springview 在加载数据之后使用
        springView.setHeader(new DefaultHeader(getActivity()));
        springView.setFooter(new DefaultFooter(getActivity()));

        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                //刷新数据
                mPublicAdapter.notifyDataSetChanged();
                springView.onFinishFreshAndLoad();

            }

            @Override
            public void onLoadmore() {
                isLoreMore = true;
                mPublicPresenter.loadMoreMovie();
                springView.onFinishFreshAndLoad();

            }
        });
    }

    @Override
    public void showInfo(String info) {
        ToastUtils.showToast(getActivity(), info);
    }


}
