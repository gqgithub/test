package com.gq.app;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gq.app.activity.CitysActivity;
import com.gq.app.activity.FavListActivity;
import com.gq.app.activity.MeizhiActivity;
import com.gq.app.activity.ReDailyActivity;
import com.gq.app.activity.WeatherActivity;
import com.gq.app.base.BaseActivity;
import com.gq.app.base.BasePresenter;
import com.gq.app.fragment.main.HomeFragment;
import com.gq.app.fragment.main.MessageFragment;
import com.gq.app.fragment.main.MineFragment;
import com.gq.app.http.weather.WeatherContract;
import com.gq.app.http.weather.WeatherPresenter;
import com.gq.app.model.EventBusCount;
import com.gq.app.model.WeatherEntity;
import com.gq.app.utils.Constants;
import com.gq.app.utils.ShareUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener, NavigationView
        .OnNavigationItemSelectedListener, WeatherContract.IWeatherView {

    //fragment
    private FragmentManager fm;
    private HomeFragment mHomeFragment;
    private MessageFragment mMessageFragment;
    private MineFragment mMineFragment;
    private Fragment mCurrent;

    //ui
    private RelativeLayout mHomeLayout;
    private RelativeLayout mPondLayout;
    private RelativeLayout mMessageLayout;
    private RelativeLayout mMineLayout;
    private TextView mHomeView;
    private TextView mPondView;
    private TextView mMessageView;
    private TextView mMineView;

    private NavigationView leftDraw;//抽屉
    private DrawerLayout mainDrawLayout;//跟布局
    //抽屉头部布局
    //头部布局
    private ImageView header_iv_weather;
    private RelativeLayout rl_weather;
    private TextView header_tv_temperature;
    private TextView header_tv_other;
    private TextView header_tv_city_name;
    private LinearLayout header_ll_choose_city;
    private static TextView mcount;
    private int count;
    private static final int citysChooseRequestCode = 10001;
    private String provinceName="北京";
    private String cityName="北京";


    /**
     * 初始化抽屉按钮
     */
    private void initDrawerView() {
        mainDrawLayout = (DrawerLayout) findViewById(R.id.main_draw_layout);
        leftDraw = (NavigationView) findViewById(R.id.left_draw);

        View headerLayout = leftDraw.inflateHeaderView(R.layout.activity_view_nav_header_main);
        //天气图标
        header_iv_weather = (ImageView) headerLayout.findViewById(R.id.header_iv_weather);
        rl_weather = (RelativeLayout) headerLayout.findViewById(R.id.rl_weather);
        //温度
        header_tv_temperature = (TextView) headerLayout.findViewById(R.id.header_tv_temperature);
        //天气、空气质量
        header_tv_other = (TextView) headerLayout.findViewById(R.id.header_tv_other);
        header_tv_city_name = (TextView) headerLayout.findViewById(R.id.header_tv_city_name);
        header_ll_choose_city = (LinearLayout) headerLayout.findViewById(R.id
                .header_ll_choose_city);
        mcount = (TextView) headerLayout.findViewById(R.id.reading_nums);
        //切换城市
        header_ll_choose_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CitysActivity.class);
                startActivityForResult(intent, citysChooseRequestCode);
            }
        });
        //显示天气大图
        rl_weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!header_tv_city_name.getText().equals("城市")){
                    Intent intent_weather = new Intent(MainActivity.this, WeatherActivity.class);

                    intent_weather.putExtra(WeatherActivity.intentKey_weatherProvinceName,
                            provinceName);
                    intent_weather.putExtra(WeatherActivity.intentKey_weatherCityName, cityName);

                    if (mweatherList != null && mweatherList.size() > 0) {
                        WeatherEntity.ResultBean weatheR=mweatherList.get(0);
                        intent_weather.putExtra(WeatherActivity.weatherResult, (Serializable)weatheR);
                    }
                    startActivity(intent_weather);
                }
            }
        });

        leftDraw.setNavigationItemSelectedListener(this);
    }

    //处理切换城市
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == citysChooseRequestCode) {
            if (data != null) {
                provinceName = data.getStringExtra("provinceName");
                cityName = data.getStringExtra("cityName");
                header_tv_city_name.setText(cityName);
                WeatherPresenter presenter = new WeatherPresenter(this, Constants.CITYKEY,
                        cityName, provinceName);
                presenter.getWeather();
            }
        }
    }

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        /*抽屉*/
        initDrawerView();

        /*底部标签页*/
        mHomeLayout = (RelativeLayout) findViewById(R.id.home_layout_view);
        mHomeLayout.setOnClickListener(this);
        //供后期修改添加
        mPondLayout = (RelativeLayout) findViewById(R.id.pond_layout_view);
        mPondLayout.setOnClickListener(this);
        mMessageLayout = (RelativeLayout) findViewById(R.id.message_layout_view);
        mMessageLayout.setOnClickListener(this);
        mMineLayout = (RelativeLayout) findViewById(R.id.mine_layout_view);
        mMineLayout.setOnClickListener(this);

        mHomeView = (TextView) findViewById(R.id.home_image_view);
        mPondView = (TextView) findViewById(R.id.fish_image_view);
        mMessageView = (TextView) findViewById(R.id.message_image_view);
        mMineView = (TextView) findViewById(R.id.mine_image_view);

        mHomeView.setBackgroundResource(R.drawable.comui_tab_home_selected);
    }

    //隐藏fragment,但是不销毁
    private void hideFragment(Fragment fragment, FragmentTransaction ft) {
        if (fragment != null) {
            ft.hide(fragment);
        }
    }

    @Override
    protected void initData() {
        saveWeather();
        //获取城市天气
        WeatherPresenter presenter = new WeatherPresenter(this, Constants.CITYKEY,
                cityName, provinceName);
        presenter.getWeather();

        //添加默认要显示的fragment
        mHomeFragment = new HomeFragment();
        fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.content_layout, mHomeFragment);
        fragmentTransaction.commit();

        //注册EventBus
        EventBus.getDefault().register(this);

        count = ShareUtils.getInt(this, "count", 0);
        mcount.setText("阅读量：" + count);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void upReadNum(EventBusCount eventBusCount) {
        //接受消息
        mcount.setText("阅读量：" + eventBusCount.getCount());
        ShareUtils.putInt(this, "count", eventBusCount.getCount());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        switch (v.getId()) {
            case R.id.home_layout_view:
                mHomeView.setBackgroundResource(R.drawable.comui_tab_home_selected);
                mPondView.setBackgroundResource(R.drawable.comui_tab_pond);
                mMessageView.setBackgroundResource(R.drawable.comui_tab_message);
                mMineView.setBackgroundResource(R.drawable.comui_tab_person);

                hideFragment(mMessageFragment, fragmentTransaction);
                hideFragment(mMineFragment, fragmentTransaction);
                //显示HomeFragment
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                    fragmentTransaction.add(R.id.content_layout, mHomeFragment);
                } else {
                    mCurrent = mHomeFragment;
                    fragmentTransaction.show(mHomeFragment);
                }
                break;
            case R.id.message_layout_view:
                mMessageView.setBackgroundResource(R.drawable.comui_tab_message_selected);
                mHomeView.setBackgroundResource(R.drawable.comui_tab_home);
                mPondView.setBackgroundResource(R.drawable.comui_tab_pond);
                mMineView.setBackgroundResource(R.drawable.comui_tab_person);

                hideFragment(mHomeFragment, fragmentTransaction);
                hideFragment(mMineFragment, fragmentTransaction);
                //显示MessageFragment
                if (mMessageFragment == null) {
                    mMessageFragment = new MessageFragment();
                    fragmentTransaction.add(R.id.content_layout, mMessageFragment);
                } else {
                    mCurrent = mMessageFragment;
                    fragmentTransaction.show(mMessageFragment);
                }
                break;
            case R.id.mine_layout_view:
                mMineView.setBackgroundResource(R.drawable.comui_tab_person_selected);
                mHomeView.setBackgroundResource(R.drawable.comui_tab_home);
                mPondView.setBackgroundResource(R.drawable.comui_tab_pond);
                mMessageView.setBackgroundResource(R.drawable.comui_tab_message);

                hideFragment(mMessageFragment, fragmentTransaction);
                hideFragment(mHomeFragment, fragmentTransaction);
                //显示MineFragment
                if (mMineFragment == null) {
                    mMineFragment = new MineFragment();
                    fragmentTransaction.add(R.id.content_layout, mMineFragment);
                } else {
                    mCurrent = mMineFragment;
                    fragmentTransaction.show(mMineFragment);
                }
                break;
        }

        fragmentTransaction.commit();
    }

    /**
     * 抽屉控件的选择监听
     *
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //抽屉控件的选中实现
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_daily_read:
                startActivity(new Intent(MainActivity.this, ReDailyActivity.class));
                Toast.makeText(this, "每日一文", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_gank:
                startActivity(new Intent(MainActivity.this, MeizhiActivity.class));
                Toast.makeText(this, "妹纸", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_home_recommend:
                startActivity(new Intent(MainActivity.this, ReDailyActivity.class));
                Toast.makeText(this, "每日推荐", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_setting:
//                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                Toast.makeText(this, "关于", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_stars:
                startActivity(new Intent(MainActivity.this, FavListActivity.class));
                Toast.makeText(this, "我的收藏", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }


    //  返回按键
    private long exitTime = 0;

    @Override
    public void onBackPressed() {
        if (mainDrawLayout.isDrawerOpen(GravityCompat.START)) {
            mainDrawLayout.closeDrawer(GravityCompat.START);
        } else {
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                super.onBackPressed();
            }
        }
    }

    private List<WeatherEntity.ResultBean> mweatherList = new ArrayList<>();

    @Override
    public void showWeatherData(List<WeatherEntity.ResultBean> weatherResult) {
        mweatherList = weatherResult;

        WeatherEntity.ResultBean weatherR = weatherResult.get(0);
        header_tv_temperature.setText(weatherR.mTemperature);
        header_tv_other.setText(weatherR.mWeather + " " + weatherR.mAirCondition);
        header_tv_city_name.setText(cityName);

        header_iv_weather.setBackgroundResource(
                ShareUtils.getInt(this,weatherR.mWeather,R.drawable.icon_weather_none));
    }

    @Override
    public void showInfo(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    /**
     * 保存天气数据
     */
    private void saveWeather() {
        ShareUtils.putInt(this, "未知", R.drawable.icon_weather_none);
        ShareUtils.putInt(this, "晴", R.drawable.icon_weather_sunny);
        ShareUtils.putInt(this, "阴", R.drawable.icon_weather_cloudy);
        ShareUtils.putInt(this, "多云", R.drawable.icon_weather_cloudy);
        ShareUtils.putInt(this, "少云", R.drawable.icon_weather_cloudy);
        ShareUtils.putInt(this, "晴间多云", R.drawable.icon_weather_cloudytosunny);
        ShareUtils.putInt(this, "局部多云", R.drawable.icon_weather_cloudy);
        ShareUtils.putInt(this, "雨", R.drawable.icon_weather_rain);
        ShareUtils.putInt(this, "小雨", R.drawable.icon_weather_rain);
        ShareUtils.putInt(this, "中雨", R.drawable.icon_weather_rain);
        ShareUtils.putInt(this, "大雨", R.drawable.icon_weather_rain);
        ShareUtils.putInt(this, "阵雨", R.drawable.icon_weather_rain);
        ShareUtils.putInt(this, "雷阵雨", R.drawable.icon_weather_thunderstorm);
        ShareUtils.putInt(this, "霾", R.drawable.icon_weather_haze);
        ShareUtils.putInt(this, "雾", R.drawable.icon_weather_fog);
        ShareUtils.putInt(this, "雨夹雪", R.drawable.icon_weather_snowrain);
    }

}
