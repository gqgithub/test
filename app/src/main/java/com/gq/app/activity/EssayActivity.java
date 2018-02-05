package com.gq.app.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.gq.app.MyApp;
import com.gq.app.R;
import com.gq.app.model.EssayForDB;
import com.gq.app.utils.db.DBUtils;

/**
 * 收藏页点击详情页
 */
public class EssayActivity extends AppCompatActivity{

    private EssayForDB essay ;
    private TextView title,author,content;
    private FloatingActionButton del_essay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_essay);
        //获取数据
        parseIntent();
        initView();

    }

    private void initView() {
        title = (TextView) findViewById(R.id.essay_title);
        author = (TextView) findViewById(R.id.essay_author);
        content = (TextView) findViewById(R.id.essay_content);
        del_essay = (FloatingActionButton) findViewById(R.id.del_essay);

        title.setText(essay.getTitle());
        author.setText(essay.getAuthor());
        content.setText(Html.fromHtml(essay.getContent()));

        //刷除收藏
        del_essay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹框Dialog
                AlertDialog.Builder dialog = new AlertDialog.Builder(EssayActivity.this);
                dialog.setTitle("删除文章");
                dialog.setMessage("您确定删除" + essay.getAuthor() + "的《" + essay.getTitle() +
                        "》这篇文章吗？");
                dialog.setNegativeButton("取消", null);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBUtils.delete(MyApp.dbOpenHelper, essay.getTitle());
                        //更新收藏页
                        FavListActivity.upDateView(essay.getTitle());
                        EssayActivity.this.finish();
                    }
                });
                dialog.show();
            }
        });
    }

    /**
     * 得到Intent传递的数据
     */
    private void parseIntent() {
        essay = (EssayForDB) getIntent().getSerializableExtra("essay");
    }
}
