package com.gq.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gq.app.R;
import com.gq.app.activity.EssayActivity;
import com.gq.app.model.EssayForDB;

import java.util.List;

/**
 * 收藏适配器
 */
public class FavAdapter extends RecyclerView.Adapter{

    private List<EssayForDB> mEssay;
    private Context mContext;

    public FavAdapter(Context mContext){
        this.mContext=mContext;
    }


    public void setEssay(List<EssayForDB> essay) {
        mEssay = essay;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favessay_list,null);
        return new FavHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FavHolder favHolder = (FavHolder) holder;
        favHolder.bindItem(mEssay.get(position));
    }

    @Override
    public int getItemCount() {
        return  mEssay == null ? 0:mEssay.size();
    }

    public class FavHolder extends RecyclerView.ViewHolder{
        CardView card_Essay;
        TextView title;
        TextView author;
        TextView digest;
        ImageView iv_type_bg;


        public FavHolder(View itemView) {
            super(itemView);
            card_Essay = (CardView) itemView.findViewById(R.id.card_fav);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            digest = (TextView) itemView.findViewById(R.id.tv_digest);
            author = (TextView) itemView.findViewById(R.id.tv_author);
            iv_type_bg = (ImageView) itemView.findViewById(R.id.iv_type_bg);
        }

        public void bindItem(final EssayForDB essay) {

            iv_type_bg.setBackgroundResource(R.color.android);
            title.setText("我的收藏 : " + essay.getTitle());
            //取出html标签
            digest.setText(Html.fromHtml(essay.getDigest()));
            author.setText("via : " + essay.getAuthor());


            card_Essay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转页面
                    Intent intent=new Intent(mContext,EssayActivity.class);
                    intent.putExtra("essay",essay);
                    mContext.startActivity(intent);
                }
            });
        }

    }
}
