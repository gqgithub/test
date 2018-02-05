package com.gq.app.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.gq.app.MyApp;
import com.gq.app.R;
import com.gq.app.adapter.FavAdapter;
import com.gq.app.model.EssayForDB;
import com.gq.app.utils.db.DBUtils;

import java.util.List;

/**
 * 显示收藏页
 */
public class FavListActivity extends AppCompatActivity{

    Toolbar mToolbar;
    TextView tb_title;
    RecyclerView mRecyclerView;
    static FavAdapter mFavAdapter;
    static List<EssayForDB> mEssays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);

        initToolbar();

        initData();

    }

    private void initData() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_fav);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mEssays = DBUtils.query(MyApp.dbOpenHelper);
        if (mEssays.isEmpty()) {
            Toast.makeText(this, "您还没有收藏过文章哦～～～", Toast.LENGTH_SHORT).show();
        } else {
            Log.e("mList+++++", mEssays.get(0).getDigest());
            mFavAdapter = new FavAdapter(this);
            mFavAdapter.setEssay(mEssays);

            mRecyclerView.setAdapter(mFavAdapter);
        }
    }

    private void initToolbar() {
        mToolbar= (Toolbar) findViewById(R.id.my_toolbar);
        tb_title= (TextView) findViewById(R.id.toolbar_title);
        tb_title.setText("我的收藏");
        setSupportActionBar(mToolbar);
        ActionBar actionBar =  getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home://原生的返回键
                finish();
                break;
        }
        return true;
    }

    ////更新收藏页
    public static void upDateView(String title) {
        for (int i = 0; i < mEssays.size() ; i++) {
            if (mEssays.get(i).getTitle().equals(title)) {
                mEssays.remove(i);
                break;
            }
        }
        mFavAdapter.setEssay(mEssays);
        mFavAdapter.notifyDataSetChanged();
//        recyclerView.setAdapter(mAdapter);
    }


}
