package com.dragon.wtudragondesign.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.dragon.wtudragondesign.R;
import com.dragon.wtudragondesign.template.BaseActivity;

public class WebViewActivity extends BaseActivity {

    private WebView webView;
    private ProgressBar progressBar;
    private String urlString;
    private String nameString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        urlString = intent.getStringExtra("url");
        nameString = intent.getStringExtra("name");
        loadMainUI(R.layout.activity_web_view);
        hideRightText();
        setTitle(""+nameString);
        init();
        initData();
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void init() {
        progressBar = fv(R.id.progressBar);

        webView = fv(R.id.webView);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        if (TextUtils.isEmpty(urlString)) {
            webView.loadUrl("");
            showToast("无相关信息");
        } else {
            webView.loadUrl(urlString);
        }

        webView.getSettings().setJavaScriptEnabled(true);

        webView.getSettings().setUseWideViewPort(true);

        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        webView.getSettings().setBuiltInZoomControls(false);//设置不放大

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                }
            }
            // 允许web界面弹出框
            @Override
            public boolean onJsAlert(WebView view, String url, String message,
                                     JsResult result) {

                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url,
                                       String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message,
                                      String defaultValue, JsPromptResult result) {
                return super.onJsPrompt(view, url, message, defaultValue,
                        result);
            }

        });
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    // 监听键盘返回键的事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
            } else {
                getAct().finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void initData() {

    }
}
