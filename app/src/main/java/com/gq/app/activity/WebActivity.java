package com.gq.app.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gq.app.R;
import com.gq.app.utils.Constants;
import com.gq.app.utils.NetUtils;
import com.gq.app.utils.snachbar.MySnackbar;

/**
 * 网页Activity
 */
public class WebActivity extends AppCompatActivity{

    private Toolbar mToolbar;
    private TextView tb_title;
    private ProgressBar progressbar;
    private WebView webView;
    String url;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        webView = (WebView) findViewById(R.id.webView);

        Intent intent = getIntent();
        title = intent.getStringExtra(Constants.WebTitle);
        url = intent.getStringExtra(Constants.WebUrl);

        initToolbar();
        initWebView();
    }

    private void initToolbar() {
        mToolbar= (Toolbar) findViewById(R.id.my_toolbar);
        tb_title= (TextView) findViewById(R.id.toolbar_title);
        //先设置标题
        tb_title.setText(title);
        setSupportActionBar(mToolbar);
        ActionBar actionBar =  getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    private void initWebView() {
        //设置背景色
        webView.setBackgroundColor(0);
        //设置WebView属性，能够执行Javascript脚本
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        // 开启DOM storage API 功能
        webView.getSettings().setDomStorageEnabled(true);
        // 开启database storage API功能
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        //自适应屏幕
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        // 设置可以支持缩放
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        //不显示webview缩放按钮
        webView.getSettings().setDisplayZoomControls(false);
        //设置缩放比例：最小25
        webView.setInitialScale(100);
        // 建议缓存策略为，判断是否有网络，有的话，使用LOAD_DEFAULT,无网络时，使用LOAD_CACHE_ELSE_NETWORK
        if (NetUtils.hasNetWorkConection(this)) {
            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);   // 根据cache-control决定是否从网络上取数据。
        } else {
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);   //优先加载缓存
        }

        //////////////////////////////
        webView.loadUrl(url);
        //设置了默认在本应用打开，不设置会用浏览器打开的
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //设置webView
                String backgroudColor = "#232736";
                String fontColor = "#626f9b";
                String urlColor = "#9AACEC";
//                SkinManager.setupWebView(webView, backgroudColor, fontColor, urlColor);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                webView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    // 网页加载完成
                    progressbar.setVisibility(View.GONE);
                } else {
                    // 加载中
                    if (progressbar.getVisibility() == View.GONE) {
                        progressbar.setVisibility(View.VISIBLE);
                    }
                    progressbar.setProgress(newProgress);
                }
            }
        });

        webView.setDownloadListener(new MyWebViewDownLoadListener());

    }

    private class MyWebViewDownLoadListener implements DownloadListener {
        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                    long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
            case R.id.action_copy://复制链接
                // 从API11开始android推荐使用android.content.ClipboardManager
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                ClipData clipData = ClipData.newPlainText("text", url);
                cm.setPrimaryClip(clipData);
                MySnackbar.makeSnackBarBlack(webView, "复制成功");
                break;
            case R.id.action_open://打开浏览器
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(url);
                intent.setData(content_url);
                startActivity(intent);
                break;
            case R.id.action_share://分享链接
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain"); // 纯文本
                shareIntent.putExtra(Intent.EXTRA_TITLE, "GankMM链接分享");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "GankMM链接分享：" + url);
                //设置分享列表的标题，并且每次都显示分享列表
                startActivity(Intent.createChooser(shareIntent, "分享到"));
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
