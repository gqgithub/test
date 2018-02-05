package com.gq.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gq.app.MyApp;
import com.gq.app.R;
import com.gq.app.model.Gank;
import com.gq.app.utils.db.CollectDBUtils;
import com.gq.app.utils.snachbar.MySnackbar;
import com.ldoublem.thumbUplib.ThumbUpView;

import java.util.List;

/**
 * 干货适配器
 */
public class PublicAdapter extends RecyclerView.Adapter implements View.OnClickListener{

    private List<Gank.ResultsBean> mGankResult;
    private Context mContext;

    public PublicAdapter(Context mContext){
        this.mContext=mContext;
    }


    public void setGank(List<Gank.ResultsBean> gankResult) {
        mGankResult = gankResult;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gank_public,parent,false);
        //1.2
        view.setOnClickListener(this);
        return new PulbicHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PulbicHolder pubHolder = (PulbicHolder) holder;
        pubHolder.bindItem(mGankResult.get(position));
        //1.3
        pubHolder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return  mGankResult == null ? 0:mGankResult.size();
    }

    public class PulbicHolder extends RecyclerView.ViewHolder{

        TextView tvShowWho;
        TextView tvShowTitle;
        TextView tvShowTime;
        ThumbUpView btnCollect;
        ImageView ivShow;


        public PulbicHolder(View itemView) {
            super(itemView);
            tvShowWho = (TextView) itemView.findViewById(R.id.tvShowWho);
            tvShowTitle = (TextView) itemView.findViewById(R.id.tvShowTitle);
            tvShowTime = (TextView) itemView.findViewById(R.id.tvShowTime);
            btnCollect = (ThumbUpView) itemView.findViewById(R.id.btn_collect);
            ivShow = (ImageView) itemView.findViewById(R.id.iv_show);
        }

        public void bindItem(final Gank.ResultsBean resultsBean) {

            tvShowWho.setText(resultsBean.mWho);
            tvShowTitle.setText(resultsBean.mDesc);
            tvShowTime.setText(resultsBean.mPublishedAt.split("T")[0]);

            //图片展示
            String imageUrl = "";
            List<String> images = resultsBean.mImages;
            if (images != null && images.size() > 0) {
                imageUrl = images.get(0);
            }
            if (TextUtils.isEmpty(imageUrl)) {
                ivShow.setVisibility(View.GONE);
            } else {
                ivShow.setVisibility(View.VISIBLE);
                Glide.with(mContext)
                        .load(imageUrl)
                        .centerCrop()
                        .into(ivShow);
            }

//            //查询是否存在收藏
            boolean isCollect = new CollectDBUtils().queryOneCollectByID(MyApp.dbOpenHelper,resultsBean.mId);
            if (isCollect) {
                btnCollect.setLike();
            } else {
                btnCollect.setUnlike();
            }

            //收藏、点赞
            btnCollect.setOnThumbUp(new ThumbUpView.OnThumbUp() {
                @Override
                public void like(boolean like) {
                    if (like) {
                        boolean insertResult = new CollectDBUtils().insertOneCollect(MyApp.dbOpenHelper,resultsBean);
                        if (insertResult) {
                            MySnackbar.makeSnackBarBlack(tvShowTime, "收藏成功");
                        } else {
                            btnCollect.setUnlike();
                            MySnackbar.makeSnackBarRed(tvShowTime, "收藏失败");
                        }
                    } else {
                        boolean deleteResult = new CollectDBUtils().deleteOneCollect(MyApp.dbOpenHelper,resultsBean.mId);
                        if (deleteResult) {
                            MySnackbar.makeSnackBarBlack(tvShowTime, "取消收藏成功");
                        } else {
                            btnCollect.setLike();
                            MySnackbar.makeSnackBarRed(tvShowTime, "取消收藏失败");
                        }
                    }
                }
            });

        }
    }


    //1.0添加item点击事件
    private OnItemClickListener mItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    //1.1
    @Override
    public void onClick(View v) {
        if (mItemClickListener!=null){
            mItemClickListener.onItemClick((Integer) v.getTag());
        }
    }

    //1.4提供外部调用
    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
}
