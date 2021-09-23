package com.example.newsreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URL;

public class ArticleActivity2 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article2);


        WebView webvview = (WebView) findViewById(R.id.webvview);

        webvview.setWebViewClient(new WebViewClient());

        webvview.getSettings().setJavaScriptEnabled(true);




        Log.i("six", "6");

            webvview.loadUrl(getIntent().getExtras().getString("url"));

    }
}
