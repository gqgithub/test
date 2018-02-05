package com.gq.app.utils;

import android.content.Context;

/**
 * 主题切换管理工具
 */
public class SkinManager {

    public final static int THEME_DAY = 0;
    public final static int THEME_NIGHT = 1;


    /**
     * 获取当前主题的Type
     *
     * @param context
     * @return 0：白天主题；1：夜间主题
     */
    public static int getCurrentSkinType(Context context) {
        return getSharePreSkin(context, THEME_DAY);
    }

    private static int getSharePreSkin(Context context, int defValue) {
        return ShareUtils.getInt(context,ShareUtils.SkinKey,defValue);
    }


}
