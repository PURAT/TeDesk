package com.example.tedesk.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.example.tedesk.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class BaseActivity extends AppCompatActivity {

    private Context context;
    private Activity activity;

    private long backPressedMillis;
    private Toast backToast;

    protected Toolbar toolbar;
    private LinearLayout loadingView, noDataView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = BaseActivity.this;
        context = activity.getApplicationContext();
    }

    public void initToolbar(String title) {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(title);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
    }

    public void enableUpButton() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

  /*  public void initLoader() {
        loadingView = (LinearLayout) findViewById(R.id.loadingView);
        noDataView = (LinearLayout) findViewById(R.id.noDataView);
    }
*/

/*    public void showLoader() {
        if (loadingView != null) {
            loadingView.setVisibility(View.VISIBLE);
        }

        if (noDataView != null) {
            noDataView.setVisibility(View.GONE);
        }
    }*/

/*
    public void hideLoader() {
        if (loadingView != null) {
            loadingView.setVisibility(View.GONE);
        }

        if (noDataView != null) {
            noDataView.setVisibility(View.GONE);
        }
    }
*/

    public void showEmptyView() {
        if (loadingView != null) {
            loadingView.setVisibility(View.GONE);
        }

        if (noDataView != null) {
            noDataView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        if (backPressedMillis + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(this, "Нажмите ещё раз, чтобы выйти", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedMillis = System.currentTimeMillis();
    }
}
