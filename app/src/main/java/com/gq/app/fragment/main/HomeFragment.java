package com.gq.app.fragment.main;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.gq.app.R;
import com.gq.app.base.BaseFragment;
import com.gq.app.base.BasePresenter;
import com.gq.app.http.gank.PublicFragment;
import com.gq.app.utils.Constants;

/**
 * tablayout标签页+viewpage+fragment
 */
public class HomeFragment extends BaseFragment{

    private TabLayout mTabLayout;

    private ViewPager mViewPager;
//    private List<Fragment> mFragments;



    private String[] mTitles = {
            Constants.FlagAndroid,
            Constants.FlagIOS,
            Constants.FlagVideo,
            Constants.FlagJS,
            Constants.FlagExpand,
            Constants.FlagRecommend,
            Constants.FlagAPP,
    };

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View rootView) {
        mTabLayout= (TabLayout) rootView.findViewById(R.id.tablayout);
        //滑动模式当标签很多的时候就一直往后添加，以滑动的效果显示出来
        mTabLayout.setTabMode (TabLayout.MODE_SCROLLABLE);

        mViewPager= (ViewPager) rootView.findViewById(R.id.viewpager);

        //预加载
        mViewPager.setOffscreenPageLimit(0);

        //viewpage滑动监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initData() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(mTitles[i]));
        }

//        mFragments=new ArrayList<>();
//        mFragments.add(new OneFragment());
//        mFragments.add(new TwoFragment());
//        mFragments.add(new ThreeFragment());
//        mFragments.add(new FourFragment());
//        mFragments.add(new FiveFragment());
//        mFragments.add(new SixFragment());
//        mFragments.add(new SevenFragment());

        //设置适配器
        mViewPager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                //Fragment布局一致，使用公共的fragment
                return PublicFragment.newInstance(mTitles[position]);
            }

            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }
        });

        //加载适配器
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
