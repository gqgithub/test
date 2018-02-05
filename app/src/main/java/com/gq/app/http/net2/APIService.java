package com.gq.app.http.net2;

import com.gq.app.model.CitysEntity;
import com.gq.app.model.Gank;
import com.gq.app.model.WeatherEntity;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * API--接口
 * 服务[这里处理的是同一的返回格式 resultCode  resultInfo Data<T> --->
 *     这里的data才是返回的结果,T就是泛型 具体返回的been对象或集合]
 */
public interface APIService {
    //https://api.douban.com/v2/movie/top250?start=0&count=1
    //http://gank.io/api/
    //http://gank.io/api/data/Android?count=10&page=1

    //category 后面Android | iOS | 休息视频 |  拓展资源 | 前端 | 瞎推荐 | App

    /**
     * 获取gank数据
     * @param type
     * @param page
     * @return
     */
    //http://gank.io/api/data/iOS/10/1
    @GET("data/{type}/10/{page}")
    Observable<Gank> getGanks(@Path("type") String type,
                                 @Path("page") int page);

    /**
     * 获取城市数据
     * @param appkey
     * @return
     */
    //http://apicloud.mob.com/v1/weather/citys?key=1c9dccb9a2434
    @GET("v1/weather/citys")
    Observable<CitysEntity> getCitys(@Query("key") String appkey);

    /**
     * 获取天气数据
     * @param key
     * @param city
     * @param province
     * @return
     */
    //http://apicloud.mob.com/v1/weather/query?key=1c9dccb9a2434&city=沈阳&province=辽宁
    @GET("v1/weather/query")
    Observable<WeatherEntity> getWeather(@Query("key") String key,
                                         @Query("city") String city,
                                         @Query("province") String province);



//    @GET(Constants.URL_Mob + "v1/weather/query")
//    Call<WeatherBeseEntity> getCityWeather(@Query("key") String appkey,
//                                           @Query("city") String city,
//                                           @Query("province") String province
//    );

    /**
     * 查询豆瓣排名前250的电影
     *
     * @param start 从第几部开始
     * @param count 几页(一页有12部)
     * @return
     */
//    @GET("v2/movie/top250")
//    Observable<Movies> getMovies(@Query("start") int start, @Query("count") int count);


    /**
     * 用户登录的接口
     *
     * @param username 用户名
     * @param pwd      密码
     * @return RxJava 对象
     */
//    @POST("okhttp/UserInfoServlert")
//    Observable<UserHttpResult<TokenResult>> userLogin(@Query("username") String username, @Query("pwd") String pwd);

    /**
     * 查询ip地址信息的接口
     *
     * @param ip 需查询的ip
     * @return RxJava 对象
     */
//    @GET("service/getIpInfo.php")
//    Observable<IPHttpResult<IpInfo>> queryIp(@Query("ip") String ip);


}
