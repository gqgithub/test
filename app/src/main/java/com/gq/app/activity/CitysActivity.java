package com.gq.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.gq.app.R;
import com.gq.app.adapter.RecycleCitysAdapter;
import com.gq.app.http.city.CityContract;
import com.gq.app.http.city.CityPresenter;
import com.gq.app.model.CitysEntity;
import com.gq.app.utils.Constants;
import com.gq.app.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/2/3.
 * 选择城市
 */
public class CitysActivity extends AppCompatActivity implements CityContract.ICityView {
    Toolbar mToolbar;
    TextView tb_title;

    RecyclerView recycleProvince;
    RecyclerView recycleCity;

    private List<String> provinceList = new ArrayList<>();
    private List<String> citysList = new ArrayList<>();
    private Map<String, List<String>> citysMap = new HashMap<>();
    private RecycleCitysAdapter recycleCitysAdapter_province;
    private RecycleCitysAdapter recycleCitysAdapter_city;
    private String chooseProvinceName;
    private String chooseCityName;

    CityPresenter mCityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        //获取数据
        mCityPresenter = new CityPresenter(this, Constants.CITYKEY);
        mCityPresenter.getCity();//启动软件时默认加载

        //初始化toolbar
        initToolbar();

        //初始化RecycleView
        recycleProvince = (RecyclerView) findViewById(R.id.recycle_province);
        recycleCity = (RecyclerView) findViewById(R.id.recycle_city);

        recycleProvince.setLayoutManager(new LinearLayoutManager(this));//设置为listview的布局
        recycleProvince.setItemAnimator(new DefaultItemAnimator());

        recycleCity.setLayoutManager(new LinearLayoutManager(this));
        recycleCity.setItemAnimator(new DefaultItemAnimator());

    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        tb_title = (TextView) findViewById(R.id.toolbar_title);
        tb_title.setText("选择城市");
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home://原生的返回键
                finish();
                break;
        }
        return true;
    }

    @Override
    public Context getCurContext() {
        return this;
    }

    @Override
    public void showCityData(List<CitysEntity.ResultBean> cityResult) {

        for (int i = 0; i < cityResult.size(); i++) {
            String province = cityResult.get(i).mProvince;
            provinceList.add(province);
            List<String> citysNameList = new ArrayList<>();
            List<CitysEntity.ResultBean.CityBean> city = cityResult.get(i).mCity;
            if (city != null) {
                for (int j = 0; j < city.size(); j++) {
                    String cityName = city.get(j).mCity;
                    citysNameList.add(cityName);
                }
                citysMap.put(province, citysNameList);
            }
        }
        recycleCitysAdapter_province = new RecycleCitysAdapter(this, provinceList);
        recycleProvince.setAdapter(recycleCitysAdapter_province);

        //点击事件
        recycleCitysAdapter_province.setItemClickListener(new RecycleCitysAdapter
                .OnItemClickListener() {

            @Override
            public void onItemClick(View view,int position) {
                chooseProvinceName = provinceList.get(position);
                citysList = citysMap.get(chooseProvinceName);
                //刷新第二个列表
                recycleCitysAdapter_city = new RecycleCitysAdapter(CitysActivity.this,
                        citysList);
                recycleCity.setAdapter(recycleCitysAdapter_city);
                //点击事件
                recycleCitysAdapter_city.setItemClickListener(new RecycleCitysAdapter
                        .OnItemClickListener() {

                    @Override
                    public void onItemClick(View view,int position) {
//                        chooseCityName = recycleCitysAdapter_city.getPositionValue(position);
                        chooseCityName=citysList.get(position);

                        //关闭界面
                        Intent intent = new Intent();
                        intent.putExtra("provinceName", chooseProvinceName);
                        intent.putExtra("cityName", chooseCityName);
                        setResult(100, intent);
                        finish();
                    }
                });

            }
        });

    }

    @Override
    public void showInfo(String info) {
        ToastUtils.showToast(this, info);
    }
}
