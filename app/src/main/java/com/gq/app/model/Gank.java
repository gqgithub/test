package com.gq.app.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/18.
 */
public class Gank {


    /**
     * error : false
     * results : [{"_id":"576e5468421aa931d70b5f52","createdAt":"2016-06-25T17:52:40.885Z",
     * "desc":"iOS应用架构谈 view层的组织和调用方案 - Casa Taloyum","publishedAt":"2018-01-16T08:40:08.101Z",
     * "source":"api","type":"iOS","url":"http://casatwy
     * .com/iosying-yong-jia-gou-tan-viewceng-de-zu-zhi-he-diao-yong-fang-an.html","used":true,
     * "who":"tripleCC"},{"_id":"576e5480421aa931d70b5f53","createdAt":"2016-06-25T17:53:04
     * .181Z","desc":"iOS应用架构谈 网络层设计方案 - Casa Taloyum","publishedAt":"2018-01-16T08:40:08.101Z",
     * "source":"api","type":"iOS","url":"http://casatwy
     * .com/iosying-yong-jia-gou-tan-wang-luo-ceng-she-ji-fang-an.html","used":true,
     * "who":"tripleCC"},{"_id":"5a4f79a6421aa90fe50c02c8","createdAt":"2018-01-05T21:12:06
     * .833Z","desc":"四款下拉刷新的比较，更少bug的KafkaRefresh","images":["http://img.gank
     * .io/1487df5d-362b-4593-b525-2f1ee6c6c850"],"publishedAt":"2018-01-10T07:57:19.486Z",
     * "source":"web","type":"iOS","url":"https://github.com/xorshine/KafkaRefresh","used":true,
     * "who":"K"},{"_id":"5a4cb9b9421aa90fe50c02bf","createdAt":"2018-01-03T19:08:41.87Z",
     * "desc":"用安卓、rn的方式开发ios原生app","publishedAt":"2018-01-04T13:45:57.211Z","source":"web",
     * "type":"iOS","url":"https://github.com/zhenglibao/FlexLib/blob/master/README.zh.md",
     * "used":true,"who":"zhenglibao"},{"_id":"5a44b54a421aa90fef2035cf",
     * "createdAt":"2017-12-28T17:11:38.645Z",
     * "desc":"Autolayout是苹果力推的，但实在是让人无法喜欢，基于此的Masonry框架也需要有替代品了~",
     * "publishedAt":"2018-01-02T08:43:32.216Z","source":"web","type":"iOS","url":"https://juejin
     * .im/post/5a4468f3f265da432a7be16c#comment","used":true,"who":"zhenglibao"},
     * {"_id":"5a426790421aa90fef2035c8","createdAt":"2017-12-26T23:15:28.638Z",
     * "desc":"iOS优秀第三方库汇总","publishedAt":"2017-12-27T12:13:22.418Z","source":"chrome",
     * "type":"iOS","url":"https://www.jianshu.com/p/91232c11770e","used":true,"who":"ray"},
     * {"_id":"5a3623b1421aa90fe2f02ce6","createdAt":"2017-12-17T15:58:41.308Z",
     * "desc":"Boostnote是为程序员开发的一种记事本开放源码应用程序。  我们的GitHub 星数已经超过了5,
     * 400。Boostnote主要的功能有两个。Markdown笔记,Snippet笔记。 参考:https://zhuanlan.zhihu.com/p/32015294",
     * "publishedAt":"2017-12-25T08:28:04.64Z","source":"web","type":"iOS",
     * "url":"https://boostnote.io/","used":true,"who":null},{"_id":"5a379464421aa90fe72536c2",
     * "createdAt":"2017-12-18T18:11:48.869Z","desc":"IOS新一代界面开发利器 \u2014\u2014 FlexLib",
     * "publishedAt":"2017-12-19T12:00:28.893Z","source":"web","type":"iOS","url":"https://github
     * .com/zhenglibao/FlexLib","used":true,"who":"zhenglibao"},
     * {"_id":"5a32367f421aa90fe72536b9","createdAt":"2017-12-14T16:29:51.903Z","desc":"Fluid
     * Slider [Swift, Open Source] A slider widget with a popup bubble displaying the precise
     * value selected.","images":["http://img.gank.io/0fc0125d-268b-4f87-ac31-3aff43e69bd4"],
     * "publishedAt":"2017-12-15T08:59:11.361Z","source":"web","type":"iOS","url":"https://github
     * .com/Ramotion/fluid-slider","used":true,"who":"Alex Mikhnev"},
     * {"_id":"5a2dd246421aa90fe50c026f","createdAt":"2017-12-11T08:33:10.107Z","desc":"Powermode
     * 输入效果，shaking，shaking！","images":["http://img.gank
     * .io/9a4bf261-4863-4072-a153-ced9adbc05b2"],"publishedAt":"2017-12-11T08:43:39.682Z",
     * "source":"chrome","type":"iOS","url":"https://github.com/younatics/PowerMode","used":true,
     * "who":"代码家"}]
     */

    @SerializedName("error")
    public boolean mError;
    @SerializedName("results")
    public List<ResultsBean> mResults;

    public static List<Gank> arrayGankFromData(String str) {

        Type listType = new TypeToken<ArrayList<Gank>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static class ResultsBean {
        /**
         * _id : 576e5468421aa931d70b5f52
         * createdAt : 2016-06-25T17:52:40.885Z
         * desc : iOS应用架构谈 view层的组织和调用方案 - Casa Taloyum
         * publishedAt : 2018-01-16T08:40:08.101Z
         * source : api
         * type : iOS
         * url : http://casatwy.com/iosying-yong-jia-gou-tan-viewceng-de-zu-zhi-he-diao-yong-fang
         * -an.html
         * used : true
         * who : tripleCC
         * images : ["http://img.gank.io/1487df5d-362b-4593-b525-2f1ee6c6c850"]
         */

        @SerializedName("_id")
        public String mId;
        @SerializedName("createdAt")
        public String mCreatedAt;
        @SerializedName("desc")
        public String mDesc;
        @SerializedName("publishedAt")
        public String mPublishedAt;
        @SerializedName("source")
        public String mSource;
        @SerializedName("type")
        public String mType;
        @SerializedName("url")
        public String mUrl;
        @SerializedName("used")
        public boolean mUsed;
        @SerializedName("who")
        public String mWho;
        @SerializedName("images")
        public List<String> mImages;

        public static List<ResultsBean> arrayResultsBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<ResultsBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }
    }
}
