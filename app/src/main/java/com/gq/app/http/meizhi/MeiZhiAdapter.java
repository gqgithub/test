package com.gq.app.http.meizhi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gq.app.R;
import com.gq.app.activity.PictureActivity;
import com.gq.app.model.MeizhiModel;

import java.util.List;

/**
 * Created by Administrator on 2017/12/29.
 */
public class MeiZhiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<MeizhiModel.Meizhi> mList;

    public MeiZhiAdapter(Context context,List<MeizhiModel.Meizhi> list) {
        this.context = context;
        mList=list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gank_meizhi, parent, false);
        return new MeizhiViewholder(rootView) ;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MeizhiViewholder) {
            MeizhiViewholder gankMeizhiViewHolder = (MeizhiViewholder) holder;
            gankMeizhiViewHolder.bindItem(mList.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MeizhiViewholder extends RecyclerView.ViewHolder {
        private CardView mCardView_meizhi;
        private ImageView iv_meizhi;

        public MeizhiViewholder(View itemView) {
            super(itemView);
            mCardView_meizhi = (CardView) itemView.findViewById(R.id.card_meizhi);
            iv_meizhi = (ImageView) itemView.findViewById(R.id.iv_meizhi);
        }

        public void bindItem(final MeizhiModel.Meizhi meizhi) {

            Glide.with(context).load(meizhi.mUrl).centerCrop().into(iv_meizhi);

            // 图片点击事件
            iv_meizhi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = PictureActivity.newIntent(context, meizhi.mUrl, meizhi
                            .mDesc);
                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            (Activity)context,iv_meizhi,PictureActivity.TRANSIT_PIC);

                    try {
                        ActivityCompat.startActivity((Activity) context, intent, optionsCompat
                                .toBundle());
                    }catch (Exception e) {
                        e.printStackTrace();
                        context.startActivity(intent);
                    }
                }
            });
            // card 点击事件
            mCardView_meizhi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = GankActivity.newIntent(context, meizhi.getPublishedAt()
//                            .getTime());
//                    context.startActivity(intent);
                }
            });
        }


    }
}
