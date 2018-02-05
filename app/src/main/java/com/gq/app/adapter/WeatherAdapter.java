package com.gq.app.adapter;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gq.app.R;
import com.gq.app.model.WeatherEntity;
import com.gq.app.view.ArcProgressView;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

/**
 * Created by Administrator on 2018/2/5.
 */
public class WeatherAdapter extends RecyclerView.Adapter{

    private WeatherEntity.ResultBean mweatherR;

    private Context mContext;

    public WeatherAdapter(WeatherEntity.ResultBean weatherR,Context context){
        this.mweatherR=weatherR;
        mContext=context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(0==viewType){
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather_header,parent,false);
            return new OneHolder(view);
        }else if(1==viewType){
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather_later,parent,false);
            return new TwoHolder(view);
        }else if(2==viewType){
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather_air,parent,false);
            return new ThreeHolder(view);
        }else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OneHolder) {
            OneHolder oneHolder = (OneHolder) holder;
            oneHolder.bindItem(mweatherR);
        }else if (holder instanceof TwoHolder) {
            TwoHolder twoHolder = (TwoHolder) holder;
            twoHolder.bindItem2(mweatherR);
        }else if (holder instanceof ThreeHolder) {
            ThreeHolder threeHolder = (ThreeHolder) holder;
            threeHolder.bindItem3(mweatherR);
        }

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }



    public class OneHolder extends RecyclerView.ViewHolder{

        TextView tv_1,tv_2,tv_3,tv_4,tv_5;


        public OneHolder(View itemView) {
            super(itemView);
            tv_1 = (TextView) itemView.findViewById(R.id.tv_01);
            tv_2 = (TextView) itemView.findViewById(R.id.tv_02);
            tv_3 = (TextView) itemView.findViewById(R.id.tv_03);
            tv_4 = (TextView) itemView.findViewById(R.id.tv_04);
            tv_5 = (TextView) itemView.findViewById(R.id.tv_05);

        }

        public void bindItem(WeatherEntity.ResultBean resultBean) {
            Log.e("gang","第一次查询"+resultBean.toString());
            tv_1.setText(resultBean.mTemperature);
            tv_2.setText(resultBean.mWeather);
            tv_3.setText(resultBean.mFuture.get(0).mTemperature);
            tv_4.setText(resultBean.mAirCondition);
            tv_5.setText(resultBean.mWind);
        }

    }

    public class TwoHolder extends RecyclerView.ViewHolder{

        RecyclerView recycle_two;


        public TwoHolder(View itemView) {
            super(itemView);
            recycle_two = (RecyclerView) itemView.findViewById(R.id.recycle_later);
        }

        public void bindItem2(WeatherEntity.ResultBean resultBean) {
            Log.e("gang","第二次查询"+resultBean.toString());
            //初始化RecycleView
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            recycle_two.setLayoutManager(linearLayoutManager);
            recycle_two.setItemAnimator(new DefaultItemAnimator());
            recycle_two.addItemDecoration(
                    new VerticalDividerItemDecoration
                            .Builder(mContext)
                            .color(mContext.getResources().getColor(R.color.white))
                            .build());
            Weather2Adapter weather2Adapter = new Weather2Adapter(mContext, resultBean);
            recycle_two.setAdapter(weather2Adapter);

        }
    }

    public class ThreeHolder extends RecyclerView.ViewHolder{

        ArcProgressView arcProgressView;


        public ThreeHolder(View itemView) {
            super(itemView);
            arcProgressView = (ArcProgressView) itemView.findViewById(R.id.arc_progress);
        }

        public void bindItem3(WeatherEntity.ResultBean resultBean) {
            String pollutionIndex = resultBean.mPollutionIndex;
            arcProgressView.setCurrentCount(500, Integer.parseInt(pollutionIndex));

        }
    }


}
