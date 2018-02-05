package com.gq.app.http.meizhi;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.liaoinstan.springview.widget.SpringView;


/**
 * Created by Administrator on 2017/12/29.
 */
public interface IMeizhiView {

    Toolbar getToolbar();

    RecyclerView getRecyclerView();

    SpringView getSpringView();


}
