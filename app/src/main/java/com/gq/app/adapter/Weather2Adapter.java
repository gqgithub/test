package com.gq.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gq.app.R;
import com.gq.app.model.WeatherEntity;
import com.gq.app.utils.ShareUtils;

/**
 * Created by Administrator on 2018/2/5.
 */
public class Weather2Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static Context mContext;
    private WeatherEntity.ResultBean weatherEntity;
    private LayoutInflater layoutInflater;


    public Weather2Adapter(Context context, WeatherEntity.ResultBean weatherEntity) {
        this.mContext = context;
        this.weatherEntity = weatherEntity;
        layoutInflater = LayoutInflater.from(this.mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = layoutInflater.inflate(R.layout.item_weather_later_item, parent, false);
        return new Weather2Adapter.MyViewHolder01(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder01) {
            final MyViewHolder01 myViewHolder01 = (MyViewHolder01) holder;
            myViewHolder01.bindItem(weatherEntity.mFuture.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return weatherEntity.mFuture.size();
    }


    public static class MyViewHolder01 extends RecyclerView.ViewHolder {

        TextView tv_01;
        TextView tv_02;
        TextView tv_03;
        TextView tv_04;
        TextView tv_05;
        TextView tv_06;
        TextView tv_07;
        TextView tv_08;
        ImageView iv_01;
        ImageView iv_02;

        public MyViewHolder01(View itemView) {
            super(itemView);
            tv_01= (TextView) itemView.findViewById(R.id.tv_01);
            tv_02= (TextView) itemView.findViewById(R.id.tv_02);
            tv_03= (TextView) itemView.findViewById(R.id.tv_03);
            tv_04= (TextView) itemView.findViewById(R.id.tv_04);
            tv_05= (TextView) itemView.findViewById(R.id.tv_05);
            tv_06= (TextView) itemView.findViewById(R.id.tv_06);
            tv_07= (TextView) itemView.findViewById(R.id.tv_07);
            tv_08= (TextView) itemView.findViewById(R.id.tv_08);
            iv_01= (ImageView) itemView.findViewById(R.id.iv_01);
            iv_02= (ImageView) itemView.findViewById(R.id.iv_02);
        }

        public void bindItem(WeatherEntity.ResultBean.FutureBean futureBean) {

            tv_01.setText(futureBean.mWeek);
            tv_02.setText(futureBean.mDate.substring(5));
            tv_03.setText(futureBean.mDayTime);
            //最高温度和最低温度
            String temperature = futureBean.mTemperature;
            if(!TextUtils.isEmpty(temperature)){
                if(temperature.length() > 1 && temperature.contains("/")){
                    String[] tempers = temperature.split("/");
                    if(tempers.length > 0){
                        tv_04.setText(tempers[0].replace(" ", ""));
                        tv_05.setText(tempers[1].replace(" ", ""));
                    }
                }
            }

            tv_06.setText(futureBean.mNight);

            //风向和风速
            String wind = futureBean.mWind;
            if(!TextUtils.isEmpty(wind)){
                if(wind.length() > 1 && wind.contains(" ")){
                    String[] winds = wind.split(" ");
                    if(winds.length > 0){
                        tv_07.setText(winds[0].replace(" ", ""));
                        tv_08.setText(winds[1].replace(" ", ""));
                    }
                }
            }

            iv_01.setImageDrawable(mContext.getResources()
                    .getDrawable(ShareUtils.getInt(
                                    mContext,
                                    futureBean.mDayTime,
                                    R.drawable.icon_weather_none)
                    ));

            iv_02.setImageDrawable(mContext.getResources()
                    .getDrawable(ShareUtils.getInt(
                                    mContext,
                                    futureBean.mNight,
                                    R.drawable.icon_weather_none)
                    ));
        }
    }

}
