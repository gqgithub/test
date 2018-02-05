package com.gq.app.http.meizhi;

import com.gq.app.http.net.Contants;
import com.gq.app.http.net.ObjectLoader;
import com.gq.app.http.net.RetrofitServiceManager;
import com.gq.app.model.MeizhiModel;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2017/12/27.
 */
public class MeizhiLoader extends ObjectLoader {


    private MeizhiService mMovieService;

    public MeizhiLoader(){
        mMovieService = RetrofitServiceManager.getInstance(Contants.GANK_BASE_URL).create(MeizhiService.class);
    }

    /**
     * 获取电影列表
     */
    public Observable<List<MeizhiModel.Meizhi>> getMeizhi(int page){
        return observe(mMovieService.getMeizhiData(page))
                .map(new Func1<MeizhiModel, List<MeizhiModel.Meizhi>>() {
                    @Override
                    public List<MeizhiModel.Meizhi> call(MeizhiModel meizhiModel) {
                        return meizhiModel.mResults;
                    }
                });
    }



    public interface MeizhiService{

        //获取豆瓣Top250 榜单  @GET("article/day?dev=1")
        @GET("data/福利/10/{page}")
        Observable<MeizhiModel> getMeizhiData(@Path("page") int page);

//        @GET("movie/top250")
//        Observable<Movie> getTop250(@Query("start") int start, @Query("count")int count);


    }
}
