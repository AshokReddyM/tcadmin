package com.byt_eye.tcadmin.webview;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.byt_eye.tcadmin.R;


public class WebViewActivity extends AppCompatActivity {

    TextView title;
    WebView webview;
    ProgressBar progressBar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SetJavaScriptEnabled")
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_web_view);

        Intent intent = getIntent();
        final String mPostLink = intent.getStringExtra("post_link");
        final String mPostTitle = intent.getStringExtra("post_title");
        String mPostDesc = intent.getStringExtra("post_desc");


        progressBar = (ProgressBar) findViewById(R.id.web_page_loading_progress);
        webview = (WebView) findViewById(R.id.post_web_view);

        webview.clearCache(true);
        webview.clearHistory();
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        webview.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);


        CookieManager.getInstance().setAcceptThirdPartyCookies(webview, true);
        webview.loadUrl(mPostLink);




        webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100 && progressBar.getVisibility() == ProgressBar.GONE) {
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                }
                progressBar.setProgress(progress);

            }


        });

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        progressBar = (ProgressBar) findViewById(R.id.web_page_loading_progress);



        title = ((TextView) findViewById(R.id.post_heading_toolbar));
        ImageView imageView = (ImageView) findViewById(R.id.back_web_view);
        imageView.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        title.setText(mPostTitle);



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}






