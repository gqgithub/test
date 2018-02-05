package com.gq.app.activity;

import android.support.design.widget.FloatingActionButton;
import android.widget.TextView;

import com.gq.app.R;
import com.gq.app.base.BaseActivity;
import com.gq.app.http.redaily.IReDailyFgView;
import com.gq.app.http.redaily.ReDailyFgPresenter;
import com.gq.app.model.EventBusCount;
import com.gq.app.utils.ShareUtils;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.greenrobot.eventbus.EventBus;

/**
 * 每日一文数据显示
 * author:刚强
 */
public class ReDailyActivity extends BaseActivity<IReDailyFgView, ReDailyFgPresenter> implements IReDailyFgView{

    private TextView mTitleTextView,mAuthorTextView,mContentTextView;

    private FloatingActionButton mFloatingActionButton;

    private SpringView mSpringView;
    //第几篇
    private int count=0;


    @Override
    protected ReDailyFgPresenter setPresenter() {
        return new ReDailyFgPresenter(this);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_redaily;
    }

    @Override
    protected void initView() {
        count= ShareUtils.getInt(this, "count", 0);

        mTitleTextView= (TextView) findViewById(R.id.essay_title);
        mAuthorTextView= (TextView) findViewById(R.id.essay_author);
        mContentTextView= (TextView) findViewById(R.id.essay_content);
        mFloatingActionButton= (FloatingActionButton) findViewById(R.id.save_essay);
        mSpringView= (SpringView) findViewById(R.id.springview_redaily);

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
                ReDailyActivity.this.count++;
            }

            @Override
            public void onLoadmore() {
                //停止加载与更新
                mSpringView.onFinishFreshAndLoad();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //发送消息
        EventBus.getDefault().post(new EventBusCount(count));
    }

    @Override
    protected void initData() {
        mPresenter.getReDailyData();

    }

    @Override
    public TextView getTitleTextView() {
        return mTitleTextView;
    }

    @Override
    public TextView getAuthorTextView() {
        return mAuthorTextView;
    }

    @Override
    public TextView getContentTextView() {
        return mContentTextView;
    }

    @Override
    public FloatingActionButton getFloatingActionButton() {
        return mFloatingActionButton;
    }

    @Override
    public SpringView getSpringView() {
        return mSpringView;
    }
}
