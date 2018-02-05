package com.gq.app.http.redaily;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gq.app.MyApp;
import com.gq.app.base.BasePresenter;
import com.gq.app.model.EssayForDB;
import com.gq.app.utils.db.DBUtils;
import com.liaoinstan.springview.widget.SpringView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 获取每日一文数据
 */
public class ReDailyFgPresenter extends BasePresenter<IReDailyFgView>{

    private Context mContext;

    private TextView mTitleTextView,mAuthorTextView,mContentTextView;

    private FloatingActionButton mFloatingActionButton;

    private SpringView mSpringView;

    private IReDailyFgView gankView;

    private boolean flag=true;
    private EssayForDB essay;

    public ReDailyFgPresenter(Context context){
        this.mContext=context;
    }

    /**
     * 获取每日一文数据
     */
    public void getReDailyData() {

        if (flag) {
            gankView = getView();
            if (gankView != null) {
                mTitleTextView = gankView.getTitleTextView();
                mAuthorTextView=gankView.getAuthorTextView();
                mContentTextView=gankView.getContentTextView();
                mFloatingActionButton=gankView.getFloatingActionButton();
                mSpringView=gankView.getSpringView();
            }
            flag = false;
        }

//        if (isLoadMore) {
//            page += 1;
//        }

        essay = new EssayForDB();

        Observable.create(new Observable.OnSubscribe<Document>() {
            @Override
            public void call(Subscriber<? super Document> subscriber) {
                try {
                    Document document = Jsoup.connect("https://meiriyiwen.com/random").get();
                    subscriber.onNext(document);

                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onNext(null);
                    loadError(e);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .doOnNext(new Action1<Document>() {

                    @Override
                    public void call(Document document) {
//                        Element docContent = document.getElementById("article_show");
                        //为数据库封装类装入数据
                        essay.setTitle(document.select("h1").text());
                        essay.setAuthor(document.getElementsByClass("article_author").text());
                        essay.setContent(document.getElementsByClass("article_text").toString());
                        essay.setDigest(document.getElementsByClass("article_text").text().substring(0,
                                48));
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Document>() {
                    @Override
                    public void call(Document document) {
                        displayEssay();
                    }
                });



    }

    /**
     * 刷新数据
     */
    public void refreshData() {
        getReDailyData();
    }

    /**
     * 加载更多数据
     */
    public void loadMoreData() {
//        isLoadMore = true;
//        getmeizhiList();
    }

    private boolean isFav=true;

    private void displayEssay() {
        mTitleTextView.setText(essay.getTitle());
        mAuthorTextView.setText("作者：" + essay.getAuthor());
        mContentTextView.setText(Html.fromHtml(essay.getContent()));
        //停止加载与更新
        mSpringView.onFinishFreshAndLoad();

//        MainActivity.updateReadNum(essay.getContent().length());

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFav){
                    //收藏功能
                    boolean b = DBUtils.insert(MyApp.dbOpenHelper, essay.getTitle(), essay.getAuthor(),
                            essay.getDigest(), essay.getContent());
                    if (b) {
                        Toast.makeText(mContext, "收藏成功", Toast.LENGTH_SHORT).show();
                        isFav=false;
                    } else {
                        Toast.makeText(mContext, "收藏失败", Toast.LENGTH_SHORT).show();

                    }
                }else{
                    Toast.makeText(mContext, "以成功收藏", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(mContext, "加载失败", Toast.LENGTH_SHORT).show();
    }

}
