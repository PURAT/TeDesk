package com.example.tedesk.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.tedesk.R;
import com.example.tedesk.constans.AppConstants;
import com.example.tedesk.utilities.ActivityUtilities;

public class ActivityItem3 extends BaseActivity {

    private Activity mActivity;
    private Context context;
    private String pageTitle, pageUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

//    private class MyWebViewClient extends WebViewClient {
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            if ("http://tzk.ru/ftpgetfile.php?id=3084".equals(Uri.parse(url).getHost())) {
//                // This is my website, so do not override; let my WebView load the page
//                return false;
//            }
//            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//            startActivity(intent);
//            return true;
//        }
//    }

    private void initView() {
        mActivity = ActivityItem3.this;
        setContentView(R.layout.activity_item3);

        initToolbar(getString(R.string.item_3));
        enableUpButton();
        onBackPressed();


        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.loadUrl("https://vk.com/doc174915045_540985036?hash=6a41882526336ff283&dl=1095aa409e4c4c417d");

//        myWebView.setWebViewClient(new MyWebViewClient());
//        invokeNativeApp("https://vk.com/doc174915045_540984857?hash=dc8161e6df4c7f55e8&dl=651e8d4eb8362c449a");

}

//    private void invokeNativeApp(String url) {
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//        mActivity.startActivity(intent);
//    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ActivityUtilities.getInstance().invokeNewActivity(mActivity, MenuActivity.class, true);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        ActivityUtilities.getInstance().invokeNewActivity(mActivity, MenuActivity.class, true);
    }
}
