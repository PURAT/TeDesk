package com.example.tedesk.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;

import com.example.tedesk.R;
import com.example.tedesk.utilities.ActivityUtilities;

public class ActivityItem4 extends BaseActivity {

    private Activity mActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        mActivity = ActivityItem4.this;
        setContentView(R.layout.activity_item4);

        initToolbar(getString(R.string.item_4));
        enableUpButton();
        onBackPressed();


        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.loadUrl("https://vk.com/doc174915045_540985154?hash=26d5e491d41853d227&dl=1e2294160fa94f7e21");
    }

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
