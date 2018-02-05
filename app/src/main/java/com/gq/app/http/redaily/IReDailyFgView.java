package com.gq.app.http.redaily;

import android.support.design.widget.FloatingActionButton;
import android.widget.TextView;

import com.liaoinstan.springview.widget.SpringView;

/**
 * Created by Administrator on 2018/1/1.
 */
public interface IReDailyFgView {

    TextView getTitleTextView();

    TextView getAuthorTextView();

    TextView getContentTextView();

    FloatingActionButton getFloatingActionButton();

    SpringView getSpringView();


}
