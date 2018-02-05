package com.gq.app.http.meizhi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.gq.app.base.BasePresenter;
import com.gq.app.model.MeizhiModel;
import com.liaoinstan.springview.widget.SpringView;

import java.util.List;

import rx.functions.Action1;

/**
 * 获取妹纸数据
 */
public class MeizhiPresenter extends BasePresenter<IMeizhiView> {

    private Context mContext;
    private IMeizhiView gankView;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private SpringView mSpringView;
    private MeiZhiAdapter mAdapter;

    private List<MeizhiModel.Meizhi> list;
    private int page = 1;
    private boolean isLoadMore = false;
    private boolean flag = true;


    public MeizhiPresenter(Context context) {
        this.mContext = context;
    }

    /**
     * 获取妹纸数据
     */
    public void getmeizhiList() {

        if (flag) {
            gankView = getView();
            if (gankView != null) {
                mToolbar = gankView.getToolbar();
                mRecyclerView = gankView.getRecyclerView();
                mSpringView = gankView.getSpringView();
                mToolbar.setTitle("干货妹纸");
            }
            flag = false;
        }

        if (isLoadMore) {
            page += 1;
        }

        new MeizhiLoader().getMeizhi(page)
                .subscribe(new Action1<List<MeizhiModel.Meizhi>>() {
                    @Override
                    public void call(List<MeizhiModel.Meizhi> meizhis) {
                        fenzhengData(meizhis);

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });


    }

    /**
     * 刷新数据
     */
    public void refreshData() {
        getmeizhiList();
    }

    /**
     * 加载更多数据
     */
    public void loadMoreData() {
        isLoadMore = true;
        getmeizhiList();
    }

    /**
     * 更新view的数据
     *
     * @param meizhis
     */
    private void fenzhengData(List<MeizhiModel.Meizhi> meizhis) {

        if (isLoadMore) {
            list.addAll(meizhis);
        } else {
            list = meizhis;
            mAdapter = new MeiZhiAdapter(mContext, list);
            mRecyclerView.setAdapter(mAdapter);
        }
        mAdapter.notifyDataSetChanged();
        mSpringView.onFinishFreshAndLoad();
    }

}
