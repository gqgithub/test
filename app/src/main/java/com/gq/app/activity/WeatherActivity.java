package com.gq.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gq.app.R;
import com.gq.app.adapter.WeatherAdapter;
import com.gq.app.model.MeizhiModel;
import com.gq.app.model.WeatherEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/4.
 */
public class WeatherActivity extends AppCompatActivity{

    public static String intentKey_weatherProvinceName="ProvinceName";
    public static String intentKey_weatherCityName="CityName";
    public static String weatherResult="weatherResult";
    public static String intentKey_bg_url="meizhi";

    TextView tvTitle;
    RecyclerView mRecyclerView;
    ImageView ivBg;
    ImageView ivBg2;
    LinearLayout llContent;
    LinearLayout llBgBlur;

    WeatherAdapter mAdapter;

    private WeatherEntity.ResultBean mweatherR;
    private List<MeizhiModel.Meizhi> mmeizhis = new ArrayList<>();
    String provinceName;
    String cityName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        initIntent();

        initView();

    }

    private void initView() {
        tvTitle= (TextView) findViewById(R.id.w_tv_title);
        tvTitle.setText(cityName);

        mRecyclerView= (RecyclerView) findViewById(R.id.wa_recycle);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//设置为listview的布局
        mAdapter=new WeatherAdapter(mweatherR,this);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void initIntent() {
        mweatherR = (WeatherEntity.ResultBean) getIntent().getSerializableExtra(weatherResult);
//        mmeizhis = (List<MeizhiModel.Meizhi>) getIntent().getSerializableExtra(intentKey_bg_url);
        provinceName = getIntent().getStringExtra(intentKey_weatherProvinceName);
        cityName = getIntent().getStringExtra(intentKey_weatherCityName);


    }



}
