package com.gq.app.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gq.app.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * 大图浏览界面
 * 1.FloatingActionButton(悬浮圆形按钮)
 * 2.下载图片到SDKcard根目录
 */

public class PictureActivity extends AppCompatActivity {

    public static final String IMG_URL = "img_url";
    public static final String IMG_DESC = "img_desc";
    public static final String TRANSIT_PIC = "picture";

    private String img_url;
    private String img_desc;

    private ImageView iv_meizhi_pic;
    private FloatingActionButton save_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acrtivity_picture);
        initView();
        parseIntent();
        // 共享元素
        ViewCompat.setTransitionName(iv_meizhi_pic,TRANSIT_PIC);
        Glide.with(this).load(img_url).centerCrop().into(iv_meizhi_pic);
        new PhotoViewAttacher(iv_meizhi_pic);
    }

    private void initView() {
        iv_meizhi_pic = (ImageView) findViewById(R.id.iv_meizhi_pic);
        save_img = (FloatingActionButton) findViewById(R.id.save_img);
        save_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImage();
            }
        });
    }

    private void parseIntent() {
        img_url = getIntent().getStringExtra(IMG_URL);
        img_desc = getIntent().getStringExtra(IMG_DESC);
//        Toast.makeText(this, "img_url:"+img_url+"img_desc:"+img_desc, Toast.LENGTH_SHORT).show();
        Log.e("gang","img_url:"+img_url+"img_desc:"+img_desc);
    }

    public static Intent newIntent(Context context, String url, String desc){
        Intent intent = new Intent(context,PictureActivity.class);
        intent.putExtra(PictureActivity.IMG_URL,url);
        intent.putExtra(PictureActivity.IMG_DESC,desc);
        return intent;
    }

    //保存图片到
    private void saveImage() {
        iv_meizhi_pic.buildDrawingCache();
        Bitmap bitMap = iv_meizhi_pic.getDrawingCache();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitMap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/meizhi");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(dir,img_desc.substring(0,5)+".png");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(byteArray,0,byteArray.length);
            fos.flush();
            // 用广播通知相册更新相册
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            PictureActivity.this.sendBroadcast(intent);
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        }catch (IOException e) {
            Toast.makeText(this, "失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Glide.clear(iv_meizhi_pic);
    }
}
