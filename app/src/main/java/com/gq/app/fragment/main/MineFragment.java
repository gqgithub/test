package com.gq.app.fragment.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gq.app.R;

/**
 * Created by Administrator on 2018/1/1.
 */
public class MineFragment extends Fragment{

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        view=View.inflate(getActivity(), R.layout.fragment_mine,null);

        return view;
    }
}
