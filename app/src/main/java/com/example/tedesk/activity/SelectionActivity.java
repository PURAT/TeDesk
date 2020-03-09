package com.example.tedesk.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tedesk.R;
import com.example.tedesk.constans.AppConstants;
import com.example.tedesk.utilities.ActivityUtilities;

public class SelectionActivity extends BaseActivity {

    private Activity mActivity;
    private String categoryId;
    private Button btnTraining, btnTesting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVar();
        initView();
        initListener();
    }

    private void initVar() {
        mActivity = SelectionActivity.this;

        Intent intent = getIntent();
        if (intent != null)
            categoryId = intent.getStringExtra(AppConstants.BUNDLE_KEY_INDEX);
    }

    private void initView() {
        setContentView(R.layout.activity_selection);

        btnTesting = (Button) findViewById(R.id.btn_testing);
        btnTraining = (Button) findViewById(R.id.btn_training);

        initToolbar(getString(R.string.choice));
        enableUpButton();
    }

    private void initListener() {
        btnTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "В будущем будет добавлен раздел \"Обучение\"", Toast.LENGTH_SHORT).show();
            }
        });
        btnTesting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtilities.getInstance().invokeCommonQuizActivity(mActivity, QuizPromptActivity.class, categoryId, false);
            }
        });
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
