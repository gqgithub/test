package com.gq.app.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/3.
 */
public class CitysEntity {

    @SerializedName("msg")
    public String mMsg;
    @SerializedName("retCode")
    public String mRetCode;
    @SerializedName("result")
    public List<ResultBean> mResult;

    public static List<CitysEntity> arrayCitysEntityFromData(String str) {

        Type listType = new TypeToken<ArrayList<CitysEntity>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static class ResultBean {
        /**
         * city : [{"city":"北京","district":[{"district":"北京"},{"district":"海淀"},{"district":"朝阳"},{"district":"顺义"},{"district":"怀柔"},{"district":"通州"},{"district":"昌平"},{"district":"延庆"},{"district":"丰台"},{"district":"石景山"},{"district":"大兴"},{"district":"房山"},{"district":"密云"},{"district":"门头沟"},{"district":"平谷"}]}]
         * province : 北京
         */

        @SerializedName("province")
        public String mProvince;
        @SerializedName("city")
        public List<CityBean> mCity;

        public static List<ResultBean> arrayResultBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<ResultBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public static class CityBean {
            /**
             * city : 北京
             * district : [{"district":"北京"},{"district":"海淀"},{"district":"朝阳"},{"district":"顺义"},{"district":"怀柔"},{"district":"通州"},{"district":"昌平"},{"district":"延庆"},{"district":"丰台"},{"district":"石景山"},{"district":"大兴"},{"district":"房山"},{"district":"密云"},{"district":"门头沟"},{"district":"平谷"}]
             */

            @SerializedName("city")
            public String mCity;
            @SerializedName("district")
            public List<DistrictBean> mDistrict;

            public static List<CityBean> arrayCityBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<CityBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public static class DistrictBean {
                /**
                 * district : 北京
                 */

                @SerializedName("district")
                public String mDistrict;

                public static List<DistrictBean> arrayDistrictBeanFromData(String str) {

                    Type listType = new TypeToken<ArrayList<DistrictBean>>() {
                    }.getType();

                    return new Gson().fromJson(str, listType);
                }
            }
        }
    }
}
