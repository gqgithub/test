package com.gq.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gq.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/3.
 */
public class RecycleCitysAdapter extends RecyclerView.Adapter implements View.OnClickListener{

    private List<String> mprovinceList = new ArrayList<>();
    private Context mContext;

    public RecycleCitysAdapter(Context mContext,List<String> provinceList){
        this.mContext=mContext;
        mprovinceList = provinceList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time,parent,false);
        //1.2
        view.setOnClickListener(this);
        return new FavHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FavHolder favHolder = (FavHolder) holder;
        favHolder.bindItem(mprovinceList.get(position));
        //1.3
        favHolder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return  mprovinceList == null ? 0:mprovinceList.size();
    }

    public class FavHolder extends RecyclerView.ViewHolder{
        TextView tvTime;


        public FavHolder(View itemView) {
            super(itemView);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
        }

        public void bindItem(final String essay) {
            tvTime.setText(essay);
        }

    }

    //1.0添加item点击事件
    private OnItemClickListener mItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    //1.1
    @Override
    public void onClick(View v) {
        if (mItemClickListener!=null){
            mItemClickListener.onItemClick(v,(Integer) v.getTag());
        }
    }

    //1.4提供外部调用
    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
}
