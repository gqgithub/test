package com.gq.app.utils.snachbar;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * 提示框
 */
public class MySnackbar {

    public static void makeSnackBarBlack(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
//        int currentSkinType = SkinManager.getCurrentSkinType(MyApp.mContext);
//        if (SkinManager.THEME_DAY == currentSkinType) {
            ColoredSnackbar.defaultInfo(snackbar).show();
//        } else {
//            ColoredSnackbar.defaultInfoNight(snackbar).show();
//        }
    }

    public static void makeSnackBarRed(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        ColoredSnackbar.alert(snackbar).show();
    }

    public static void makeSnackBarBlue(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        ColoredSnackbar.info(snackbar).show();
    }

    public static void makeSnackBarOrange(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        ColoredSnackbar.warning(snackbar).show();
    }

    public static void makeSnackBarGreen(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        ColoredSnackbar.confirm(snackbar).show();
    }

}
