package com.gq.app.utils;

import com.gq.app.MyApp;
import com.gq.app.model.CitysEntity;

/**
 * Created by Administrator on 2018/2/3.
 */
public class UserUtils {

    private static final String cache_citys = "Cache_Citys";

    public static CitysEntity getCitysCache() {
        CitysEntity citysEntity = (CitysEntity) MyApp.getACache().getAsObject(cache_citys);
        return citysEntity;
    }
}
